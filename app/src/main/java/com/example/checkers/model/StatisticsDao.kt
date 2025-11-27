package com.example.checkers.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticsDao {
    @Query("SELECT * FROM statistics WHERE username = :username")
    suspend fun getStatistics(username: String): Statistics?

    @Query("SELECT * FROM statistics WHERE username = :username")
    fun getStatisticsFlow(username: String): Flow<Statistics?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatistics(statistics: Statistics)

    @Update
    suspend fun updateStatistics(statistics: Statistics)

    @Delete
    suspend fun deleteStatistics(statistics: Statistics)

    @Query("SELECT * FROM statistics")
    suspend fun getAllStatistics(): List<Statistics>

    @Query("DELETE FROM statistics WHERE username = :username")
    suspend fun deleteStatisticsByUsername(username: String)
}