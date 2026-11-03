package com.galyaminsky.zachetenot.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.galyaminsky.zachetenot.R

val InterFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold)
)

val ClassHeadline = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.SemiBold, fontSize = 30.sp, lineHeight = 37.sp)
val ClassSubtitle = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Normal, fontSize = 15.sp, lineHeight = 18.sp)
val ClassNumber = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Bold, fontSize = 33.sp, lineHeight = 41.sp)
val ClassHintText = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Normal, fontSize = 13.sp, lineHeight = 16.sp)
val ProgressCounter = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Bold, fontSize = 19.sp, lineHeight = 24.sp)
val ProgressPercent = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 20.sp)
val FutureSubjectTitle = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.SemiBold, fontSize = 19.sp, lineHeight = 24.sp)
val PrepStatValue = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Bold, fontSize = 24.sp, lineHeight = 30.sp)
val TopicTileTitle = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, lineHeight = 20.sp)
val HintSymbol = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Bold, fontSize = 19.sp, lineHeight = 24.sp)
val CardSymbol = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Bold, fontSize = 25.sp, lineHeight = 31.sp)
val FactSymbol = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Bold, fontSize = 31.sp, lineHeight = 40.sp)
val DialogTitle = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Bold, fontSize = 24.sp, lineHeight = 30.sp)
val DialogMessage = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp)

private val DefaultTypography = Typography()

private fun TextStyle.withInter() = copy(fontFamily = InterFamily)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 46.sp,
        lineHeight = 48.sp,
        letterSpacing = (-2).sp
    ),
    displayMedium = DefaultTypography.displayMedium.withInter(),
    displaySmall = DefaultTypography.displaySmall.withInter(),
    headlineLarge = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp,
        lineHeight = 33.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 23.sp,
        lineHeight = 29.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = DefaultTypography.headlineSmall.withInter(),
    titleLarge = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = DefaultTypography.titleMedium.withInter(),
    titleSmall = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = DefaultTypography.bodyMedium.withInter(),
    bodySmall = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.sp
    ),
    labelLarge = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp
    )
)
