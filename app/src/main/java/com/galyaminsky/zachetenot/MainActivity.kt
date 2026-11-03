package com.galyaminsky.zachetenot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.galyaminsky.zachetenot.ui.components.AppNavigationBar
import com.galyaminsky.zachetenot.ui.components.NavItem
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
                            showTest = false
                            showResult = true
                            showTimeUp = true
                            break
                        }
                        timer!!.consumeWarning()?.let { warningMinute = it }
                        delay(1_000L)
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showResult && result != null) {
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
                                listButton = stringResource(R.string.figma_result_topics)
                            ),
                            onReview = {},
                            onRetry = { showResult = false; showPrep = true },
                            onList = { showResult = false; showTopics = true },
                            modifier = Modifier.padding(innerPadding),
                            reviewAvailable = false
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
                                    currentSession.submitAnswer(question.options[selectedOptionIndex!!].id)
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
                            onStart = {
                                val bank = content?.questionsOf(selectedTopicId.orEmpty()).orEmpty()
                                if (bank.size >= TestConfig.QUESTIONS_PER_TEST) {
                                    session = TestEngine().startSession(bank)
                                    timer = TestTimer(SystemTimeSource()).also { it.start() }
                                    remainingMs = TestConfig.TIME_LIMIT_MINUTES * 60_000L
                                    selectedOptionIndex = null
                                    answerPhase = AnswerPhase.Empty
                                    showPrep = false; showTest = true
                                }
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
                                NavItem(stringResource(R.string.home_nav_home), R.drawable.ic_logo),
                                NavItem(stringResource(R.string.home_nav_subjects), R.drawable.ic_layers),
                                NavItem(stringResource(R.string.nav_progress), R.drawable.ic_chart)
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
                    AlertDialog(
                        onDismissRequest = { showExitConfirmation = false },
                        title = { Text(text = stringResource(R.string.figma_exit_title)) },
                        text = { Text(text = stringResource(R.string.figma_exit_message)) },
                        confirmButton = { TextButton(onClick = { showExitConfirmation = false; showTest = false; showTopics = true }) { Text(text = stringResource(R.string.figma_exit_confirm)) } },
                        dismissButton = { TextButton(onClick = { showExitConfirmation = false }) { Text(text = stringResource(R.string.figma_cancel)) } }
                    )
                }
                if (showResetConfirmation) {
                    AlertDialog(
                        onDismissRequest = { showResetConfirmation = false },
                        title = { Text(text = stringResource(R.string.figma_reset_title)) },
                        text = { Text(text = stringResource(R.string.figma_reset_message)) },
                        confirmButton = { TextButton(onClick = { coroutineScope.launch { progressRepository.reset() }; showResetConfirmation = false }) { Text(text = stringResource(R.string.figma_reset_confirm)) } },
                        dismissButton = { TextButton(onClick = { showResetConfirmation = false }) { Text(text = stringResource(R.string.figma_cancel)) } }
                    )
                }
                warningMinute?.let { minute ->
                    AlertDialog(
                        onDismissRequest = { warningMinute = null },
                        title = { Text(text = stringResource(if (minute == 5) R.string.figma_warning_five else R.string.figma_warning_one)) },
                        text = { Text(text = stringResource(if (minute == 5) R.string.figma_warning_five_text else R.string.figma_warning_one_text)) },
                        confirmButton = { TextButton(onClick = { warningMinute = null }) { Text(text = stringResource(R.string.figma_understood)) } }
                    )
                }
                if (showTimeUp) {
                    AlertDialog(
                        onDismissRequest = { showTimeUp = false },
                        title = { Text(text = stringResource(R.string.figma_time_up_title)) },
                        text = { Text(text = stringResource(R.string.figma_time_up_message)) },
                        confirmButton = { TextButton(onClick = { showTimeUp = false }) { Text(text = stringResource(R.string.figma_understood)) } }
                    )
                }
            }
        }
    }
}
