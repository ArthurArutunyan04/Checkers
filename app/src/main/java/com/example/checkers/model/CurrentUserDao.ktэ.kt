package com.example.checkers.model

import androidx.room.*

@Dao
interface CurrentUserDao {
    @Query("SELECT * FROM current_user WHERE id = 0")
    suspend fun getCurrentUser(): CurrentUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCurrentUser(user: CurrentUser)

    @Query("DELETE FROM current_user WHERE id = 0")
    suspend fun clearCurrentUser()
}