package com.galyaminsky.zachetenot.ui.screens.topicselection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.domain.progress.TopicStatus
import com.galyaminsky.zachetenot.ui.components.VariantHeader
import com.galyaminsky.zachetenot.ui.components.StatusChip
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.HeroShape22
import com.galyaminsky.zachetenot.ui.theme.TopicTileTitle

data class TopicItemUi(val id: String, val number: Int, val title: String, val description: String, val status: TopicStatus)
data class TopicSelectionUiState(val gradeLabel: String, val subjectName: String, val topics: List<TopicItemUi>, val passedCount: Int, val totalCount: Int, val percent: Int, val progressMessage: String)

@Composable
fun TopicSelectionScreen(state: TopicSelectionUiState, onTopicClick: (String) -> Unit, onSettingsClick: () -> Unit, modifier: Modifier = Modifier) {
    val featured = state.topics.firstOrNull { it.status == TopicStatus.AVAILABLE } ?: state.topics.firstOrNull { it.status == TopicStatus.PASSED }
    val remaining = state.topics.filter { it.id != featured?.id }
    Column(modifier = modifier.fillMaxSize().background(color = Background).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding)) {
        Spacer(modifier = Modifier.height(Dimens.Space24))
        VariantHeader(label = stringResource(R.string.screen_topic_label), onLabelClick = onSettingsClick, actionLabel = stringResource(R.string.nav_settings))
        Spacer(modifier = Modifier.height(Dimens.Space16))
        Text(text = stringResource(R.string.variant_topic_title), style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
        Text(text = stringResource(R.string.variant_topic_context, state.subjectName, state.gradeLabel), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        Spacer(modifier = Modifier.height(Dimens.Space16))
        featured?.let { topic ->
            Row(modifier = Modifier.fillMaxWidth().background(color = Selected, shape = HeroShape22).clickable { onTopicClick(topic.id) }.padding(Dimens.Space16), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                    Text(text = stringResource(R.string.variant_topic_available, topic.number), style = MaterialTheme.typography.labelSmall, color = Primary)
                    Text(text = topic.title, style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                    Text(text = stringResource(R.string.variant_topic_ready), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
                Image(painter = painterResource(R.drawable.figma_pencil), contentDescription = null, modifier = Modifier.size(width = Dimens.HeroIllustrationWidth, height = Dimens.HeroIllustrationHeight))
            }
        }
        Spacer(modifier = Modifier.height(Dimens.Space16))
        Text(text = stringResource(R.string.variant_topic_next), style = MaterialTheme.typography.titleLarge, color = TextPrimary)
        Text(text = stringResource(R.string.variant_topic_next_note), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        Spacer(modifier = Modifier.height(Dimens.Space12))
        remaining.chunked(2).forEach { rowItems ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                rowItems.forEach { topic ->
                    Column(modifier = Modifier.weight(1f).height(Dimens.TopicTileCurrentHeight).background(color = Surface, shape = MaterialTheme.shapes.medium).then(if (topic.status == TopicStatus.LOCKED) Modifier else Modifier.clickable { onTopicClick(topic.id) }).padding(Dimens.ClassCardPadding), verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(R.string.variant_topic_locked_mark), style = TopicTileTitle, color = TextSecondary)
                            StatusChip(status = topic.status)
                        }
                        Text(text = if (topic.status == TopicStatus.LOCKED) stringResource(R.string.variant_topic_number, topic.number) else topic.title, style = TopicTileTitle, color = TextSecondary)
                    }
                }
                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(Dimens.Space12))
        }
    }
}

