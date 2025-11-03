package com.example.checkers.ui.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.checkers.gamelogic.ThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = DarkOrange,
    secondary = Beige,
    tertiary = Green,
    background = Field,
    surface = Field,
    onPrimary = LightWhite,
    onSecondary = LightWhite,
    onTertiary = LightWhite,
    onBackground = LightGray,
    onSurface = LightGray,
    primaryContainer = ButtonDefaultsColor,
    secondaryContainer = TopBarColor,
    surfaceVariant = GridBack,
)

private val LightColorScheme = lightColorScheme(
    primary = LightDarkOrange,
    secondary = LightBeige,
    tertiary = LightGreen,
    background = LightWhite,
    surface = LightField,
    onPrimary = DarkBrown,
    onSecondary = DarkBrown,
    onTertiary = DarkBrown,
    onBackground = GrayDef,
    onSurface = GrayDef,
    primaryContainer = LightButtonDefaultsColor,
    secondaryContainer = LightTopBarColor,
    surfaceVariant = LightGridBack,
)

@Composable
fun CheckersTheme(
    themeMode: ThemeMode = ThemeMode.DARK,
    content: @Composable () -> Unit
) {
    val isDarkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }
    Log.d("CheckersTheme", "Applying theme: $themeMode, isDarkTheme: $isDarkTheme")

    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

data class ExtendedColorScheme(
    val colorScheme: ColorScheme,
    val gameColor: Color
)