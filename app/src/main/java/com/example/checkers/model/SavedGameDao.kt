package com.example.checkers.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedGameDao {
    @Query("SELECT * FROM saved_games WHERE username = :username LIMIT 1")
    suspend fun getSavedGame(username: String): SavedGame?

    @Query("SELECT * FROM saved_games WHERE username = :username LIMIT 1")
    fun getSavedGameFlow(username: String): Flow<SavedGame?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGame(savedGame: SavedGame)

    @Query("DELETE FROM saved_games WHERE username = :username")
    suspend fun deleteSavedGame(username: String)
}