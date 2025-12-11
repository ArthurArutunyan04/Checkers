package com.example.checkers

import android.content.Context
import androidx.room.Room
import com.example.checkers.database.UserDatabase
import com.example.checkers.viewmodel.AuthViewModel
import com.example.checkers.viewmodel.StatisticsViewModel
import com.example.checkers.viewmodel.UserCRUDViewModel

object AppContainer {
    private var authViewModel: AuthViewModel? = null
    private var statisticsViewModel: StatisticsViewModel? = null
    private var userCRUDViewModel: UserCRUDViewModel? = null
    private var database: UserDatabase? = null

    fun getAuthViewModel(context: Context): AuthViewModel {
        if (authViewModel == null) {
            authViewModel = AuthViewModel()
            initializeDatabase(context)
            authViewModel?.initializeDatabase(context)
        }
        return authViewModel!!
    }

    fun getStatisticsViewModel(context: Context): StatisticsViewModel {
        if (statisticsViewModel == null) {
            statisticsViewModel = StatisticsViewModel()
            initializeDatabase(context)
            statisticsViewModel?.initializeDatabase(database!!)
        }
        return statisticsViewModel!!
    }

    fun getUserCRUDViewModel(context: Context): UserCRUDViewModel {
        if (userCRUDViewModel == null) {
            userCRUDViewModel = UserCRUDViewModel()
            initializeDatabase(context)
            userCRUDViewModel?.initializeDatabase(database!!)
        }
        return userCRUDViewModel!!
    }

    fun getDatabase(context: Context): UserDatabase {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "user_database"
            )
                .build()
        }
        return database!!
    }

    private fun initializeDatabase(context: Context) {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "user_database"
            )
                .build()
        }
    }
}