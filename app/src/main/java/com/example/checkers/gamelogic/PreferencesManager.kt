package com.example.checkers.gamelogic

import android.content.Context


class PreferencesManager(private val context: Context) {
    companion object {
        private const val PREFS_NAME = "checkers_prefs"
        private const val THEME_MODE_KEY = "theme_mode"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setThemeMode(themeMode: ThemeMode) {
        sharedPreferences.edit().putString(THEME_MODE_KEY, themeMode.name).apply()
    }

    fun getThemeMode(): ThemeMode {
        val mode = sharedPreferences.getString(THEME_MODE_KEY, ThemeMode.SYSTEM.name)
        return try {
            ThemeMode.valueOf(mode ?: ThemeMode.SYSTEM.name)
        } catch (e: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
    }
}