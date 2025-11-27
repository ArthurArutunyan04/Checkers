package com.example.checkers.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statistics")
data class Statistics(
    @PrimaryKey
    val username: String,
    val gamesPlayed: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
    val duelsPlayed: Int = 0,
    val lightForcesWins: Int = 0,
    val darkForcesWins: Int = 0,
    val winStreak: Int = 0,
    val creepsKilled: Int = 0,
    val mageCreepsCreated: Int = 0
) {
    val winPercentage: Int
        get() = if (gamesPlayed > 0) (wins * 100 / gamesPlayed) else 0
}