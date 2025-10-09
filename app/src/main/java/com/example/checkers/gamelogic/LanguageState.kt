package com.example.checkers.ui.theme

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.checkers.gamelogic.getSavedLanguage
import java.util.Locale

class LanguageState(context: Context) {
    var languageCode by mutableStateOf(getSavedLanguage(context))
    var locale by mutableStateOf(Locale(languageCode))

    fun getLocalizedString(context: Context, resId: Int, vararg args: Any): String {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration).resources.getString(resId, *args)
    }

    fun updateLanguage(newLanguageCode: String) {
        languageCode = newLanguageCode
        locale = Locale(newLanguageCode)
    }
}

val LocalLanguage = compositionLocalOf<LanguageState> { error("No LanguageState provided") }

@Composable
fun ProvideLanguage(context: Context, content: @Composable () -> Unit) {
    val languageState = remember { LanguageState(context) }
    CompositionLocalProvider(LocalLanguage provides languageState) {
        content()
    }
}