package com.example.checkers.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.checkers.ui.theme.CheckersTheme
import com.example.checkers.ui.theme.LocalThemeMode
import com.example.checkers.ui.theme.ProvideLanguage
import com.example.checkers.ui.theme.ProvideThemeMode
import com.example.checkers.uiСomponents.SettingScreen

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ProvideThemeMode(this) {
                ProvideLanguage(this) {
                    val themeMode = LocalThemeMode.current.themeMode
                    CheckersTheme(themeMode = themeMode) {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            SettingScreen(innerPadding = innerPadding)
                        }
                    }
                }
            }
        }
    }
}