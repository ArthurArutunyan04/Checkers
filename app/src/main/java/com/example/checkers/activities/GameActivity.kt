package com.example.checkers.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.checkers.ui.theme.CheckersTheme
import com.example.checkers.uiСomponents.GameBoard
import com.example.checkers.uiСomponents.TopPanel
import com.example.checkers.gamelogic.generateStandardSetup

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CheckersTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val pieces = remember { generateStandardSetup() }
                    GameScreen(innerPadding = innerPadding, pieces = pieces)
                }
            }
        }
    }
}

@Composable
fun GameScreen(innerPadding: PaddingValues, pieces: Map<Int, Int>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF3A2A24),
                        Color(0xFF12140F)
                    ),
                    radius = 800f
                )
            )
            .padding(innerPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopPanel("Сложный")
        GameBoard(pieces = pieces)
        Spacer(modifier = Modifier.height(80.dp))
    }
}
