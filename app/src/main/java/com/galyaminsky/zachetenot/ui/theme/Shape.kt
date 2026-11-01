package com.galyaminsky.zachetenot.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Шкала UI Kit: 8/16/24/капсула; 12 используется в плитках иконок и бейдже номера темы (решение владельца: оставить в шкале).
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

val CapsuleShape = RoundedCornerShape(percent = 50)
