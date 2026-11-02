package com.galyaminsky.zachetenot.ui.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.components.BackButton
import com.galyaminsky.zachetenot.ui.components.TestActionBlock
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Border
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun PrepScreen(state: PrepUiState, onBack: () -> Unit, onStart: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().background(Background)) {
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, top = 8.dp)
        ) {
            BackButton(onClick = onBack)
            Spacer(Modifier.padding(top = 24.dp))
            Text(text = state.contextLine, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
            Spacer(Modifier.padding(top = 12.dp))
            Text(text = state.title, style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
            Spacer(Modifier.padding(top = 12.dp))
            Text(text = state.subtitle, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Spacer(Modifier.padding(top = 24.dp))
            ConditionsCard(state)
            Spacer(Modifier.padding(top = 24.dp))
            Row(Modifier.padding(horizontal = 4.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
                Icon(painter = painterResource(R.drawable.ic_lock), contentDescription = null, modifier = Modifier.size(20.dp), tint = Primary)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = state.warningTitle, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                    Text(text = state.warningText, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
            }
            Spacer(Modifier.padding(bottom = 8.dp))
        }
        TestActionBlock(
            buttonText = state.buttonText,
            buttonEnabled = true,
            onButtonClick = onStart,
            caption = state.caption,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
        )
    }
}

@Composable
private fun ConditionsCard(state: PrepUiState) {
    val shape = MaterialTheme.shapes.large
    Column(
        Modifier.fillMaxWidth().clip(shape).background(Surface)
            .border(Dimens.BorderWidth, Border, shape).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.ic_book_open), contentDescription = null, modifier = Modifier.size(24.dp), tint = Primary)
            Text(text = state.conditionsTitle, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
        }
        Text(text = state.conditionsStats, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
        Text(text = state.conditionsRules, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        Text(text = state.passNote, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
    }
}
