package com.example.checkers.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_user")
data class CurrentUser(
    @PrimaryKey
    val id: Int = 0,
    val username: String
)