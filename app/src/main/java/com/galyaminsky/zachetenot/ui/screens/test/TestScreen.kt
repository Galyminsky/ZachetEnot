package com.galyaminsky.zachetenot.ui.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.components.AppButton
import com.galyaminsky.zachetenot.ui.components.StepProgressBar
import com.galyaminsky.zachetenot.ui.components.ChoiceOption
import com.galyaminsky.zachetenot.ui.components.MatchingRow
import com.galyaminsky.zachetenot.ui.components.SequenceItem
import com.galyaminsky.zachetenot.ui.components.TaskCard
import com.galyaminsky.zachetenot.ui.components.TestActionBlock
import com.galyaminsky.zachetenot.ui.components.TestProgressHeader
import com.galyaminsky.zachetenot.ui.components.TestTopBar
import com.galyaminsky.zachetenot.ui.components.TextAnswerField
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Border
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun TestScreen(
    state: TestScreenState,
    onExit: () -> Unit,
    onAction: () -> Unit,
    onChoiceSelected: (Int) -> Unit,
    onTextChange: (String) -> Unit,
    onPartClick: (Int) -> Unit,
    onPairClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().background(color = Background)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = Dimens.ScreenHorizontalPadding), verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
            Spacer(modifier = Modifier.height(Dimens.Space8))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Space8), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(onClick = onExit)) {
                    Icon(painter = painterResource(R.drawable.ic_arrow_left), contentDescription = null, tint = TextPrimary)
                    Text(text = stringResource(R.string.variant_test_title), style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                }
                Text(text = state.header.timeText, style = MaterialTheme.typography.titleLarge, color = Primary, modifier = Modifier.background(color = Selected, MaterialTheme.shapes.large).padding(Dimens.Space8))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = stringResource(R.string.question_n_of, state.header.questionNumber, state.header.total), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                Text(text = stringResource(R.string.figma_count_slash, state.header.questionNumber, state.header.total), style = MaterialTheme.typography.titleLarge, color = Primary)
            }
            StepProgressBar(progress = state.header.questionNumber.toFloat() / state.header.total, trackColor = Border, fillColor = Primary)
            Column(modifier = Modifier.fillMaxWidth().background(color = Surface, MaterialTheme.shapes.large).padding(Dimens.Space20), verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
                Text(text = stringResource(R.string.variant_test_task, state.header.questionNumber), style = MaterialTheme.typography.labelLarge, color = Primary, modifier = Modifier.background(color = Selected, MaterialTheme.shapes.medium).padding(Dimens.Space8))
                Text(text = state.taskText, style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
                Text(text = stringResource(when (state.answer) { is AnswerUi.TextInput -> R.string.variant_test_text_hint; is AnswerUi.Matching -> R.string.variant_test_matching_hint; is AnswerUi.Sequence -> R.string.variant_test_sequence_hint; else -> R.string.variant_test_hint }), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
            when (val answer = state.answer) {
                is AnswerUi.Choice -> answer.options.forEachIndexed { index, option ->
                    val label = stringResource(listOf(R.string.choice_letter_a, R.string.choice_letter_b, R.string.choice_letter_c, R.string.choice_letter_d).getOrElse(index) { R.string.choice_letter_d })
                    Row(modifier = Modifier.fillMaxWidth().height(Dimens.Space48 + Dimens.Space16).clip(MaterialTheme.shapes.large).background(color = if (answer.selectedIndex == index) Selected else Surface).border(width = Dimens.BorderWidth, color = if (answer.selectedIndex == index) Primary else Border, shape = MaterialTheme.shapes.large).clickable(enabled = state.phase != AnswerPhase.Locked) { onChoiceSelected(index) }.padding(Dimens.Space16), horizontalArrangement = Arrangement.spacedBy(Dimens.Space12), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = label, style = MaterialTheme.typography.titleLarge, color = TextSecondary, modifier = Modifier.background(color = Background, MaterialTheme.shapes.small).padding(Dimens.Space8))
                        Text(text = option, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                    }
                }
                else -> AnswerArea(state, onChoiceSelected, onTextChange, onPartClick, onPairClick)
            }
            Text(text = stringResource(when (state.answer) { is AnswerUi.TextInput -> R.string.variant_test_text_notice; is AnswerUi.Matching -> R.string.variant_test_matching_notice; is AnswerUi.Sequence -> R.string.variant_test_sequence_notice; else -> R.string.variant_test_notice }), style = MaterialTheme.typography.bodyLarge, color = TextSecondary, modifier = Modifier.fillMaxWidth().background(color = Selected, MaterialTheme.shapes.large).padding(Dimens.Space16))
        }
        AppButton(text = if (state.phase == AnswerPhase.Locked) state.buttonText else stringResource(R.string.variant_test_confirm), onClick = onAction, enabled = state.phase != AnswerPhase.Empty, modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.ScreenHorizontalPadding, vertical = Dimens.Space16))
    }
}

@Composable
private fun AnswerArea(
    state: TestScreenState,
    onChoiceSelected: (Int) -> Unit,
    onTextChange: (String) -> Unit,
    onPartClick: (Int) -> Unit,
    onPairClick: (Int) -> Unit
) {
    val locked = state.phase == AnswerPhase.Locked
    when (val answer = state.answer) {
        is AnswerUi.Choice -> Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            answer.options.forEachIndexed { index, option ->
                ChoiceOption(text = option, selected = index == answer.selectedIndex, locked = locked, onClick = { onChoiceSelected(index) })
            }
        }
        is AnswerUi.TextInput -> Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
            BasicTextField(value = answer.value, onValueChange = onTextChange, enabled = !locked, singleLine = true, textStyle = MaterialTheme.typography.titleLarge.copy(color = TextPrimary), cursorBrush = SolidColor(Primary), modifier = Modifier.fillMaxWidth().height(Dimens.Space48 + Dimens.Space12).background(color = Surface, MaterialTheme.shapes.large).border(width = Dimens.BorderWidth, color = Border, shape = MaterialTheme.shapes.large).padding(Dimens.Space16), decorationBox = { inner -> Box(contentAlignment = Alignment.CenterStart) { if (answer.value.isEmpty()) Text(text = answer.placeholder, style = MaterialTheme.typography.titleLarge, color = TextSecondary); inner() } })
            Text(text = answer.hint, style = MaterialTheme.typography.bodyLarge, color = TextSecondary, modifier = Modifier.fillMaxWidth().background(color = Selected, MaterialTheme.shapes.medium).padding(Dimens.Space12))
        }
        is AnswerUi.Sequence -> Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
            val order = answer.chosenOrder + answer.parts.indices.filter { it !in answer.chosenOrder }
            order.forEachIndexed { position, index ->
                Row(modifier = Modifier.fillMaxWidth().height(Dimens.Space48).background(color = Surface, MaterialTheme.shapes.large).border(width = Dimens.BorderWidth, color = Border, shape = MaterialTheme.shapes.large).clickable(enabled = !locked) { onPartClick(index) }.padding(Dimens.Space12), horizontalArrangement = Arrangement.spacedBy(Dimens.Space16), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.variant_sequence_number, position + 1), style = MaterialTheme.typography.titleLarge, color = Primary)
                    Text(text = answer.parts[index], style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                }
            }
        }
        is AnswerUi.Matching -> Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                Text(text = stringResource(R.string.variant_matching_words), style = MaterialTheme.typography.labelLarge, color = TextSecondary, modifier = Modifier.weight(1f))
                Text(text = stringResource(R.string.variant_matching_types), style = MaterialTheme.typography.labelLarge, color = TextSecondary, modifier = Modifier.weight(1f))
            }
            answer.prompts.forEachIndexed { index, prompt ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                    Text(text = prompt, style = MaterialTheme.typography.bodyLarge, color = TextPrimary, modifier = Modifier.weight(1f).background(color = Surface, MaterialTheme.shapes.medium).border(width = Dimens.BorderWidth, color = Border, shape = MaterialTheme.shapes.medium).clickable(enabled = !locked) { onPairClick(index) }.padding(Dimens.Space12))
                    Text(text = answer.chosen.getOrNull(index).orEmpty(), style = MaterialTheme.typography.bodyLarge, color = TextPrimary, modifier = Modifier.weight(1f).background(color = Surface, MaterialTheme.shapes.medium).border(width = Dimens.BorderWidth, color = Border, shape = MaterialTheme.shapes.medium).clickable(enabled = !locked) { onPairClick(index) }.padding(Dimens.Space12))
                }
            }
        }
    }
}

