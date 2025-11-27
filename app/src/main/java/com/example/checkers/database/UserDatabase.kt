package com.example.checkers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.checkers.model.User
import com.example.checkers.model.Statistics
import com.example.checkers.model.CurrentUser
import com.example.checkers.model.UserDao
import com.example.checkers.model.StatisticsDao
import com.example.checkers.model.CurrentUserDao
import com.example.checkers.model.Converters

@Database(
    entities = [User::class, Statistics::class, CurrentUser::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun statisticsDao(): StatisticsDao
    abstract fun currentUserDao(): CurrentUserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}