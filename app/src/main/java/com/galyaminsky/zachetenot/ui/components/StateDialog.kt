package com.galyaminsky.zachetenot.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.galyaminsky.zachetenot.ui.theme.DangerAction
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.DialogShape26
import com.galyaminsky.zachetenot.ui.theme.DialogHeroShape18
import com.galyaminsky.zachetenot.ui.theme.DialogTitle
import com.galyaminsky.zachetenot.ui.theme.DialogMessage
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextPrimary
import com.galyaminsky.zachetenot.ui.theme.TextSecondary

@Composable
fun StateDialog(
    @DrawableRes illustration: Int,
    badge: String,
    title: String,
    message: String,
    primaryLabel: String,
    onPrimary: () -> Unit,
    onDismiss: () -> Unit,
    heroColor: Color,
    modifier: Modifier = Modifier,
    secondaryLabel: String? = null,
    onSecondary: (() -> Unit)? = null,
    secondaryFirst: Boolean = false,
    destructive: Boolean = false
) {
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Column(modifier = modifier.fillMaxWidth().padding(horizontal = Dimens.ScreenHorizontalPadding).background(color = Surface, shape = DialogShape26).padding(Dimens.Space16), verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
            Column(modifier = Modifier.fillMaxWidth().background(color = heroColor, shape = DialogHeroShape18).padding(top = Dimens.DialogHeroTopPadding, bottom = Dimens.Space16, start = Dimens.Space16, end = Dimens.Space16), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                Image(painter = painterResource(illustration), contentDescription = null)
                Text(text = badge, style = MaterialTheme.typography.labelSmall, color = if (destructive) DangerAction else Primary, modifier = Modifier.background(color = Surface, MaterialTheme.shapes.medium).padding(Dimens.Space8))
                Text(text = title, style = DialogTitle, color = TextPrimary, textAlign = TextAlign.Center)
                Text(text = message, style = DialogMessage, color = TextSecondary, textAlign = TextAlign.Center)
            }
            if (secondaryFirst && secondaryLabel != null && onSecondary != null) AppButton(text = secondaryLabel, onClick = onSecondary, modifier = Modifier.fillMaxWidth(), style = AppButtonStyle.Secondary, height = Dimens.DialogButtonHeight)
            if (destructive) {
                Button(onClick = onPrimary, modifier = Modifier.fillMaxWidth().height(Dimens.DialogButtonHeight), colors = ButtonDefaults.buttonColors(containerColor = DangerAction, contentColor = Surface), shape = MaterialTheme.shapes.medium) {
                    Text(text = primaryLabel, style = MaterialTheme.typography.labelLarge, color = Surface)
                }
            } else AppButton(text = primaryLabel, onClick = onPrimary, modifier = Modifier.fillMaxWidth(), height = Dimens.DialogButtonHeight)
            if (!secondaryFirst && secondaryLabel != null && onSecondary != null) AppButton(text = secondaryLabel, onClick = onSecondary, modifier = Modifier.fillMaxWidth(), style = AppButtonStyle.Secondary, height = Dimens.DialogButtonHeight)
        }
    }
}
