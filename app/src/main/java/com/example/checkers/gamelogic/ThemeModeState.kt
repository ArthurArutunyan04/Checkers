package com.example.checkers.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.checkers.gamelogic.ThemeMode

val LocalThemeMode = compositionLocalOf<ThemeModeState> { error("No ThemeMode provided") }

class ThemeModeState(context: android.content.Context) {
    var themeMode by mutableStateOf(com.example.checkers.gamelogic.getSavedThemeMode(context))
}

@Composable
fun ProvideThemeMode(context: android.content.Context, content: @Composable () -> Unit) {
    val themeModeState = ThemeModeState(context)
    androidx.compose.runtime.CompositionLocalProvider(LocalThemeMode provides themeModeState) {
        content()
    }
}