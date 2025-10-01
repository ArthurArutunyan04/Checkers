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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.R
import com.example.checkers.gamelogic.AnimatedPiece
import com.example.checkers.gamelogic.GameLogic
import com.example.checkers.gamelogic.GameState

@Composable
fun GameScreen(
    innerPadding: PaddingValues,
    gameState: GameState,
    onCellClick: (Int) -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onExit: () -> Unit,
    showTopPanel: Boolean = true,
    animatedPiece: AnimatedPiece? = null,
    onAnimationEnd: () -> Unit = {}
) {
    Box(
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
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (showTopPanel) {
                TopPanel(
                    title = stringResource(R.string.difficulty_level, stringResource(gameState.selectedDifficulty.displayNameRes)),
                    onClick = onPause
                )
            }

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
                possibleMoves = possibleMoves,
                animatedPiece = animatedPiece,
                onAnimationEnd = onAnimationEnd
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
                            text = stringResource(R.string.game_over),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (gameState.winner.value != null) {
                                stringResource(
                                    R.string.victory,
                                    stringResource(gameState.winner.value?.displayNameRes ?: R.string.forces_of_light)
                                )
                            } else {
                                stringResource(R.string.draw)
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                BottomGamePanel(gameState = gameState)
            }
        }

        if (gameState.paused.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2E211C))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.game_paused),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                gameState.paused.value = false
                                onResume()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A3728))
                        ) {
                            Text(
                                text = stringResource(R.string.resume_game),
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = onExit) {
                            Text(
                                text = stringResource(R.string.exit_game),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}