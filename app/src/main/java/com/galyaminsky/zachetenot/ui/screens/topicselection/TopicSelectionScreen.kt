package com.galyaminsky.zachetenot.ui.screens.topicselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.domain.progress.TopicStatus
import com.galyaminsky.zachetenot.ui.components.AppLogo
import com.galyaminsky.zachetenot.ui.components.HintRow
import com.galyaminsky.zachetenot.ui.components.ProgressCard
import com.galyaminsky.zachetenot.ui.components.SectionHeader
import com.galyaminsky.zachetenot.ui.components.SettingsButton
import com.galyaminsky.zachetenot.ui.components.TopicCard
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

data class TopicItemUi(val id: String, val number: Int, val title: String, val description: String, val status: TopicStatus)

data class TopicSelectionUiState(
    val gradeLabel: String,
    val subjectName: String,
    val topics: List<TopicItemUi>,
    val passedCount: Int,
    val totalCount: Int,
    val percent: Int,
    val progressMessage: String
)

@Composable
fun TopicSelectionScreen(
    state: TopicSelectionUiState,
    onTopicClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().background(Background).verticalScroll(rememberScrollState())
            .padding(horizontal = Dimens.ScreenHorizontalPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(Dimens.Space48),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppLogo()
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                Text(text = state.gradeLabel, style = MaterialTheme.typography.labelSmall, color = TextSecondary, textAlign = TextAlign.End)
                Text(text = state.subjectName, style = MaterialTheme.typography.titleSmall, color = TextPrimary, textAlign = TextAlign.End)
            }
            SettingsButton(onClick = onSettingsClick)
        }
        Spacer(modifier = Modifier.height(Dimens.Space24))
        Text(text = stringResource(R.string.topics_greeting), style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
        Spacer(modifier = Modifier.height(Dimens.Space8))
        Text(text = stringResource(R.string.topics_subtitle), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        Spacer(modifier = Modifier.height(Dimens.Space24))
        ProgressCard(
            passedCount = state.passedCount,
            totalCount = state.totalCount,
            percent = state.percent,
            message = state.progressMessage
        )
        Spacer(modifier = Modifier.height(Dimens.Space24))
        SectionHeader(title = stringResource(R.string.topics_section_title), trailing = stringResource(R.string.topics_count, state.totalCount))
        Spacer(modifier = Modifier.height(Dimens.Space12))
        Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
            state.topics.forEach { topic ->
                TopicCard(
                    number = topic.number,
                    title = topic.title,
                    description = topic.description,
                    status = topic.status,
                    onClick = { if (topic.status != TopicStatus.LOCKED) onTopicClick(topic.id) }
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimens.Space24))
        HintRow(icon = R.drawable.ic_lock_small, text = stringResource(R.string.topics_hint), iconSize = 16.dp, spacing = 9.dp)
        Spacer(modifier = Modifier.height(Dimens.Space24))
    }
}

@Preview(showBackground = true)
@Composable
private fun TopicSelectionPreview() {
    ZachetEnotTheme {
        TopicSelectionScreen(
            state = TopicSelectionUiState(
                gradeLabel = stringResource(R.string.subject_grade_chip),
                subjectName = stringResource(R.string.subject_russian),
                topics = listOf(
                    TopicItemUi("01", 1, stringResource(R.string.topic_speech_title), stringResource(R.string.topic_speech_description), TopicStatus.PASSED),
                    TopicItemUi("02", 2, stringResource(R.string.topic_sentence_title), stringResource(R.string.topic_sentence_description), TopicStatus.PASSED),
                    TopicItemUi("03", 3, stringResource(R.string.topic_word_title), stringResource(R.string.topic_word_description), TopicStatus.AVAILABLE),
                    TopicItemUi("04", 4, stringResource(R.string.topic_roots_title), stringResource(R.string.topic_roots_description), TopicStatus.LOCKED),
                    TopicItemUi("05", 5, stringResource(R.string.topic_noun_title), stringResource(R.string.topic_noun_description), TopicStatus.LOCKED),
                    TopicItemUi("06", 6, stringResource(R.string.topic_adjective_title), stringResource(R.string.topic_adjective_description), TopicStatus.LOCKED)
                ),
                passedCount = 2,
                totalCount = 6,
                percent = 33,
                progressMessage = stringResource(R.string.topics_progress_message)
            ),
            onTopicClick = {},
            onSettingsClick = {}
        )
    }
}
