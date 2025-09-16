package com.example.checkers.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.gamelogic.Difficulty
import com.example.checkers.gamelogic.GameLogic
import com.example.checkers.gamelogic.GameState
import com.example.checkers.gamelogic.PlayerColor
import com.example.checkers.ui.theme.CheckersTheme
import com.example.checkers.uiСomponents.GameScreen
import kotlinx.coroutines.launch

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckersTheme {
                var gameState by remember { mutableStateOf<GameState?>(null) }
                var showDifficultyDialog by remember { mutableStateOf(true) }
                var showColorDialog by remember { mutableStateOf(false) }
                var selectedDifficulty by remember { mutableStateOf<Difficulty?>(null) }
                val coroutineScope = rememberCoroutineScope()

                if (showDifficultyDialog) {
                    CustomDifficultyDialog(
                        onSelect = { difficulty ->
                            selectedDifficulty = difficulty
                            showDifficultyDialog = false
                            if (difficulty != Difficulty.DUEL) {
                                showColorDialog = true
                            } else {
                                gameState = GameState(
                                    difficulty = difficulty,
                                    playerColor = PlayerColor.WHITE
                                ).apply { initializeBoard() }
                            }
                        },
                        onDismiss = { finish() }
                    )
                } else if (showColorDialog && selectedDifficulty != null) {
                    ColorDialog(
                        onSelect = { color ->
                            gameState = GameState(
                                difficulty = selectedDifficulty!!,
                                playerColor = color
                            ).apply { initializeBoard() }
                            showColorDialog = false
                        },
                        onDismiss = { finish() }
                    )
                } else if (gameState != null) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        GameScreen(
                            innerPadding = innerPadding,
                            gameState = gameState!!,
                            onCellClick = { index ->
                                handleCellClick(gameState!!, index) {
                                    coroutineScope.launch {
                                        kotlinx.coroutines.delay(500)
                                        GameLogic.aiMove(gameState!!)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun handleCellClick(state: GameState, index: Int, onAiMove: () -> Unit) {
        if (state.gameOver.value || state.isAiTurn) {
            println("Click ignored: gameOver=${state.gameOver.value}, isAiTurn=${state.isAiTurn}")
            return
        }

        val selected = state.selectedCell
        val pieceRes = state.board[index]
        val pieceColor = GameLogic.getPieceColor(pieceRes ?: 0)

        println("Cell clicked: $index, pieceColor=$pieceColor, currentPlayer=${state.currentPlayer}, selected=$selected")

        if (selected != null) {
            if (GameLogic.performMove(state, selected, index)) {
                if (state.isAiTurn) {
                    onAiMove()
                }
            }
            state.selectedCell = null
        } else if (pieceRes != null && pieceColor == state.currentPlayer) {
            state.selectedCell = index
            println("Selected piece at $index")
        }
    }
}

@Composable
fun CustomDifficultyDialog(onSelect: (Difficulty) -> Unit, onDismiss: () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .wrapContentSize(Alignment.Center)
        ) {
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Выберите уровень сложности",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Difficulty.values().forEach { difficulty ->
                        val buttonColor = when (difficulty) {
                            Difficulty.EASY -> Color(0xFF4CAF50)
                            Difficulty.MEDIUM -> Color(0xFFFFCA28)
                            Difficulty.HARD -> Color(0xFFE57373)
                            Difficulty.DUEL -> Color(0xFF2196F3)
                        }
                        Button(
                            onClick = { onSelect(difficulty) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = difficulty.displayName,
                                fontSize = 16.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF757575),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Отмена",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColorDialog(onSelect: (PlayerColor) -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выберите цвет фигур") },
        text = {
            Column {
                PlayerColor.values().forEach { color ->
                    TextButton(
                        onClick = { onSelect(color) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(color.displayName)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Отмена") }
        }
    )
}