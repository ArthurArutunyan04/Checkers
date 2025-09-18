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
import androidx.compose.material3.*
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
import com.example.checkers.uiСomponents.ColorDialog
import com.example.checkers.uiСomponents.CustomDifficultyDialog
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
                            },
                            onPause = { gameState!!.paused.value = true },
                            onResume = {  },
                            onExit = { finish() },
                            showTopPanel = true
                        )
                    }
                }
            }
        }
    }

    private fun handleCellClick(state: GameState, index: Int, onAiMove: () -> Unit) {
        if (state.gameOver.value || state.isAiTurn || state.paused.value) {
            println("Click ignored: gameOver=${state.gameOver.value}, isAiTurn=${state.isAiTurn}, paused=${state.paused.value}")
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