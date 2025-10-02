package com.example.checkers.gamelogic

import android.content.Context
import android.content.res.Configuration
import com.example.checkers.R
import java.util.Locale

private const val PREFS_NAME = "app_settings"
private const val KEY_LANGUAGE = "language"
private const val KEY_SOUND_VOLUME = "sound_volume"
private const val KEY_MUSIC_VOLUME = "music_volume"

fun saveLanguage(context: Context, languageCode: String) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
}

fun getSavedLanguage(context: Context): String {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getString(KEY_LANGUAGE, getCurrentLanguage(context)) ?: getCurrentLanguage(context)
}

fun saveSoundVolume(context: Context, volume: Float) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putFloat(KEY_SOUND_VOLUME, volume).apply()
}

fun getSavedSoundVolume(context: Context): Float {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getFloat(KEY_SOUND_VOLUME, 0.7f)
}

fun saveMusicVolume(context: Context, volume: Float) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putFloat(KEY_MUSIC_VOLUME, volume).apply()
}

fun getSavedMusicVolume(context: Context): Float {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getFloat(KEY_MUSIC_VOLUME, 0.5f)
}

fun getCurrentLanguage(context: Context): String {
    val config = context.resources.configuration
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        config.locales[0].language
    } else {
        config.locale.language
    }
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
}

fun getLocalizedString(key: String, context: Context): String {
    return when (key) {
        "language" -> context.getString(R.string.language)
        "sound_volume" -> context.getString(R.string.sound_volume)
        "music_volume" -> context.getString(R.string.music_volume)
        else -> key
    }
}

fun initializeAppLanguage(context: Context) {
    val savedLanguage = getSavedLanguage(context)
    if (savedLanguage != getCurrentLanguage(context)) {
        setAppLanguage(context, savedLanguage)
    }
}
