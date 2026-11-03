package com.galyaminsky.zachetenot.ui.screens.status

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.components.AppButton
import com.galyaminsky.zachetenot.ui.components.VariantHeader
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.InfoContainer
import com.galyaminsky.zachetenot.ui.theme.Selected
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

enum class StatusKind { Unavailable, NoContent }

@Composable
fun StatusScreen(kind: StatusKind, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val unavailable = kind == StatusKind.Unavailable
    Column(modifier = modifier.fillMaxSize().background(color = Background).padding(horizontal = Dimens.ScreenHorizontalPadding)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
            Spacer(modifier = Modifier.padding(Dimens.Space4))
            VariantHeader(label = stringResource(R.string.status_screen_label))
            Column(modifier = Modifier.fillMaxWidth().background(color = if (unavailable) Selected else InfoContainer, shape = MaterialTheme.shapes.large).padding(Dimens.Space24), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(Dimens.Space16)) {
                Image(painter = painterResource(if (unavailable) R.drawable.ic_status_lock else R.drawable.ic_status_document), contentDescription = null)
                Text(text = stringResource(if (unavailable) R.string.status_unavailable_badge else R.string.status_no_content_badge), style = MaterialTheme.typography.labelSmall, color = Primary, modifier = Modifier.background(color = Surface, shape = MaterialTheme.shapes.medium).padding(Dimens.Space8))
                Text(text = stringResource(if (unavailable) R.string.status_unavailable_title else R.string.status_no_content_title), style = MaterialTheme.typography.headlineLarge, color = TextPrimary, textAlign = TextAlign.Center)
                Text(text = stringResource(if (unavailable) R.string.status_unavailable_message else R.string.status_no_content_message), style = MaterialTheme.typography.bodyLarge, color = TextSecondary, textAlign = TextAlign.Center)
            }
            Row(modifier = Modifier.fillMaxWidth().background(color = Surface, shape = MaterialTheme.shapes.large).padding(Dimens.Space16), horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text(text = stringResource(R.string.variant_sparkle), style = MaterialTheme.typography.titleLarge, color = Primary)
                Text(text = stringResource(if (unavailable) R.string.status_unavailable_note else R.string.status_no_content_note), style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
        }
        AppButton(text = stringResource(if (unavailable) R.string.status_back else R.string.status_to_topics), onClick = onBack, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.padding(Dimens.Space8))
    }
}

@Preview(showBackground = true)
@Composable
private fun UnavailablePreview() { ZachetEnotTheme { StatusScreen(kind = StatusKind.Unavailable, onBack = {}) } }

@Preview(showBackground = true)
@Composable
private fun NoContentPreview() { ZachetEnotTheme { StatusScreen(kind = StatusKind.NoContent, onBack = {}) } }
