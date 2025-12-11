package com.example.checkers.gamelogic

import android.content.Context
import com.google.gson.Gson

data class GameStateJsonWrapper(
    val board: Map<Int, Int>,
    val currentPlayer: String,
    val selectedCell: Int?,
    val gameOver: Boolean,
    val winner: String?,
    val historyLog: List<String>,
    val paused: Boolean,
    val creepsKilled: Int,
    val mageCreepsCreated: Int,
    val selectedDifficulty: String,
    val playerColor: String,
    val alreadySavedToStats: Boolean
)

fun GameState.toJson(): String {
    return Gson().toJson(GameStateJsonWrapper(
        board = board,
        currentPlayer = currentPlayer.name,
        selectedCell = selectedCell,
        gameOver = gameOver.value,
        winner = winner.value?.name,
        historyLog = historyLog.toList(),
        paused = paused.value,
        creepsKilled = creepsKilled,
        mageCreepsCreated = mageCreepsCreated,
        selectedDifficulty = selectedDifficulty.name,
        playerColor = playerColor.name,
        alreadySavedToStats = alreadySavedToStats
    ))
}

fun fromJson(json: String, context: Context, difficulty: Difficulty, playerColor: PlayerColor): GameState {
    val wrapper = Gson().fromJson(json, GameStateJsonWrapper::class.java)
    val gameState = GameState(
        context = context,
        difficulty = Difficulty.valueOf(wrapper.selectedDifficulty),
        playerColor = PlayerColor.valueOf(wrapper.playerColor)
    ).apply {
        board.clear()
        board.putAll(wrapper.board)
        currentPlayer = PlayerColor.valueOf(wrapper.currentPlayer)
        selectedCell = wrapper.selectedCell
        gameOver.value = wrapper.gameOver
        winner.value = wrapper.winner?.let { PlayerColor.valueOf(it) }
        historyLog.clear()
        historyLog.addAll(wrapper.historyLog)
        paused.value = wrapper.paused
        creepsKilled = wrapper.creepsKilled
        mageCreepsCreated = wrapper.mageCreepsCreated
        alreadySavedToStats = wrapper.alreadySavedToStats
    }
    return gameState
}