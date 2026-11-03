package com.galyaminsky.zachetenot.ui.screens.classselection

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

@Preview(showBackground = true)
@Composable
private fun SelectedClassPreview() {
    ZachetEnotTheme {
        ClassSelectionScreen(selectedGrade = 3, onGradeSelected = {}, onContinue = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun UnselectedClassPreview() {
    ZachetEnotTheme {
        ClassSelectionScreen(selectedGrade = null, onGradeSelected = {}, onContinue = {})
    }
}
