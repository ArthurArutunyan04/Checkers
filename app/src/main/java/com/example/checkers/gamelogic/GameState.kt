package com.example.checkers.gamelogic

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import com.example.checkers.R
import com.example.checkers.ui.theme.LanguageState
import com.example.checkers.ui.theme.LocalLanguage

data class GameState(
    val context: Context,
    val board: MutableMap<Int, Int> = mutableStateMapOf(),
    var currentPlayer: PlayerColor = PlayerColor.WHITE,
    val selectedDifficulty: Difficulty,
    val playerColor: PlayerColor,
    var isAiTurn: Boolean = false,
    var selectedCell: Int? = null,
    val gameOver: MutableState<Boolean> = mutableStateOf(false),
    val winner: MutableState<PlayerColor?> = mutableStateOf(null),
    val historyLog: MutableList<String> = mutableStateListOf(),
    val paused: MutableState<Boolean> = mutableStateOf(false),
    var creepsKilled: Int = 0,
    var mageCreepsCreated: Int = 0
) {
    constructor(context: Context, difficulty: Difficulty, playerColor: PlayerColor) : this(
        context = context,
        selectedDifficulty = difficulty,
        playerColor = playerColor
    )

    fun initializeBoard(languageState: LanguageState) {
        board.clear()
        generateStandardSetup().forEach { (index, res) ->
            board[index] = res
        }
        historyLog.clear()
        historyLog.add(languageState.getLocalizedString(context, R.string.forces_of_light_turn))
        creepsKilled = 0
        mageCreepsCreated = 0
    }

    fun switchPlayer(context: Context, languageState: LanguageState) {
        currentPlayer = if (currentPlayer == PlayerColor.WHITE) PlayerColor.BLACK else PlayerColor.WHITE
        isAiTurn = (currentPlayer != playerColor) && (selectedDifficulty != Difficulty.DUEL)
        val turnMessage = if (currentPlayer == PlayerColor.WHITE)
            languageState.getLocalizedString(context, R.string.forces_of_light_turn)
        else
            languageState.getLocalizedString(context, R.string.forces_of_darkness_turn)
        historyLog.add(turnMessage)
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

    @Composable
    fun GameState.initializeBoard() {
        val languageState = LocalLanguage.current
        initializeBoard(languageState)
    }
}