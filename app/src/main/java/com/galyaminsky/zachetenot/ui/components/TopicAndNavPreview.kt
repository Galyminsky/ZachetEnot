package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.domain.progress.TopicStatus
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

@Preview(widthDp = 402, heightDp = 620, showBackground = true)
@Composable
private fun TopicAndNavPreview() {
    ZachetEnotTheme {
        Column(modifier = Modifier.fillMaxSize().background(Background)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(Dimens.ScreenHorizontalPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.Space12)
            ) {
                TopicCard(3, "Состав слова", "Корень, приставка, суффикс и окончание.", TopicStatus.AVAILABLE, {})
                TopicCard(2, "Корень слова", "Однокоренные слова и их общий корень.", TopicStatus.PASSED, {})
                TopicCard(4, "Правописание суффиксов", "Скоро появится.", TopicStatus.LOCKED, {})
            }
            Spacer(modifier = Modifier.weight(1f))
            AppNavigationBar(
                items = listOf(
                    NavItem(stringResource(R.string.nav_topics), R.drawable.ic_layers),
                    NavItem(stringResource(R.string.nav_progress), R.drawable.ic_chart),
                    NavItem(stringResource(R.string.nav_settings), R.drawable.ic_settings)
                ),
                selectedIndex = 0,
                onSelect = {}
            )
        }
    }
}
