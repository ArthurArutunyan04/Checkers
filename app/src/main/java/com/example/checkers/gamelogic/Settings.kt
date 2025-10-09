package com.example.checkers.gamelogic

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import com.example.checkers.R
import com.example.checkers.ui.theme.LocalLanguage
import java.util.Locale


private const val PREFS_NAME = "app_settings"
private const val KEY_LANGUAGE = "language"
private const val KEY_SOUND_VOLUME = "sound_volume"
private const val KEY_MUSIC_VOLUME = "music_volume"
private const val KEY_THEME_MODE = "theme_mode"

fun saveLanguage(context: Context, languageCode: String) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
    Log.d("Preferences", "Saved language: $languageCode")
}

fun getSavedLanguage(context: Context): String {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val language = prefs.getString(KEY_LANGUAGE, Locale.getDefault().language) ?: Locale.getDefault().language
    Log.d("Preferences", "Retrieved language: $language")
    return language
}

fun saveSoundVolume(context: Context, volume: Float) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putFloat(KEY_SOUND_VOLUME, volume).apply()
    Log.d("Preferences", "Saved sound volume: $volume")
}

fun getSavedSoundVolume(context: Context): Float {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val volume = prefs.getFloat(KEY_SOUND_VOLUME, 0.7f)
    Log.d("Preferences", "Retrieved sound volume: $volume")
    return volume
}

fun saveMusicVolume(context: Context, volume: Float) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putFloat(KEY_MUSIC_VOLUME, volume).apply()
    Log.d("Preferences", "Saved music volume: $volume")
}

fun getSavedMusicVolume(context: Context): Float {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val volume = prefs.getFloat(KEY_MUSIC_VOLUME, 0.5f)
    Log.d("Preferences", "Retrieved music volume: $volume")
    return volume
}

fun saveThemeMode(context: Context, themeMode: ThemeMode) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putString(KEY_THEME_MODE, themeMode.name).apply()
    Log.d("Preferences", "Saved theme mode: ${themeMode.name}")
    Log.d("Preferences", "Verified saved theme mode: ${prefs.getString(KEY_THEME_MODE, null)}")
}

fun getSavedThemeMode(context: Context): ThemeMode {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val mode = prefs.getString(KEY_THEME_MODE, ThemeMode.DARK.name)
    val themeMode = try {
        ThemeMode.valueOf(mode ?: ThemeMode.DARK.name)
    } catch (e: IllegalArgumentException) {
        ThemeMode.DARK
    }
    Log.d("Preferences", "Retrieved theme mode: $themeMode")
    return themeMode
}

@Composable
fun getCurrentLanguageDisplayName(context: Context): String {
    val savedLang = LocalLanguage.current.languageCode
    return when (savedLang) {
        "ru" -> LocalLanguage.current.getLocalizedString(context, R.string.russian)
        else -> LocalLanguage.current.getLocalizedString(context, R.string.english)
    }
}