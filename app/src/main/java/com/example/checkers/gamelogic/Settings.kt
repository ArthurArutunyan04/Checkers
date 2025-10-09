package com.example.checkers.gamelogic

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.example.checkers.R
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
    val language = prefs.getString(KEY_LANGUAGE, getCurrentLanguage(context)) ?: getCurrentLanguage(context)
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
}

fun getSavedThemeMode(context: Context): ThemeMode {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val mode = prefs.getString(KEY_THEME_MODE, ThemeMode.SYSTEM.name)
    val themeMode = try {
        ThemeMode.valueOf(mode ?: ThemeMode.SYSTEM.name)
    } catch (e: IllegalArgumentException) {
        ThemeMode.SYSTEM
    }
    Log.d("Preferences", "Retrieved theme mode: $themeMode")
    return themeMode
}

fun getCurrentLanguage(context: Context): String {
    val config = context.resources.configuration
    val language = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        config.locales[0].language
    } else {
        config.locale.language
    }
    Log.d("Preferences", "Current system language: $language")
    return language
}

fun getCurrentLanguageDisplayName(context: Context): String {
    val savedLang = getSavedLanguage(context)
    return when (savedLang) {
        "ru" -> context.getString(R.string.russian)
        else -> context.getString(R.string.english)
    }
}

fun setAppLanguage(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val resources = context.resources
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)

    resources.updateConfiguration(configuration, resources.displayMetrics)

    saveLanguage(context, languageCode)
    Log.d("Preferences", "Set app language: $languageCode")
}

fun getLocalizedString(key: String, context: Context): String {
    return when (key) {
        "language" -> context.getString(R.string.language)
        "sound_volume" -> context.getString(R.string.sound_volume)
        "music_volume" -> context.getString(R.string.music_volume)
        "theme_mode" -> context.getString(R.string.theme_mode)
        else -> key
    }
}

fun initializeAppLanguage(context: Context) {
    val savedLanguage = getSavedLanguage(context)
    if (savedLanguage != getCurrentLanguage(context)) {
        setAppLanguage(context, savedLanguage)
    }
}