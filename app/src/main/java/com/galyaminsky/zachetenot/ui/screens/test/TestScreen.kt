package com.galyaminsky.zachetenot.ui.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.ui.components.ChoiceOption
import com.galyaminsky.zachetenot.ui.components.MatchingRow
import com.galyaminsky.zachetenot.ui.components.SequenceItem
import com.galyaminsky.zachetenot.ui.components.TaskCard
import com.galyaminsky.zachetenot.ui.components.TestActionBlock
import com.galyaminsky.zachetenot.ui.components.TestProgressHeader
import com.galyaminsky.zachetenot.ui.components.TestTopBar
import com.galyaminsky.zachetenot.ui.components.TextAnswerField
import com.galyaminsky.zachetenot.ui.theme.Background
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
    Column(modifier.fillMaxSize().background(Background)) {
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, top = 8.dp)
        ) {
            TestTopBar(timeText = state.header.timeText, onExit = onExit)
            Spacer(Modifier.padding(top = 24.dp))
            TestProgressHeader(
                contextLine = state.header.contextLine,
                title = state.header.title,
                questionNumber = state.header.questionNumber,
                total = state.header.total,
                answered = state.header.answered
            )
            Spacer(Modifier.padding(top = 24.dp))
            TaskCard(label = state.taskLabel, text = state.taskText)
            Spacer(Modifier.padding(top = 16.dp))
            AnswerArea(state, onChoiceSelected, onTextChange, onPartClick, onPairClick)
            Spacer(Modifier.padding(bottom = 8.dp))
        }
        TestActionBlock(
            buttonText = state.buttonText,
            buttonEnabled = state.phase != AnswerPhase.Empty,
            onButtonClick = onAction,
            caption = state.caption,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
            showLockedNotice = state.phase == AnswerPhase.Locked
        )
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
        is AnswerUi.TextInput -> TextAnswerField(
            label = answer.fieldLabel,
            value = answer.value,
            onValueChange = onTextChange,
            placeholder = answer.placeholder,
            hint = answer.hint,
            locked = locked
        )
        is AnswerUi.Sequence -> {
            Text(text = answer.instruction, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
            Spacer(Modifier.padding(top = 12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                val selected = answer.chosenOrder.toSet()
                answer.chosenOrder.forEachIndexed { position, index ->
                    SequenceItem(text = answer.parts[index], position = position + 1, locked = locked, onClick = { onPartClick(index) })
                }
                answer.parts.forEachIndexed { index, part ->
                    if (index !in selected) SequenceItem(text = part, position = null, locked = locked, onClick = { onPartClick(index) })
                }
            }
            Spacer(Modifier.padding(top = 12.dp))
            Text(text = answer.footer, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
        }
        is AnswerUi.Matching -> {
            Text(text = answer.instruction, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
            Spacer(Modifier.padding(top = 12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                answer.prompts.forEachIndexed { index, prompt ->
                    MatchingRow(prompt = prompt, chosen = answer.chosen[index], locked = locked, onClick = { onPairClick(index) })
                }
            }
        }
    }
}
