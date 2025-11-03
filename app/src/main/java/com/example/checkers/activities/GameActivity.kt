package com.example.checkers.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.checkers.R
import com.example.checkers.gamelogic.AnimatedPiece
import com.example.checkers.gamelogic.Difficulty
import com.example.checkers.gamelogic.GameLogic
import com.example.checkers.gamelogic.GameState
import com.example.checkers.gamelogic.PlayerColor
import com.example.checkers.ui.theme.CheckersTheme
import com.example.checkers.ui.theme.GameActivityColor
import com.example.checkers.ui.theme.LanguageState
import com.example.checkers.ui.theme.LocalLanguage
import com.example.checkers.ui.theme.LocalThemeMode
import com.example.checkers.ui.theme.ProvideLanguage
import com.example.checkers.ui.theme.ProvideThemeMode
import com.example.checkers.uiСomponents.ColorDialog
import com.example.checkers.uiСomponents.CustomDifficultyDialog
import com.example.checkers.uiСomponents.GameScreen
import kotlinx.coroutines.launch

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProvideThemeMode(this) {
                ProvideLanguage(this) {
                    val themeMode = LocalThemeMode.current.themeMode
                    val languageState = LocalLanguage.current
                    CheckersTheme(themeMode = themeMode) {
                        val context = LocalContext.current
                        var gameState by remember { mutableStateOf<GameState?>(null) }
                        var showDifficultyDialog by remember { mutableStateOf(true) }
                        var showColorDialog by remember { mutableStateOf(false) }
                        var selectedDifficulty by remember { mutableStateOf<Difficulty?>(null) }
                        var animatedPiece by remember { mutableStateOf<AnimatedPiece?>(null) }
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
                                            context = context,
                                            difficulty = difficulty,
                                            playerColor = PlayerColor.WHITE
                                        ).apply { initializeBoard(languageState) }
                                    }
                                },
                                onDismiss = { finish() }
                            )
                        } else if (showColorDialog && selectedDifficulty != null) {
                            ColorDialog(
                                onSelect = { color ->
                                    gameState = GameState(
                                        context = context,
                                        difficulty = selectedDifficulty!!,
                                        playerColor = color
                                    ).apply { initializeBoard(languageState) }
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
                                        handleCellClick(
                                            gameState!!,
                                            index,
                                            languageState,
                                            { animated -> animatedPiece = animated }) {
                                            coroutineScope.launch {
                                                kotlinx.coroutines.delay(500)
                                                GameLogic.aiMove(gameState!!)
                                            }
                                        }
                                    },
                                    onPause = { gameState!!.paused.value = true },
                                    onResume = { gameState!!.paused.value = false },
                                    onExit = {
                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.putExtra("activity_color", GameActivityColor.toArgb())
                                        intent.putExtra("activity_name", languageState.getLocalizedString(context, R.string.game_activity))
                                        startActivity(intent)
                                        finish()
                                    },
                                    showTopPanel = true,
                                    animatedPiece = animatedPiece,
                                    onAnimationEnd = {
                                        println("Animation ended, resetting animatedPiece")
                                        animatedPiece = null
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleCellClick(
        state: GameState,
        index: Int,
        languageState: LanguageState,
        setAnimatedPiece: (AnimatedPiece?) -> Unit,
        onAiMove: () -> Unit
    ) {
        if (state.gameOver.value || state.isAiTurn || state.paused.value) {
            println("Click ignored: gameOver=${state.gameOver.value}, isAiTurn=${state.isAiTurn}, paused=${state.paused.value}")
            return
        }

        val selected = state.selectedCell
        val pieceRes = state.board[index]
        val pieceColor = GameLogic.getPieceColor(pieceRes ?: 0)

        println("Cell clicked: $index, pieceColor=$pieceColor, currentPlayer=${state.currentPlayer}, selected=$selected")

        if (selected != null) {
            val (success, animatedPiece) = GameLogic.performMove(state, selected, index, languageState)
            println("Move attempt: success=$success, animatedPiece=$animatedPiece")
            if (success) {
                setAnimatedPiece(animatedPiece)
                if (state.isAiTurn) {
                    onAiMove()
                }
            }
            if (!success || animatedPiece == null) {
                state.selectedCell = null
                println("Deselected cell due to invalid move or no animation")
            }
        } else if (pieceRes != null && pieceColor == state.currentPlayer) {
            state.selectedCell = index
            println("Selected piece at $index")
        } else {
            println("Invalid selection: pieceRes=$pieceRes, pieceColor=$pieceColor, currentPlayer=${state.currentPlayer}")
        }
    }
}