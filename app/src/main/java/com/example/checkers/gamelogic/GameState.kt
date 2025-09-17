package com.example.checkers.gamelogic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf

data class GameState(
    val board: MutableMap<Int, Int> = mutableStateMapOf(),
    var currentPlayer: PlayerColor = PlayerColor.WHITE,
    val selectedDifficulty: Difficulty,
    val playerColor: PlayerColor,
    var isAiTurn: Boolean = false,
    var selectedCell: Int? = null,
    val gameOver: MutableState<Boolean> = mutableStateOf(false),
    val winner: MutableState<PlayerColor?> = mutableStateOf(null),
    val historyLog: MutableList<String> = mutableStateListOf()
) {
    constructor(difficulty: Difficulty, playerColor: PlayerColor) : this(
        selectedDifficulty = difficulty,
        playerColor = playerColor
    )

    fun initializeBoard() {
        board.clear()
        generateStandardSetup().forEach { (index, res) ->
            board[index] = res
        }
        historyLog.clear()
        historyLog.add("Ход Сил Света")
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == PlayerColor.WHITE) PlayerColor.BLACK else PlayerColor.WHITE
        isAiTurn = (currentPlayer != playerColor) && (selectedDifficulty != Difficulty.DUEL)
        historyLog.add("Ход ${if (currentPlayer == PlayerColor.WHITE) "Сил Света" else "Сил Тьмы"}")
    }

    fun isValidCell(index: Int): Boolean {
        val row = index / 8
        val col = index % 8
        return (row + col) % 2 != 0
    }

    fun addLog(message: String) {
        historyLog.add(message)
        if (historyLog.size > 5) {
            historyLog.removeAt(0)
        }
    }
}