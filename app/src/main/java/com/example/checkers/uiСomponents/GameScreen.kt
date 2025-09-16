package com.example.checkers.uiСomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.checkers.gamelogic.GameLogic
import com.example.checkers.gamelogic.GameState

@Composable
fun GameScreen(
    innerPadding: PaddingValues,
    gameState: GameState,
    onCellClick: (Int) -> Unit
) {
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
        TopPanel(
            title = "${gameState.selectedDifficulty.displayName}"
        )

        val possibleMoves = remember(gameState.selectedCell, gameState.gameOver.value, gameState.board) {
            if (gameState.selectedCell != null && !gameState.gameOver.value) {
                GameLogic.getPossibleMoves(gameState, gameState.selectedCell!!)
            } else {
                emptyList()
            }
        }

        GameBoard(
            pieces = gameState.board,
            onCellClick = onCellClick,
            selectedCell = gameState.selectedCell,
            isGameOver = gameState.gameOver.value,
            possibleMoves = possibleMoves
        )

        if (gameState.gameOver.value) {
            Spacer(modifier = Modifier.height(80.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Игра окончена!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Победитель: ${gameState.winner.value?.displayName ?: "Ничья"}",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            BottomGamePanel(gameState = gameState)
        }
    }
}