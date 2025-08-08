package com.xavier.moneytaa.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val GradientStart = Color(0xFF8A2BE2) // A shade of blue-violet
val GradientEnd = Color(0xFFFFA07A)   // A shade of light salmon



val LightColorScheme = lightColorScheme(
    primary = Color(0xFF8E56FF),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFFFA9F9),
    background = Color(0xFFF9F5FF),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFEDE7F6),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    error = Color(0xFFFF4D4F),
    onError = Color(0xFFFFFFFF),
)

// 🌙 Dark color scheme
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFB388FF),
    onPrimary = Color(0xFF1C1B1F),
    secondary = Color(0xFFFFA9F9),
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF2C2C2E),
    surfaceVariant = Color(0xFF3A3A3C),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    error = Color(0xFFFF4D4F),
    onError = Color(0xFF1C1B1F),
)

// ✅ Optional success and info colors (for tokens or custom composables)
val SuccessColor = Color(0xFF00C88F)
val InfoColor = Color(0xFF579AFF)
