package com.galyaminsky.zachetenot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galyaminsky.zachetenot.R
import com.galyaminsky.zachetenot.ui.theme.Background
import com.galyaminsky.zachetenot.ui.theme.Dimens
import com.galyaminsky.zachetenot.ui.theme.Disabled
import com.galyaminsky.zachetenot.ui.theme.Primary
import com.galyaminsky.zachetenot.ui.theme.SelectedStrong
import com.galyaminsky.zachetenot.ui.theme.Surface
import com.galyaminsky.zachetenot.ui.theme.TextDisabled
import com.galyaminsky.zachetenot.ui.theme.TextSecondary
import com.galyaminsky.zachetenot.ui.theme.ZachetEnotTheme

@Preview(widthDp = 402, heightDp = 640, showBackground = true)
@Composable
private fun ResourcesPreview() {
    ZachetEnotTheme {
        Column(
            modifier = Modifier
                .width(402.dp)
                .background(Background)
                .padding(
                    horizontal = Dimens.ScreenHorizontalPadding,
                    vertical = Dimens.Space24
                ),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space16)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text("Иконки 24 dp", color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                        Box(Modifier.size(40.dp).background(Surface, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Icon(painterResource(R.drawable.ic_arrow_left), contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                        }
                        Box(Modifier.size(40.dp).background(Surface, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Icon(painterResource(R.drawable.ic_arrow_right), contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                        }
                        Box(Modifier.size(40.dp).background(Surface, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Icon(painterResource(R.drawable.ic_check), contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                        }
                        Box(Modifier.size(40.dp).background(Surface, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Icon(painterResource(R.drawable.ic_lock), contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                        }
                        Box(Modifier.size(40.dp).background(Surface, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Icon(painterResource(R.drawable.ic_book_open), contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                        Box(Modifier.size(40.dp).background(Surface, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Icon(painterResource(R.drawable.ic_calculator), contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                        }
                        Box(Modifier.size(40.dp).background(Surface, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Icon(painterResource(R.drawable.ic_globe), contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                        }
                        Box(Modifier.size(40.dp).background(Surface, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Icon(painterResource(R.drawable.ic_settings), contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                        }
                        Box(Modifier.size(40.dp).background(Surface, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Icon(painterResource(R.drawable.ic_layers), contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                        }
                        Box(Modifier.size(40.dp).background(Surface, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Icon(painterResource(R.drawable.ic_chart), contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text("Малые иконки", color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                    Box(Modifier.size(40.dp).background(Primary, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                        Icon(painterResource(R.drawable.ic_check_small), contentDescription = null, tint = Surface, modifier = Modifier.size(15.dp))
                    }
                    Box(Modifier.size(40.dp).background(Disabled, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                        Icon(painterResource(R.drawable.ic_lock_small), contentDescription = null, tint = TextDisabled, modifier = Modifier.size(12.dp))
                    }
                    Box(Modifier.size(40.dp).background(SelectedStrong, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                        Icon(painterResource(R.drawable.ic_play_small), contentDescription = null, tint = Primary, modifier = Modifier.size(12.dp))
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text("Иллюстрации 104×88", color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)) {
                    Image(painterResource(R.drawable.illustration_school), contentDescription = null, modifier = Modifier.size(104.dp, 88.dp))
                    Image(painterResource(R.drawable.illustration_locked), contentDescription = null, modifier = Modifier.size(104.dp, 88.dp))
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Dimens.Space8)) {
                Text("Логотип", color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                Image(painterResource(R.drawable.ic_logo), contentDescription = null, modifier = Modifier.size(34.dp))
            }
        }
    }
}
