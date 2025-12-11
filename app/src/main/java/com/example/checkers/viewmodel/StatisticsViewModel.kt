package com.example.checkers.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkers.database.UserDatabase
import com.example.checkers.model.Statistics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatisticsViewModel : ViewModel() {
    private var database: UserDatabase? = null
    private val _userStatistics = MutableStateFlow<Statistics?>(null)
    val userStatistics: StateFlow<Statistics?> = _userStatistics

    private val _allStatistics = MutableStateFlow<List<Statistics>>(emptyList())
    val allStatistics: StateFlow<List<Statistics>> = _allStatistics

    fun initializeDatabase(database: UserDatabase) {
        this.database = database
        Log.d("StatisticsViewModel", "Database initialized")
    }

    fun loadUserStatistics(username: String) {
        Log.d("StatisticsViewModel", "Loading statistics for user: $username")
        viewModelScope.launch {
            var stats = database?.statisticsDao()?.getStatistics(username)

            if (stats == null) {
                Log.d("StatisticsViewModel", "Statistics not found for user: $username, creating new one")
                stats = Statistics(username = username)
                database?.statisticsDao()?.insertStatistics(stats)
            }

            Log.d("StatisticsViewModel", "Loaded statistics: $stats")
            _userStatistics.value = stats
        }
    }

    fun loadAllStatistics() {
        viewModelScope.launch {
            val allStats = database?.statisticsDao()?.getAllStatistics() ?: emptyList()
            _allStatistics.value = allStats
        }
    }

    fun createStatistics(statistics: Statistics) {
        viewModelScope.launch {
            database?.statisticsDao()?.insertStatistics(statistics)
            loadAllStatistics()
        }
    }

    fun updateStatistics(statistics: Statistics) {
        viewModelScope.launch {
            database?.statisticsDao()?.updateStatistics(statistics)
            loadAllStatistics()
            if (_userStatistics.value?.username == statistics.username) {
                _userStatistics.value = statistics
            }
        }
    }

    fun deleteStatistics(statistics: Statistics) {
        viewModelScope.launch {
            database?.statisticsDao()?.deleteStatistics(statistics)
            loadAllStatistics()
            if (statistics.username == _userStatistics.value?.username) {
                _userStatistics.value = null
            }
        }
    }

    fun deleteStatisticsByUsername(username: String) {
        viewModelScope.launch {
            database?.statisticsDao()?.deleteStatisticsByUsername(username)
            loadAllStatistics()
            if (username == _userStatistics.value?.username) {
                _userStatistics.value = null
            }
        }
    }

    fun incrementGamesPlayed(username: String) {
        Log.d("StatisticsViewModel", "Incrementing games played for user: $username")
        viewModelScope.launch {
            var currentStats = database?.statisticsDao()?.getStatistics(username)
            if (currentStats != null) {
                val updatedStats = currentStats.copy(
                    gamesPlayed = currentStats.gamesPlayed + 1
                )
                database?.statisticsDao()?.updateStatistics(updatedStats)
                _userStatistics.value = updatedStats
                Log.d("StatisticsViewModel", "Updated statistics: $updatedStats")
            } else {
                val newStats = Statistics(
                    username = username,
                    gamesPlayed = 1
                )
                database?.statisticsDao()?.insertStatistics(newStats)
                _userStatistics.value = newStats
                Log.d("StatisticsViewModel", "Created new statistics: $newStats")
            }
        }
    }

    fun incrementWin(username: String) {
        Log.d("StatisticsViewModel", "Incrementing win for user: $username")
        viewModelScope.launch {
            var currentStats = database?.statisticsDao()?.getStatistics(username)
            if (currentStats != null) {
                val updatedStats = currentStats.copy(
                    wins = currentStats.wins + 1,
                    winStreak = currentStats.winStreak + 1
                )
                database?.statisticsDao()?.updateStatistics(updatedStats)
                _userStatistics.value = updatedStats
                Log.d("StatisticsViewModel", "Updated statistics: $updatedStats")
            } else {
                val newStats = Statistics(
                    username = username,
                    wins = 1,
                    winStreak = 1,
                    gamesPlayed = 1
                )
                database?.statisticsDao()?.insertStatistics(newStats)
                _userStatistics.value = newStats
                Log.d("StatisticsViewModel", "Created new statistics: $newStats")
            }
        }
    }

    fun incrementLoss(username: String) {
        Log.d("StatisticsViewModel", "Incrementing loss for user: $username")
        viewModelScope.launch {
            var currentStats = database?.statisticsDao()?.getStatistics(username)
            if (currentStats != null) {
                val updatedStats = currentStats.copy(
                    losses = currentStats.losses + 1,
                    winStreak = 0
                )
                database?.statisticsDao()?.updateStatistics(updatedStats)
                _userStatistics.value = updatedStats
                Log.d("StatisticsViewModel", "Updated statistics: $updatedStats")
            } else {
                val newStats = Statistics(
                    username = username,
                    losses = 1,
                    gamesPlayed = 1
                )
                database?.statisticsDao()?.insertStatistics(newStats)
                _userStatistics.value = newStats
                Log.d("StatisticsViewModel", "Created new statistics: $newStats")
            }
        }
    }

    fun incrementDuelPlayed(username: String) {
        Log.d("StatisticsViewModel", "Incrementing duel played for user: $username")
        viewModelScope.launch {
            var currentStats = database?.statisticsDao()?.getStatistics(username)
            if (currentStats != null) {
                val updatedStats = currentStats.copy(
                    duelsPlayed = currentStats.duelsPlayed + 1
                )
                database?.statisticsDao()?.updateStatistics(updatedStats)
                _userStatistics.value = updatedStats
                Log.d("StatisticsViewModel", "Updated statistics: $updatedStats")
            } else {
                val newStats = Statistics(
                    username = username,
                    duelsPlayed = 1
                )
                database?.statisticsDao()?.insertStatistics(newStats)
                _userStatistics.value = newStats
                Log.d("StatisticsViewModel", "Created new statistics: $newStats")
            }
        }
    }

    fun incrementLightForcesWin(username: String) {
        Log.d("StatisticsViewModel", "Incrementing light forces win for user: $username")
        viewModelScope.launch {
            var currentStats = database?.statisticsDao()?.getStatistics(username)
            if (currentStats != null) {
                val updatedStats = currentStats.copy(
                    lightForcesWins = currentStats.lightForcesWins + 1
                )
                database?.statisticsDao()?.updateStatistics(updatedStats)
                _userStatistics.value = updatedStats
                Log.d("StatisticsViewModel", "Updated statistics: $updatedStats")
            } else {
                val newStats = Statistics(
                    username = username,
                    lightForcesWins = 1
                )
                database?.statisticsDao()?.insertStatistics(newStats)
                _userStatistics.value = newStats
                Log.d("StatisticsViewModel", "Created new statistics: $newStats")
            }
        }
    }

    fun incrementDarkForcesWin(username: String) {
        Log.d("StatisticsViewModel", "Incrementing dark forces win for user: $username")
        viewModelScope.launch {
            var currentStats = database?.statisticsDao()?.getStatistics(username)
            if (currentStats != null) {
                val updatedStats = currentStats.copy(
                    darkForcesWins = currentStats.darkForcesWins + 1
                )
                database?.statisticsDao()?.updateStatistics(updatedStats)
                _userStatistics.value = updatedStats
                Log.d("StatisticsViewModel", "Updated statistics: $updatedStats")
            } else {
                val newStats = Statistics(
                    username = username,
                    darkForcesWins = 1
                )
                database?.statisticsDao()?.insertStatistics(newStats)
                _userStatistics.value = newStats
                Log.d("StatisticsViewModel", "Created new statistics: $newStats")
            }
        }
    }

    fun incrementWinStreak(username: String) {
        Log.d("StatisticsViewModel", "Incrementing win streak for user: $username")
        viewModelScope.launch {
            var currentStats = database?.statisticsDao()?.getStatistics(username)
            if (currentStats != null) {
                val updatedStats = currentStats.copy(
                    winStreak = currentStats.winStreak + 1
                )
                database?.statisticsDao()?.updateStatistics(updatedStats)
                _userStatistics.value = updatedStats
                Log.d("StatisticsViewModel", "Updated statistics: $updatedStats")
            } else {
                val newStats = Statistics(
                    username = username,
                    winStreak = 1
                )
                database?.statisticsDao()?.insertStatistics(newStats)
                _userStatistics.value = newStats
                Log.d("StatisticsViewModel", "Created new statistics: $newStats")
            }
        }
    }

    fun incrementCreepsKilled(username: String, count: Int = 1) {
        Log.d("StatisticsViewModel", "Incrementing creeps killed for user: $username, count: $count")
        viewModelScope.launch {
            var currentStats = database?.statisticsDao()?.getStatistics(username)
            if (currentStats != null) {
                val updatedStats = currentStats.copy(
                    creepsKilled = currentStats.creepsKilled + count
                )
                database?.statisticsDao()?.updateStatistics(updatedStats)
                _userStatistics.value = updatedStats
                Log.d("StatisticsViewModel", "Updated statistics: $updatedStats")
            } else {
                val newStats = Statistics(
                    username = username,
                    creepsKilled = count
                )
                database?.statisticsDao()?.insertStatistics(newStats)
                _userStatistics.value = newStats
                Log.d("StatisticsViewModel", "Created new statistics: $newStats")
            }
        }
    }

    fun incrementMageCreepsCreated(username: String, count: Int = 1) {
        Log.d("StatisticsViewModel", "Incrementing mage creeps created for user: $username, count: $count")
        viewModelScope.launch {
            var currentStats = database?.statisticsDao()?.getStatistics(username)
            if (currentStats != null) {
                val updatedStats = currentStats.copy(
                    mageCreepsCreated = currentStats.mageCreepsCreated + count
                )
                database?.statisticsDao()?.updateStatistics(updatedStats)
                _userStatistics.value = updatedStats
                Log.d("StatisticsViewModel", "Updated statistics: $updatedStats")
            } else {
                val newStats = Statistics(
                    username = username,
                    mageCreepsCreated = count
                )
                database?.statisticsDao()?.insertStatistics(newStats)
                _userStatistics.value = newStats
                Log.d("StatisticsViewModel", "Created new statistics: $newStats")
            }
        }
    }

    fun updateStatisticsAfterGame(
        username: String,
        difficulty: com.example.checkers.gamelogic.Difficulty,
        winner: com.example.checkers.gamelogic.PlayerColor?,
        playerColor: com.example.checkers.gamelogic.PlayerColor,
        creepsKilled: Int,
        mageCreepsCreated: Int
    ) {
        Log.d("StatisticsViewModel", "Updating statistics after game for user: $username, difficulty: $difficulty, winner: $winner, playerColor: $playerColor, creepsKilled: $creepsKilled, mageCreepsCreated: $mageCreepsCreated")

        viewModelScope.launch {
            var currentStats = database?.statisticsDao()?.getStatistics(username)
            Log.d("StatisticsViewModel", "Current stats before update: $currentStats")

            var updatedStats = currentStats ?: Statistics(username = username)

            when (difficulty) {
                com.example.checkers.gamelogic.Difficulty.DUEL -> {
                    updatedStats = updatedStats.copy(
                        duelsPlayed = updatedStats.duelsPlayed + 1
                    )
                    if (winner == com.example.checkers.gamelogic.PlayerColor.WHITE) {
                        updatedStats = updatedStats.copy(
                            lightForcesWins = updatedStats.lightForcesWins + 1
                        )
                    } else if (winner == com.example.checkers.gamelogic.PlayerColor.BLACK) {
                        updatedStats = updatedStats.copy(
                            darkForcesWins = updatedStats.darkForcesWins + 1
                        )
                    }
                }
                else -> {
                    updatedStats = updatedStats.copy(
                        gamesPlayed = updatedStats.gamesPlayed + 1
                    )
                    if (winner == playerColor) {
                        updatedStats = updatedStats.copy(
                            wins = updatedStats.wins + 1,
                            winStreak = updatedStats.winStreak + 1
                        )
                    } else {
                        updatedStats = updatedStats.copy(
                            losses = updatedStats.losses + 1,
                            winStreak = 0
                        )
                    }
                }
            }

            updatedStats = updatedStats.copy(
                creepsKilled = updatedStats.creepsKilled + creepsKilled,
                mageCreepsCreated = updatedStats.mageCreepsCreated + mageCreepsCreated
            )

            database?.statisticsDao()?.updateStatistics(updatedStats)
            _userStatistics.value = updatedStats
            Log.d("StatisticsViewModel", "Final updated statistics: $updatedStats")
        }
    }

    fun incrementDuelPlayedWithStats(username: String, creepsKilled: Int, mageCreepsCreated: Int) {
        viewModelScope.launch {
            val stats = database?.statisticsDao()?.getStatistics(username) ?: Statistics(
                username = username,
                duelsPlayed = 0,
                creepsKilled = 0,
                mageCreepsCreated = 0
            )
            val updatedStats = stats.copy(
                duelsPlayed = stats.duelsPlayed + 1,
                creepsKilled = stats.creepsKilled + creepsKilled,
                mageCreepsCreated = stats.mageCreepsCreated + mageCreepsCreated
            )
            database?.statisticsDao()?.insertStatistics(updatedStats)
            if (_userStatistics.value?.username == username) {
                _userStatistics.value = updatedStats
            }
        }
    }

    fun incrementLossWithStats(username: String, creepsKilled: Int, mageCreepsCreated: Int) {
        viewModelScope.launch {
            val stats = database?.statisticsDao()?.getStatistics(username) ?: Statistics(
                username = username,
                gamesPlayed = 0,
                losses = 0,
                creepsKilled = 0,
                mageCreepsCreated = 0
            )
            val updatedStats = stats.copy(
                gamesPlayed = stats.gamesPlayed + 1,
                losses = stats.losses + 1,
                creepsKilled = stats.creepsKilled + creepsKilled,
                mageCreepsCreated = stats.mageCreepsCreated + mageCreepsCreated
            )
            database?.statisticsDao()?.insertStatistics(updatedStats)
            if (_userStatistics.value?.username == username) {
                _userStatistics.value = updatedStats
            }
        }
    }

    fun incrementWinWithStats(username: String, creepsKilled: Int, mageCreepsCreated: Int) {
        viewModelScope.launch {
            val stats = database?.statisticsDao()?.getStatistics(username) ?: Statistics(
                username = username,
                gamesPlayed = 0,
                wins = 0,
                creepsKilled = 0,
                mageCreepsCreated = 0
            )
            val updatedStats = stats.copy(
                gamesPlayed = stats.gamesPlayed + 1,
                wins = stats.wins + 1,
                creepsKilled = stats.creepsKilled + creepsKilled,
                mageCreepsCreated = stats.mageCreepsCreated + mageCreepsCreated
            )
            database?.statisticsDao()?.insertStatistics(updatedStats)
            if (_userStatistics.value?.username == username) {
                _userStatistics.value = updatedStats
            }
        }
    }
}