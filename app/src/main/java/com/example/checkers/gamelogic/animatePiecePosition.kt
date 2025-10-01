package com.example.checkers.gamelogic

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.delay

data class AnimatedPiece(
    val pieceRes: Int,
    val fromIndex: Int,
    val toIndex: Int,
    val isCapture: Boolean,
    val onAnimationEnd: () -> Unit
)

@Composable
fun animatePiecePosition(
    animatedPiece: AnimatedPiece?,
    boardWidth: Float,
    boardHeight: Float,
    onAnimationEnd: () -> Unit
): Offset? {
    if (animatedPiece == null) {
        return null
    }


    val fromRow = animatedPiece.fromIndex / 8
    val fromCol = animatedPiece.fromIndex % 8
    val toRow = animatedPiece.toIndex / 8
    val toCol = animatedPiece.toIndex % 8

    val cellWidth = boardWidth / 8
    val cellHeight = boardHeight / 8

    val startOffset = Offset(
        x = fromCol * cellWidth + cellWidth / 2,
        y = fromRow * cellHeight + cellHeight / 2
    )
    val endOffset = Offset(
        x = toCol * cellWidth + cellWidth / 2,
        y = toRow * cellHeight + cellHeight / 2
    )


    val animatedOffset = remember { Animatable(startOffset, Offset.VectorConverter) }

    LaunchedEffect(animatedPiece) {
        animatedOffset.animateTo(
            targetValue = endOffset,
            animationSpec = if (animatedPiece.isCapture) {
                tween(
                    durationMillis = 350,
                    easing = LinearEasing
                )
            } else {
                tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            }
        )
        delay(50)
        animatedPiece.onAnimationEnd()
        onAnimationEnd()
    }

    return animatedOffset.value
}