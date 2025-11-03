package com.example.checkers.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.checkers.gamelogic.generateInitialPieces
import com.example.checkers.ui.theme.CheckersTheme
import com.example.checkers.ui.theme.LocalThemeMode
import com.example.checkers.ui.theme.ProvideLanguage
import com.example.checkers.ui.theme.ProvideThemeMode
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
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            val pieces = remember { generateInitialPieces() }
                            MainScreen(
                                innerPadding = innerPadding,
                                pieces = pieces,
                                activityColor = receivedColor,
                                activityFont = receivedFont
                            )
                        }
                    }
                }
            }
        }
    }
}