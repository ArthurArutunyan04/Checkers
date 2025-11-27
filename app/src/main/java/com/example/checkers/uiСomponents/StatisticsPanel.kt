package com.example.checkers.uiСomponents

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.R
import com.example.checkers.activities.MainActivity
import com.example.checkers.ui.theme.Colus
import com.example.checkers.ui.theme.LocalLanguage
import com.example.checkers.ui.theme.Red
import com.example.checkers.ui.theme.StatisticActivityColor
import com.example.checkers.viewmodel.AuthViewModel
import com.example.checkers.viewmodel.StatisticsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatePanel(
    authViewModel: AuthViewModel,
    statisticsViewModel: StatisticsViewModel
) {
    val context = LocalContext.current
    val languageState = LocalLanguage.current
    val currentUsername by authViewModel.currentUsername
    val statistics by statisticsViewModel.userStatistics.collectAsState()

    var showCRUD by remember { mutableStateOf(false) }
    var selectedField by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogValue by remember { mutableStateOf("") }

    Log.d("StatePanel", "Current statistics: $statistics")
    Log.d("StatePanel", "Current username: $currentUsername")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "User: $currentUsername",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = languageState.getLocalizedString(context, R.string.general_statistics),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontFamily = Colus,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.games_played),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .weight(1f)
                        .combinedClickable(
                            onClick = { /* одиночное нажатие - ничего не делаем */ },
                            onDoubleClick = {
                                Log.d("StatePanel", "Double click on Games Played, statistics: $statistics")
                                statistics?.let { stats ->
                                    selectedField = "gamesPlayed"
                                    dialogValue = stats.gamesPlayed.toString()
                                    showDialog = true
                                    Log.d("StatePanel", "Dialog opened for gamesPlayed with value: $dialogValue")
                                } ?: run {
                                    Log.e("StatePanel", "Statistics is null, cannot open dialog for gamesPlayed")
                                }
                            }
                        )
                )
                Text(
                    text = statistics?.gamesPlayed?.toString() ?: "0",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.wins),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .weight(1f)
                        .combinedClickable(
                            onClick = { /* одиночное нажатие - ничего не делаем */ },
                            onDoubleClick = {
                                Log.d("StatePanel", "Double click on Wins, statistics: $statistics")
                                statistics?.let { stats ->
                                    selectedField = "wins"
                                    dialogValue = stats.wins.toString()
                                    showDialog = true
                                    Log.d("StatePanel", "Dialog opened for wins with value: $dialogValue")
                                } ?: run {
                                    Log.e("StatePanel", "Statistics is null, cannot open dialog for wins")
                                }
                            }
                        )
                )
                Text(
                    text = statistics?.wins?.toString() ?: "0",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.losses),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .weight(1f)
                        .combinedClickable(
                            onClick = { /* одиночное нажатие - ничего не делаем */ },
                            onDoubleClick = {
                                Log.d("StatePanel", "Double click on Losses, statistics: $statistics")
                                statistics?.let { stats ->
                                    selectedField = "losses"
                                    dialogValue = stats.losses.toString()
                                    showDialog = true
                                    Log.d("StatePanel", "Dialog opened for losses with value: $dialogValue")
                                } ?: run {
                                    Log.e("StatePanel", "Statistics is null, cannot open dialog for losses")
                                }
                            }
                        )
                )
                Text(
                    text = statistics?.losses?.toString() ?: "0",
                    color = Red
                )
            }

            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.win_percentage),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${statistics?.winPercentage ?: 0}%",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Text(
                text = languageState.getLocalizedString(context, R.string.duel),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = Colus,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.duels_played),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .weight(1f)
                        .combinedClickable(
                            onClick = { /* одиночное нажатие - ничего не делаем */ },
                            onDoubleClick = {
                                Log.d("StatePanel", "Double click on Duels Played, statistics: $statistics")
                                statistics?.let { stats ->
                                    selectedField = "duelsPlayed"
                                    dialogValue = stats.duelsPlayed.toString()
                                    showDialog = true
                                    Log.d("StatePanel", "Dialog opened for duelsPlayed with value: $dialogValue")
                                } ?: run {
                                    Log.e("StatePanel", "Statistics is null, cannot open dialog for duelsPlayed")
                                }
                            }
                        )
                )
                Text(
                    text = statistics?.duelsPlayed?.toString() ?: "0",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.light_forces_wins),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .weight(1f)
                        .combinedClickable(
                            onClick = { /* одиночное нажатие - ничего не делаем */ },
                            onDoubleClick = {
                                Log.d("StatePanel", "Double click on Light Forces Wins, statistics: $statistics")
                                statistics?.let { stats ->
                                    selectedField = "lightForcesWins"
                                    dialogValue = stats.lightForcesWins.toString()
                                    showDialog = true
                                    Log.d("StatePanel", "Dialog opened for lightForcesWins with value: $dialogValue")
                                } ?: run {
                                    Log.e("StatePanel", "Statistics is null, cannot open dialog for lightForcesWins")
                                }
                            }
                        )
                )
                Text(
                    text = statistics?.lightForcesWins?.toString() ?: "0",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.dark_forces_wins),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .weight(1f)
                        .combinedClickable(
                            onClick = { /* одиночное нажатие - ничего не делаем */ },
                            onDoubleClick = {
                                Log.d("StatePanel", "Double click on Dark Forces Wins, statistics: $statistics")
                                statistics?.let { stats ->
                                    selectedField = "darkForcesWins"
                                    dialogValue = stats.darkForcesWins.toString()
                                    showDialog = true
                                    Log.d("StatePanel", "Dialog opened for darkForcesWins with value: $dialogValue")
                                } ?: run {
                                    Log.e("StatePanel", "Statistics is null, cannot open dialog for darkForcesWins")
                                }
                            }
                        )
                )
                Text(
                    text = statistics?.darkForcesWins?.toString() ?: "0",
                    color = Red
                )
            }

            Text(
                text = languageState.getLocalizedString(context, R.string.additional),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = Colus,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.win_streak),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .weight(1f)
                        .combinedClickable(
                            onClick = { /* одиночное нажатие - ничего не делаем */ },
                            onDoubleClick = {
                                Log.d("StatePanel", "Double click on Win Streak, statistics: $statistics")
                                statistics?.let { stats ->
                                    selectedField = "winStreak"
                                    dialogValue = stats.winStreak.toString()
                                    showDialog = true
                                    Log.d("StatePanel", "Dialog opened for winStreak with value: $dialogValue")
                                } ?: run {
                                    Log.e("StatePanel", "Statistics is null, cannot open dialog for winStreak")
                                }
                            }
                        )
                )
                Text(
                    text = statistics?.winStreak?.toString() ?: "0",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.creeps_killed),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .weight(1f)
                        .combinedClickable(
                            onClick = { /* одиночное нажатие - ничего не делаем */ },
                            onDoubleClick = {
                                Log.d("StatePanel", "Double click on Creeps Killed, statistics: $statistics")
                                statistics?.let { stats ->
                                    selectedField = "creepsKilled"
                                    dialogValue = stats.creepsKilled.toString()
                                    showDialog = true
                                    Log.d("StatePanel", "Dialog opened for creepsKilled with value: $dialogValue")
                                } ?: run {
                                    Log.e("StatePanel", "Statistics is null, cannot open dialog for creepsKilled")
                                }
                            }
                        )
                )
                Text(
                    text = statistics?.creepsKilled?.toString() ?: "0",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.mage_creeps_created),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .weight(1f)
                        .combinedClickable(
                            onClick = { /* одиночное нажатие - ничего не делаем */ },
                            onDoubleClick = {
                                Log.d("StatePanel", "Double click on Mage Creeps Created, statistics: $statistics")
                                statistics?.let { stats ->
                                    selectedField = "mageCreepsCreated"
                                    dialogValue = stats.mageCreepsCreated.toString()
                                    showDialog = true
                                    Log.d("StatePanel", "Dialog opened for mageCreepsCreated with value: $dialogValue")
                                } ?: run {
                                    Log.e("StatePanel", "Statistics is null, cannot open dialog for mageCreepsCreated")
                                }
                            }
                        )
                )
                Text(
                    text = statistics?.mageCreepsCreated?.toString() ?: "0",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra("activity_color", StatisticActivityColor.toArgb())
                    intent.putExtra("activity_name", languageState.getLocalizedString(context, R.string.statistics))
                    context.startActivity(intent)
                    (context as? Activity)?.finish()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.cancel),
                    fontSize = 16.sp,
                    fontFamily = Colus
                )
            }
        }
    }

    if (showCRUD) {
        CRUDScreen(
            statisticsViewModel = statisticsViewModel,
            onBack = { showCRUD = false }
        )
    }

    if (showDialog && selectedField != null) {
        Log.d("StatePanel", "Showing dialog for field: $selectedField with value: $dialogValue")
        statistics?.let { currentStats ->
            FieldUpdateDialog(
                fieldName = selectedField!!,
                currentValue = dialogValue,
                onValueChange = { dialogValue = it },
                onConfirm = {
                    Log.d("StatePanel", "Confirming update for field: $selectedField, new value: $dialogValue")
                    val updatedStats = currentStats.copy(
                        gamesPlayed = if (selectedField == "gamesPlayed") dialogValue.toIntOrNull() ?: 0 else currentStats.gamesPlayed,
                        wins = if (selectedField == "wins") dialogValue.toIntOrNull() ?: 0 else currentStats.wins,
                        losses = if (selectedField == "losses") dialogValue.toIntOrNull() ?: 0 else currentStats.losses,
                        duelsPlayed = if (selectedField == "duelsPlayed") dialogValue.toIntOrNull() ?: 0 else currentStats.duelsPlayed,
                        lightForcesWins = if (selectedField == "lightForcesWins") dialogValue.toIntOrNull() ?: 0 else currentStats.lightForcesWins,
                        darkForcesWins = if (selectedField == "darkForcesWins") dialogValue.toIntOrNull() ?: 0 else currentStats.darkForcesWins,
                        winStreak = if (selectedField == "winStreak") dialogValue.toIntOrNull() ?: 0 else currentStats.winStreak,
                        creepsKilled = if (selectedField == "creepsKilled") dialogValue.toIntOrNull() ?: 0 else currentStats.creepsKilled,
                        mageCreepsCreated = if (selectedField == "mageCreepsCreated") dialogValue.toIntOrNull() ?: 0 else currentStats.mageCreepsCreated
                    )
                    statisticsViewModel.updateStatistics(updatedStats)
                    Log.d("StatePanel", "Updated statistics: $updatedStats")
                    showDialog = false
                    selectedField = null
                },
                onDismiss = {
                    Log.d("StatePanel", "Dialog dismissed for field: $selectedField")
                    showDialog = false
                    selectedField = null
                }
            )
        } ?: run {
            Log.e("StatePanel", "Statistics is null when trying to update, closing dialog")
            showDialog = false
            selectedField = null
        }
    }
}