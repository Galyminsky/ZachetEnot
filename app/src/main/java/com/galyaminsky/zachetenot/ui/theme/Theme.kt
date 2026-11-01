package com.galyaminsky.zachetenot.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Surface,
    primaryContainer = Selected,
    onPrimaryContainer = TextPrimary,
    secondary = Accent,
    onSecondary = TextPrimary,
    tertiary = Success,
    onTertiary = Surface,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = Disabled,
    onSurfaceVariant = TextSecondary,
    outline = Border,
    outlineVariant = Border
)

@Composable
fun ZachetEnotTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
