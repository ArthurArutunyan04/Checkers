package com.example.checkers.gamelogic

import com.example.checkers.R
import kotlin.random.Random


fun generateInitialPieces(): Map<Int, Int> {
    val blackCells = (0 until 64).filter { index ->
        val row = index / 8
        val col = index % 8
        (row + col) % 2 != 0
    }

    val figurePositions = blackCells.shuffled(Random(System.currentTimeMillis())).take(4)

    return figurePositions.mapIndexed { i, index ->
        val imageRes = when (i) {
            0 -> R.drawable.white_win
            1 -> R.drawable.black_win
            2 -> R.drawable.white_def
            else -> R.drawable.black_def
        }
        index to imageRes
    }.toMap()
}

fun generateStandardSetup(): Map<Int, Int> {
    val pieces = mutableMapOf<Int, Int>()

    for (index in 0 until 64) {
        val row = index / 8
        val col = index % 8
        val isBlackCell = (row + col) % 2 != 0

        if (!isBlackCell) continue

        when (row) {
            in 0..2 -> pieces[index] = R.drawable.black_def
            in 5..7 -> pieces[index] = R.drawable.white_def
        }
    }

    return pieces
}