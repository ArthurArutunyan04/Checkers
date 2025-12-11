package com.example.checkers.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.checkers.AppContainer
import com.example.checkers.gamelogic.generateInitialPieces
import com.example.checkers.ui.theme.*
import com.example.checkers.uiСomponents.MainScreen
import androidx.compose.ui.graphics.Color
import com.example.checkers.ui.theme.LightWhite

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receivedColorInt = intent.getIntExtra("activity_color", LightWhite.toArgb())
        val receivedColor = Color(receivedColorInt)
        val receivedFont = intent.getStringExtra("activity_name") ?: "Checkers"

        Log.d("MainActivity", "First launch - receivedFont: $receivedFont, receivedColor: $receivedColorInt")

        installSplashScreen()

        enableEdgeToEdge()

        setContent {
            ProvideThemeMode(this) {
                ProvideLanguage(this) {
                    val themeMode = LocalThemeMode.current.themeMode
                    CheckersTheme(themeMode = themeMode) {
                        val authViewModel = AppContainer.getAuthViewModel(this)
                        val statisticsViewModel = AppContainer.getStatisticsViewModel(this)

                        LaunchedEffect(Unit) {
                            authViewModel.loadInitialState(this@MainActivity, com.example.checkers.ui.theme.LanguageState(this@MainActivity))
                        }

                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            val pieces = remember { generateInitialPieces() }
                            MainScreen(
                                innerPadding = innerPadding,
                                pieces = pieces,
                                activityColor = receivedColor,
                                activityFont = receivedFont,
                                authViewModel = authViewModel,
                                statisticsViewModel = statisticsViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}