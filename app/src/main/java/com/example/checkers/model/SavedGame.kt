package com.example.checkers.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_games")
data class SavedGame(
    @PrimaryKey
    val username: String,
    val gameData: String,
    val createdAt: Long = System.currentTimeMillis(),
    val difficulty: String,
    val playerColor: String
)