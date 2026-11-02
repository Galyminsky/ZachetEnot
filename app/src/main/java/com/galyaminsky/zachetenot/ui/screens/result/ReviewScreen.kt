package com.galyaminsky.zachetenot.ui.screens.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.components.BackButton
import com.galyaminsky.zachetenot.ui.components.MistakeCard
import com.galyaminsky.zachetenot.ui.components.ReviewSuccessCard
import com.galyaminsky.zachetenot.ui.components.AppButton
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun ReviewScreen(state: ReviewUiState, onBack: () -> Unit, onList: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().background(Background)) {
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, top = 8.dp)
        ) {
            BackButton(onClick = onBack)
            Spacer(Modifier.height(24.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = state.contextLine, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                Text(text = state.title, style = MaterialTheme.typography.headlineLarge, color = TextPrimary)
                Text(text = state.subtitle, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                Text(text = state.statsLine, style = MaterialTheme.typography.labelMedium.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold), color = Primary)
                Text(text = state.timeLine, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
            }
            Spacer(Modifier.height(24.dp))
            if (state.mistakes.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    state.mistakes.forEach { mistake -> MistakeCard(mistake = mistake) }
                }
                if (state.footerMessage != null) {
                    Spacer(Modifier.height(24.dp))
                    Text(text = state.footerMessage, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                }
            } else if (state.success != null) {
                ReviewSuccessCard(title = state.success.title, text = state.success.text, note = state.success.note)
            }
            Spacer(Modifier.height(8.dp))
        }
        Column(Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 12.dp)) {
            AppButton(text = state.buttonText, onClick = onList, modifier = Modifier.fillMaxWidth(), trailingIcon = R.drawable.ic_arrow_right)
        }
    }
}
