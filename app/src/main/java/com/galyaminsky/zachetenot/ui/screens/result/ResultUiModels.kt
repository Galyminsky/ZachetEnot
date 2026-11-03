package com.galyaminsky.zachetenot.ui.screens.result

import com.galyaminsky.zachetenot.ui.components.MistakeUi

data class ResultUiState(
    val contextLine: String,
    val headerLabel: String,
    val title: String,
    val subtitle: String,
    val passed: Boolean,
    val verdictTitle: String,
    val verdictNote: String?,
    val correctText: String,
    val percentText: String,
    val correctLabel: String,
    val completion: Float,
    val completionText: String,
    val errorsValue: String,
    val errorsLabel: String,
    val timeValue: String,
    val timeLabel: String,
    val message: String,
    val reviewButton: String,
    val retryButton: String,
    val listButton: String,
    val failedNote: String? = null
)

data class ReviewSuccessUi(val title: String, val text: String, val note: String)

data class ReviewUiState(
    val contextLine: String,
    val title: String,
    val subtitle: String,
    val statsLine: String,
    val timeLine: String,
    val mistakes: List<MistakeUi>,
    val footerMessage: String?,
    val success: ReviewSuccessUi?,
    val buttonText: String
)
