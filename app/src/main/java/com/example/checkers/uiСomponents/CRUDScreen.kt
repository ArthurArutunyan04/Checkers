package com.example.checkers.uiСomponents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.checkers.model.Statistics
import com.example.checkers.viewmodel.StatisticsViewModel

@Composable
fun CRUDScreen(
    statisticsViewModel: StatisticsViewModel,
    onBack: () -> Unit
) {
    val allStatistics by statisticsViewModel.allStatistics.collectAsState()
    var selectedStatistics by remember { mutableStateOf<Statistics?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogType by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        statisticsViewModel.loadAllStatistics()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "CRUD Operations for Statistics",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                dialogType = "update"
                selectedStatistics = statisticsViewModel.userStatistics.value
                if (selectedStatistics != null) {
                    showDialog = true
                }
            }) {
                Text("Update")
            }

            Button(
                onClick = {
                    dialogType = "delete"
                    selectedStatistics = statisticsViewModel.userStatistics.value
                    if (selectedStatistics != null) {
                        statisticsViewModel.deleteStatistics(selectedStatistics!!)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text("Delete")
            }

            Button(onClick = onBack) {
                Text("Back")
            }
        }

        LazyColumn {
            items(allStatistics) { stats ->
                StatisticsItem(
                    statistics = stats,
                    onDoubleClick = { stats ->
                        selectedStatistics = stats
                        dialogType = "update"
                        showDialog = true
                    }
                )
            }
        }
    }

    if (showDialog && selectedStatistics != null) {
        StatisticsUpdateDialog(
            statistics = selectedStatistics!!,
            onConfirm = { updatedStats ->
                statisticsViewModel.updateStatistics(updatedStats)
                showDialog = false
                selectedStatistics = null
            },
            onDismiss = {
                showDialog = false
                selectedStatistics = null
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatisticsItem(
    statistics: Statistics,
    onDoubleClick: (Statistics) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .combinedClickable(
                onClick = { /* одиночное нажатие - ничего не делаем */ },
                onDoubleClick = { onDoubleClick(statistics) }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "User: ${statistics.username}",
                style = MaterialTheme.typography.titleMedium
            )
            Text("Games: ${statistics.gamesPlayed}")
            Text("Wins: ${statistics.wins}")
            Text("Losses: ${statistics.losses}")
            Text("Duels: ${statistics.duelsPlayed}")
            Text("Light Wins: ${statistics.lightForcesWins}")
            Text("Dark Wins: ${statistics.darkForcesWins}")
            Text("Streak: ${statistics.winStreak}")
            Text("Creeps Killed: ${statistics.creepsKilled}")
            Text("Mage Creeps: ${statistics.mageCreepsCreated}")
        }
    }
}

@Composable
fun StatisticsUpdateDialog(
    statistics: Statistics,
    onConfirm: (Statistics) -> Unit,
    onDismiss: () -> Unit
) {
    var gamesPlayed by remember { mutableStateOf(statistics.gamesPlayed.toString()) }
    var wins by remember { mutableStateOf(statistics.wins.toString()) }
    var losses by remember { mutableStateOf(statistics.losses.toString()) }
    var duelsPlayed by remember { mutableStateOf(statistics.duelsPlayed.toString()) }
    var lightForcesWins by remember { mutableStateOf(statistics.lightForcesWins.toString()) }
    var darkForcesWins by remember { mutableStateOf(statistics.darkForcesWins.toString()) }
    var winStreak by remember { mutableStateOf(statistics.winStreak.toString()) }
    var creepsKilled by remember { mutableStateOf(statistics.creepsKilled.toString()) }
    var mageCreepsCreated by remember { mutableStateOf(statistics.mageCreepsCreated.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Statistics") },
        text = {
            Column {
                Text("User: ${statistics.username}", modifier = Modifier.padding(bottom = 8.dp))
                OutlinedTextField(
                    value = gamesPlayed,
                    onValueChange = { gamesPlayed = it },
                    label = { Text("Games Played") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = wins,
                    onValueChange = { wins = it },
                    label = { Text("Wins") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = losses,
                    onValueChange = { losses = it },
                    label = { Text("Losses") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = duelsPlayed,
                    onValueChange = { duelsPlayed = it },
                    label = { Text("Duels Played") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = lightForcesWins,
                    onValueChange = { lightForcesWins = it },
                    label = { Text("Light Forces Wins") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = darkForcesWins,
                    onValueChange = { darkForcesWins = it },
                    label = { Text("Dark Forces Wins") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = winStreak,
                    onValueChange = { winStreak = it },
                    label = { Text("Win Streak") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = creepsKilled,
                    onValueChange = { creepsKilled = it },
                    label = { Text("Creeps Killed") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = mageCreepsCreated,
                    onValueChange = { mageCreepsCreated = it },
                    label = { Text("Mage Creeps Created") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedStats = statistics.copy(
                        gamesPlayed = gamesPlayed.toIntOrNull() ?: 0,
                        wins = wins.toIntOrNull() ?: 0,
                        losses = losses.toIntOrNull() ?: 0,
                        duelsPlayed = duelsPlayed.toIntOrNull() ?: 0,
                        lightForcesWins = lightForcesWins.toIntOrNull() ?: 0,
                        darkForcesWins = darkForcesWins.toIntOrNull() ?: 0,
                        winStreak = winStreak.toIntOrNull() ?: 0,
                        creepsKilled = creepsKilled.toIntOrNull() ?: 0,
                        mageCreepsCreated = mageCreepsCreated.toIntOrNull() ?: 0
                    )
                    onConfirm(updatedStats)
                }
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}