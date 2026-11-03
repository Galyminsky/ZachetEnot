package com.galyaminsky.zachetenot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.galyaminsky.zachetenot.domain.catalog.AppCatalog
import com.galyaminsky.zachetenot.data.AssetContentRepository
import com.galyaminsky.zachetenot.data.DataStoreProgressRepository
import com.galyaminsky.zachetenot.data.progressDataStore
import com.galyaminsky.zachetenot.domain.model.ContentBundle
import com.galyaminsky.zachetenot.domain.model.TestConfig
import com.galyaminsky.zachetenot.domain.model.TestResult
import com.galyaminsky.zachetenot.domain.test.TestEngine
import com.galyaminsky.zachetenot.domain.test.TestSession
import com.galyaminsky.zachetenot.domain.test.TestTimer
import com.galyaminsky.zachetenot.data.SystemTimeSource
import com.galyaminsky.zachetenot.domain.progress.ProgressCalculator
import com.galyaminsky.zachetenot.domain.progress.TopicStatus
import com.galyaminsky.zachetenot.ui.screens.classselection.ClassSelectionScreen
import com.galyaminsky.zachetenot.ui.screens.home.HomeScreen
import com.galyaminsky.zachetenot.ui.screens.home.HomeSubjectUi
import com.galyaminsky.zachetenot.ui.screens.home.HomeUiState
import com.galyaminsky.zachetenot.ui.screens.subjectselection.SubjectSelectionScreen
import com.galyaminsky.zachetenot.ui.screens.progress.ProgressScreen
import com.galyaminsky.zachetenot.ui.screens.progress.ProgressUiState
import com.galyaminsky.zachetenot.ui.screens.settings.SettingsScreen
import com.galyaminsky.zachetenot.ui.screens.settings.SettingsUiState
import com.galyaminsky.zachetenot.ui.screens.topicselection.TopicSelectionScreen
import com.galyaminsky.zachetenot.ui.screens.topicselection.TopicSelectionUiState
import com.galyaminsky.zachetenot.ui.screens.topicselection.TopicItemUi
import com.galyaminsky.zachetenot.ui.screens.test.PrepScreen
import com.galyaminsky.zachetenot.ui.screens.test.PrepUiState
import com.galyaminsky.zachetenot.ui.screens.test.TestScreen
import com.galyaminsky.zachetenot.ui.screens.test.TestScreenState
import com.galyaminsky.zachetenot.ui.screens.test.TestHeaderUi
import com.galyaminsky.zachetenot.ui.screens.test.AnswerUi
import com.galyaminsky.zachetenot.ui.screens.test.AnswerPhase
import com.galyaminsky.zachetenot.ui.screens.result.ResultScreen
import com.galyaminsky.zachetenot.ui.screens.result.ResultUiState
import com.galyaminsky.zachetenot.ui.screens.result.ReviewScreen
import com.galyaminsky.zachetenot.ui.screens.result.ReviewUiState
import com.galyaminsky.zachetenot.ui.components.MistakeUi
import com.galyaminsky.zachetenot.ui.components.AppNavigationBar
import com.galyaminsky.zachetenot.ui.components.NavItem
import com.galyaminsky.zachetenot.ui.components.StateDialog
import com.galyaminsky.zachetenot.ui.screens.status.StatusScreen
import com.galyaminsky.zachetenot.ui.screens.status.StatusKind
import com.galyaminsky.zachetenot.ui.theme.InfoContainer
import com.galyaminsky.zachetenot.ui.theme.AttentionContainer
import com.galyaminsky.zachetenot.ui.theme.DangerContainer
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZachetEnotTheme {
                var selectedGrade by rememberSaveable { mutableIntStateOf(AppCatalog.DEFAULT_GRADE) }
                var showHome by rememberSaveable { mutableStateOf(false) }
                var showSubjectSelection by rememberSaveable { mutableStateOf(false) }
                var showProgress by rememberSaveable { mutableStateOf(false) }
                var showSettings by rememberSaveable { mutableStateOf(false) }
                var showTopics by rememberSaveable { mutableStateOf(false) }
                var showPrep by rememberSaveable { mutableStateOf(false) }
                var showTest by rememberSaveable { mutableStateOf(false) }
                var showResult by rememberSaveable { mutableStateOf(false) }
                var showReview by rememberSaveable { mutableStateOf(false) }
                var showNoContent by rememberSaveable { mutableStateOf(false) }
                var showUnavailable by rememberSaveable { mutableStateOf(false) }
                var reviewMistakes by remember { mutableStateOf<List<MistakeUi>>(emptyList()) }
                var showExitConfirmation by remember { mutableStateOf(false) }
                var showResetConfirmation by remember { mutableStateOf(false) }
                var showTimeUp by remember { mutableStateOf(false) }
                var warningMinute by remember { mutableStateOf<Int?>(null) }
                var selectedTopicId by rememberSaveable { mutableStateOf<String?>(null) }
                var session by remember { mutableStateOf<TestSession?>(null) }
                var timer by remember { mutableStateOf<TestTimer?>(null) }
                var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
                var answerPhase by remember { mutableStateOf(AnswerPhase.Empty) }
                var remainingMs by remember { mutableStateOf(TestConfig.TIME_LIMIT_MINUTES * 60_000L) }
                var result by remember { mutableStateOf<TestResult?>(null) }
                val coroutineScope = rememberCoroutineScope()
                var soundEnabled by rememberSaveable { mutableStateOf(true) }
                var selectedSubjectId by rememberSaveable { mutableStateOf(AppCatalog.DEFAULT_SUBJECT_ID) }
                val progressRepository = remember { DataStoreProgressRepository(progressDataStore) }
                val passedIds by progressRepository.passedTopicIds.collectAsState(initial = emptySet())
                val content by produceState<ContentBundle?>(initialValue = null) {
                    value = AssetContentRepository(this@MainActivity).loadContent()
                }
                val subjectProgress = ProgressCalculator.calculate(content?.topics.orEmpty(), passedIds)
                LaunchedEffect(showTest, timer) {
                    while (showTest && timer != null) {
                        remainingMs = timer!!.remainingMs()
                        if (timer!!.isExpired()) {
                            result = session?.finish()
                            if (result?.passed == true) selectedTopicId?.let { id -> coroutineScope.launch { progressRepository.markPassed(id) } }
                            warningMinute = null
                            showExitConfirmation = false
                            showTimeUp = true
                            break
                        }
                        timer!!.consumeWarning()?.let { warningMinute = it }
                        delay(1_000L)
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showReview && result != null) {
                        val currentResult = result!!
                        ReviewScreen(
                            state = ReviewUiState(
                                contextLine = stringResource(R.string.figma_grade_subject, stringResource(R.string.subject_grade_chip), stringResource(R.string.subject_russian)),
                                title = stringResource(R.string.variant_review_title),
                                subtitle = stringResource(R.string.variant_review_subtitle),
                                statsLine = stringResource(if (reviewMistakes.isEmpty()) R.string.variant_review_perfect_note else R.string.variant_review_score, currentResult.correctCount, currentResult.totalCount),
                                timeLine = stringResource(R.string.figma_time_format, ((TestConfig.TIME_LIMIT_MINUTES * 60_000L - remainingMs) / 60_000L).toInt(), (((TestConfig.TIME_LIMIT_MINUTES * 60_000L - remainingMs) / 1_000L) % 60).toInt()),
                                mistakes = reviewMistakes,
                                footerMessage = null,
                                success = null,
                                buttonText = stringResource(R.string.figma_result_topics)
                            ),
                            onBack = { showReview = false; showResult = true },
                            onList = { showReview = false; showTopics = true },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else if (showResult && result != null) {
                        val currentResult = result!!
                        ResultScreen(
                            state = ResultUiState(
                                contextLine = stringResource(R.string.figma_grade_subject, stringResource(R.string.subject_grade_chip), stringResource(R.string.subject_russian)),
                                headerLabel = stringResource(R.string.figma_result_title),
                                title = stringResource(R.string.figma_result_title),
                                subtitle = content?.topics?.firstOrNull { it.id == selectedTopicId }?.title.orEmpty(),
                                passed = currentResult.passed,
                                verdictTitle = stringResource(if (currentResult.passed) R.string.figma_result_passed else R.string.figma_result_failed),
                                verdictNote = null,
                                correctText = stringResource(R.string.figma_count_slash, currentResult.correctCount, currentResult.totalCount),
                                percentText = stringResource(R.string.percent_format, currentResult.percent),
                                correctLabel = stringResource(R.string.figma_result_correct),
                                completion = 1f,
                                completionText = stringResource(R.string.figma_result_completion, currentResult.totalCount, currentResult.totalCount),
                                errorsValue = currentResult.incorrectCount.toString(),
                                errorsLabel = stringResource(R.string.figma_result_errors),
                                timeValue = stringResource(R.string.figma_time_format, ((TestConfig.TIME_LIMIT_MINUTES * 60_000L - remainingMs) / 60_000L).toInt(), (((TestConfig.TIME_LIMIT_MINUTES * 60_000L - remainingMs) / 1_000L) % 60).toInt()),
                                timeLabel = stringResource(R.string.figma_result_time),
                                message = stringResource(if (currentResult.passed) R.string.figma_result_passed_message else R.string.figma_result_failed_message),
                                reviewButton = stringResource(R.string.figma_result_review),
                                retryButton = stringResource(R.string.figma_result_retry),
                                listButton = stringResource(R.string.figma_result_topics),
                                failedNote = stringResource(R.string.variant_failed_note, currentResult.correctCount, currentResult.totalCount)
                            ),
                            onReview = { showResult = false; showReview = true },
                            onRetry = { showResult = false; showPrep = true },
                            onList = { showResult = false; showTopics = true },
                            modifier = Modifier.padding(innerPadding),
                            reviewAvailable = reviewMistakes.size == currentResult.incorrectCount,
                            onHome = { showResult = false; showHome = true },
                            onProgress = { showResult = false; showProgress = true }
                        )
                    } else if (showTest && session != null) {
                        val currentSession = session!!
                        val question = currentSession.currentQuestion
                        TestScreen(
                            state = TestScreenState(
                                header = TestHeaderUi(
                                    contextLine = stringResource(R.string.figma_grade_subject, stringResource(R.string.subject_grade_chip), stringResource(R.string.subject_russian)),
                                    title = content?.topics?.firstOrNull { it.id == selectedTopicId }?.title.orEmpty(),
                                    questionNumber = currentSession.currentIndex + 1,
                                    total = currentSession.questions.size,
                                    answered = currentSession.currentIndex + if (answerPhase == AnswerPhase.Locked) 1 else 0,
                                    timeText = stringResource(R.string.figma_time_format, (remainingMs / 60_000L).toInt(), ((remainingMs / 1_000L) % 60).toInt())
                                ),
                                taskLabel = stringResource(R.string.figma_test_label),
                                taskText = question.text,
                                answer = AnswerUi.Choice(question.options.map { it.text }, selectedOptionIndex),
                                phase = answerPhase,
                                buttonText = stringResource(if (answerPhase == AnswerPhase.Locked) { if (currentSession.isLastQuestion) R.string.figma_test_finish else R.string.figma_test_next } else R.string.figma_test_answer),
                                caption = stringResource(if (answerPhase == AnswerPhase.Locked) R.string.figma_test_locked else R.string.figma_test_empty)
                            ),
                            onExit = { showExitConfirmation = true },
                            onAction = {
                                if (answerPhase == AnswerPhase.Filled) {
                                    val chosen = question.options[selectedOptionIndex!!]
                                    val correct = currentSession.submitAnswer(chosen.id)
                                    if (!correct) {
                                        val rule = content?.ruleOf(question.ruleId)
                                        if (rule != null) reviewMistakes = reviewMistakes + MistakeUi(
                                            questionLabel = getString(R.string.variant_review_question, currentSession.currentIndex + 1),
                                            questionText = question.text,
                                            yourAnswerLabel = getString(R.string.variant_review_rule),
                                            yourAnswer = chosen.text,
                                            correctLabel = getString(R.string.variant_review_rule),
                                            correctAnswer = question.options.first { it.id == question.correctOptionId }.text,
                                            ruleTitle = rule.title,
                                            ruleText = rule.explanation
                                        )
                                    }
                                    answerPhase = AnswerPhase.Locked
                                } else if (answerPhase == AnswerPhase.Locked) {
                                    if (currentSession.isLastQuestion) {
                                        result = currentSession.finish()
                                        if (result?.passed == true) selectedTopicId?.let { id -> coroutineScope.launch { progressRepository.markPassed(id) } }
                                        showTest = false; showResult = true
                                    } else {
                                        currentSession.moveToNext()
                                        selectedOptionIndex = null
                                        answerPhase = AnswerPhase.Empty
                                    }
                                }
                            },
                            onChoiceSelected = { selectedOptionIndex = it; answerPhase = AnswerPhase.Filled },
                            onTextChange = {}, onPartClick = {}, onPairClick = {},
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else if (showNoContent || showUnavailable) {
                        StatusScreen(kind = if (showNoContent) StatusKind.NoContent else StatusKind.Unavailable, onBack = {
                            if (showNoContent) { showNoContent = false; showTopics = true }
                            else { showUnavailable = false; showSubjectSelection = true }
                        }, modifier = Modifier.padding(innerPadding))
                    } else if (showPrep) {
                        PrepScreen(
                            state = PrepUiState(
                                contextLine = stringResource(R.string.figma_grade_subject, stringResource(R.string.subject_grade_chip), stringResource(R.string.subject_russian)),
                                title = stringResource(R.string.figma_prep_title),
                                subtitle = stringResource(R.string.figma_prep_subtitle),
                                conditionsTitle = stringResource(R.string.figma_prep_ready),
                                conditionsStats = stringResource(R.string.figma_prep_stats),
                                conditionsRules = stringResource(R.string.figma_prep_warning_text),
                                passNote = stringResource(R.string.figma_prep_pass),
                                warningTitle = stringResource(R.string.figma_prep_warning),
                                warningText = stringResource(R.string.figma_prep_warning_text),
                                buttonText = stringResource(R.string.figma_prep_start),
                                caption = stringResource(R.string.figma_prep_repeat)
                            ),
                            onBack = { showPrep = false; showTopics = true },
                            onHome = { showPrep = false; showHome = true },
                            onProgress = { showPrep = false; showProgress = true },
                            onStart = {
                                val bank = content?.questionsOf(selectedTopicId.orEmpty()).orEmpty()
                                if (bank.size >= TestConfig.QUESTIONS_PER_TEST) {
                                    session = TestEngine().startSession(bank)
                                    timer = TestTimer(SystemTimeSource()).also { it.start() }
                                    remainingMs = TestConfig.TIME_LIMIT_MINUTES * 60_000L
                                    selectedOptionIndex = null
                                    answerPhase = AnswerPhase.Empty
                                    reviewMistakes = emptyList()
                                    showPrep = false; showTest = true
                                } else { showPrep = false; showNoContent = true }
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else if (showSettings) {
                        SettingsScreen(
                            state = SettingsUiState(stringResource(R.string.subject_grade_chip), soundEnabled),
                            onSoundChanged = { soundEnabled = it },
                            onGradeClick = { showSettings = false; showHome = false },
                            onResetClick = { showResetConfirmation = true },
                            onHome = { showSettings = false },
                            onSubjects = { showSettings = false; showSubjectSelection = true },
                            onProgress = { showSettings = false; showProgress = true },
                            modifier = Modifier.padding(innerPadding),
                            soundAvailable = false
                        )
                    } else if (showProgress) {
                        ProgressScreen(
                            state = ProgressUiState(
                                gradeLabel = stringResource(R.string.subject_grade_chip),
                                subjectName = stringResource(R.string.subject_russian),
                                passedCount = subjectProgress.passedCount,
                                totalCount = subjectProgress.totalCount,
                                percent = subjectProgress.percent,
                                availableCount = subjectProgress.topics.count { it.status == TopicStatus.AVAILABLE },
                                lockedCount = subjectProgress.topics.count { it.status == TopicStatus.LOCKED }
                            ),
                            onHome = { showProgress = false },
                            onSubjects = { showProgress = false; showSubjectSelection = true },
                            onTopics = { showProgress = false; showTopics = true },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else if (showTopics) {
                        Column(modifier = Modifier.padding(innerPadding)) {
                        TopicSelectionScreen(
                            state = TopicSelectionUiState(
                                gradeLabel = stringResource(R.string.subject_grade_chip),
                                subjectName = stringResource(R.string.subject_russian),
                                topics = subjectProgress.topics.map { item -> TopicItemUi(item.topic.id, item.topic.order, item.topic.title, item.topic.description, item.status) },
                                passedCount = subjectProgress.passedCount,
                                totalCount = subjectProgress.totalCount,
                                percent = subjectProgress.percent,
                                progressMessage = stringResource(R.string.topics_progress_message)
                            ),
                            onTopicClick = { selectedTopicId = it; showTopics = false; showPrep = true },
                            onSettingsClick = { showTopics = false; showSettings = true },
                            modifier = Modifier.weight(1f)
                        )
                        AppNavigationBar(
                            items = listOf(
                                NavItem(stringResource(R.string.home_nav_home), R.drawable.ic_nav_home),
                                NavItem(stringResource(R.string.home_nav_subjects), R.drawable.ic_nav_grid),
                                NavItem(stringResource(R.string.nav_progress), R.drawable.ic_nav_progress)
                            ),
                            selectedIndex = 1,
                            onSelect = { index -> when (index) {
                                0 -> showTopics = false
                                1 -> { showTopics = false; showSubjectSelection = true }
                                2 -> { showTopics = false; showProgress = true }
                            } }
                        )
                        }
                    } else if (showSubjectSelection) {
                        SubjectSelectionScreen(
                            selectedSubjectId = selectedSubjectId,
                            onSubjectSelected = { selectedSubjectId = it },
                            onBack = { showSubjectSelection = false },
                            onContinue = { showSubjectSelection = false; showTopics = true },
                            onProgress = { showSubjectSelection = false; showProgress = true },
                            onUnavailable = { showSubjectSelection = false; showUnavailable = true },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else if (showHome) {
                        val subjects = AppCatalog.subjects.map { subject ->
                            val title = when (subject.id) {
                                "russian" -> R.string.subject_russian
                                "math" -> R.string.subject_math
                                "literature" -> R.string.subject_literature
                                "world" -> R.string.subject_world
                                else -> error("Unknown subject: ${subject.id}")
                            }
                            val icon = when (subject.id) {
                                "math" -> R.drawable.ic_calculator
                                "literature" -> R.drawable.ic_book_open
                                "world" -> R.drawable.ic_globe
                                else -> null
                            }
                            HomeSubjectUi(
                                id = subject.id,
                                title = stringResource(title),
                                subtitle = stringResource(if (subject.id == AppCatalog.DEFAULT_SUBJECT_ID) R.string.subject_russian_subtitle else R.string.subject_coming_soon),
                                available = subject.isAvailable,
                                selected = subject.id == AppCatalog.DEFAULT_SUBJECT_ID,
                                iconText = if (subject.id == AppCatalog.DEFAULT_SUBJECT_ID) stringResource(R.string.subject_russian_icon) else null,
                                icon = icon
                            )
                        }
                        HomeScreen(
                            state = HomeUiState(
                                gradeLabel = stringResource(R.string.subject_grade_chip),
                                greeting = stringResource(R.string.home_start_greeting),
                                programSummary = stringResource(R.string.subject_selection_footer),
                                ctaLabel = stringResource(R.string.home_choose_subject),
                                subjects = subjects,
                                passedCount = subjectProgress.passedCount,
                                totalCount = subjectProgress.totalCount,
                                percent = subjectProgress.percent,
                                progressMessage = stringResource(R.string.subject_selection_footer)
                            ),
                            onSettingsClick = { showSettings = true },
                            onProgramClick = { showSubjectSelection = true },
                            onSubjectClick = {},
                            onProgressClick = { showProgress = true },
                            onNextTopicClick = { showTopics = true },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        ClassSelectionScreen(
                            selectedGrade = selectedGrade,
                            onGradeSelected = { selectedGrade = it },
                            onContinue = { showHome = true },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
                if (showExitConfirmation) {
                    StateDialog(illustration = R.drawable.ic_dialog_exit, badge = stringResource(R.string.state_confirm_badge), title = stringResource(R.string.figma_exit_title), message = stringResource(R.string.figma_exit_message), primaryLabel = stringResource(R.string.state_exit_continue), onPrimary = { showExitConfirmation = false }, onDismiss = { showExitConfirmation = false }, heroColor = InfoContainer, secondaryLabel = stringResource(R.string.figma_exit_confirm), onSecondary = { showExitConfirmation = false; showTest = false; showTopics = true })
                }
                if (showResetConfirmation) {
                    StateDialog(illustration = R.drawable.ic_reset, badge = stringResource(R.string.state_reset_badge), title = stringResource(R.string.figma_reset_title), message = stringResource(R.string.figma_reset_message), primaryLabel = stringResource(R.string.figma_reset_confirm), onPrimary = { coroutineScope.launch { progressRepository.reset() }; showResetConfirmation = false }, onDismiss = { showResetConfirmation = false }, heroColor = DangerContainer, secondaryLabel = stringResource(R.string.figma_cancel), onSecondary = { showResetConfirmation = false }, secondaryFirst = true, destructive = true)
                }
                warningMinute?.let { minute ->
                    StateDialog(illustration = R.drawable.ic_dialog_timer, badge = stringResource(if (minute == 5) R.string.figma_warning_five else R.string.figma_warning_one), title = stringResource(if (minute == 5) R.string.state_timer_five else R.string.state_timer_one), message = stringResource(if (minute == 5) R.string.figma_warning_five_text else R.string.figma_warning_one_text), primaryLabel = stringResource(R.string.state_warning_continue), onPrimary = { warningMinute = null }, onDismiss = { warningMinute = null }, heroColor = if (minute == 5) InfoContainer else AttentionContainer)
                }
                if (showTimeUp) {
                    StateDialog(illustration = R.drawable.ic_dialog_hourglass, badge = stringResource(R.string.state_timeup_badge), title = stringResource(R.string.figma_time_up_title), message = stringResource(R.string.figma_time_up_message), primaryLabel = stringResource(R.string.state_timeup_result), onPrimary = { showTimeUp = false; showTest = false; showResult = true }, onDismiss = { showTimeUp = false; showTest = false; showResult = true }, heroColor = AttentionContainer)
                }
            }
        }
    }
}

