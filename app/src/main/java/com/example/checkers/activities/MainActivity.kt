package com.example.checkers.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.checkers.uiСomponents.ButtonPanel
import com.example.checkers.ui.theme.CheckersTheme
import com.example.checkers.uiСomponents.GameBoard
import com.example.checkers.uiСomponents.TopPanel
import com.example.checkers.gamelogic.generateInitialPieces
import com.example.checkers.gamelogic.initializeAppLanguage
import com.example.checkers.uiСomponents.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        initializeAppLanguage(this)

        enableEdgeToEdge()

        setContent {
            CheckersTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val pieces = remember { generateInitialPieces() }
                    MainScreen(innerPadding = innerPadding, pieces = pieces)
                }
            }
        }
    }
}