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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.checkers.R
import com.example.checkers.gamelogic.initializeAppLanguage
import com.example.checkers.ui.theme.CheckersTheme
import com.example.checkers.uiСomponents.ButtonPanel
import com.example.checkers.uiСomponents.SettingPanel
import com.example.checkers.uiСomponents.TopPanel

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeAppLanguage(this)

        enableEdgeToEdge()

        setContent {
            CheckersTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SettingScreen(innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun SettingScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF3A2A24),
                        Color(0xFF12140F)
                    ),
                    center = Offset.Unspecified,
                    radius = 800f
                )
            )
            .padding(innerPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopPanel(title = stringResource(R.string.settings))
        SettingPanel()
        ButtonPanel()
    }
}