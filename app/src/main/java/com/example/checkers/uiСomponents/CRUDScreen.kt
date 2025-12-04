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
import com.example.checkers.R
import com.example.checkers.model.Statistics
import com.example.checkers.viewmodel.StatisticsViewModel
import com.example.checkers.ui.theme.LocalLanguage

@Composable
fun CRUDScreen(
    statisticsViewModel: StatisticsViewModel,
    onBack: () -> Unit
) {
    val languageState = LocalLanguage.current
    val context = androidx.compose.ui.platform.LocalContext.current
    val allStatistics by statisticsViewModel.allStatistics.collectAsState()
    var selectedStatistics by remember { mutableStateOf<Statistics?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        statisticsViewModel.loadAllStatistics()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = languageState.getLocalizedString(context, R.string.statistics_crud_title),
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
                val currentStats = statisticsViewModel.userStatistics.value
                if (currentStats != null) {
                    selectedStatistics = currentStats
                    showDialog = true
                }
            }) {
                Text(languageState.getLocalizedString(context, R.string.update_button))
            }

            Button(
                onClick = {
                    val currentStats = statisticsViewModel.userStatistics.value
                    if (currentStats != null) {
                        selectedStatistics = currentStats
                        showDeleteConfirmation = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(languageState.getLocalizedString(context, R.string.delete_button))
            }

            Button(onClick = onBack) {
                Text(languageState.getLocalizedString(context, R.string.back_button))
            }
        }

        LazyColumn {
            items(allStatistics) { stats ->
                StatisticsItem(
                    statistics = stats,
                    languageState = languageState,
                    onDoubleClick = { stats ->
                        selectedStatistics = stats
                        showDialog = true
                    }
                )
            }
        }
    }

    if (showDialog && selectedStatistics != null) {
        StatisticsUpdateDialog(
            statistics = selectedStatistics!!,
            languageState = languageState,
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

    if (showDeleteConfirmation && selectedStatistics != null) {
        DeleteStatisticsConfirmationDialog(
            statistics = selectedStatistics!!,
            languageState = languageState,
            onConfirm = {
                statisticsViewModel.deleteStatistics(selectedStatistics!!)
                showDeleteConfirmation = false
                selectedStatistics = null
            },
            onDismiss = {
                showDeleteConfirmation = false
                selectedStatistics = null
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatisticsItem(
    statistics: Statistics,
    languageState: com.example.checkers.ui.theme.LanguageState,
    onDoubleClick: (Statistics) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .combinedClickable(
                onClick = { },
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
                text = "${languageState.getLocalizedString(context, R.string.username_field)}: ${statistics.username}",
                style = MaterialTheme.typography.titleMedium
            )
            Text("${languageState.getLocalizedString(context, R.string.games_played)}: ${statistics.gamesPlayed}")
            Text("${languageState.getLocalizedString(context, R.string.wins)}: ${statistics.wins}")
            Text("${languageState.getLocalizedString(context, R.string.losses)}: ${statistics.losses}")
            Text("${languageState.getLocalizedString(context, R.string.duels_played)}: ${statistics.duelsPlayed}")
            Text("${languageState.getLocalizedString(context, R.string.light_forces_wins)}: ${statistics.lightForcesWins}")
            Text("${languageState.getLocalizedString(context, R.string.dark_forces_wins)}: ${statistics.darkForcesWins}")
            Text("${languageState.getLocalizedString(context, R.string.win_streak)}: ${statistics.winStreak}")
            Text("${languageState.getLocalizedString(context, R.string.creeps_killed)}: ${statistics.creepsKilled}")
            Text("${languageState.getLocalizedString(context, R.string.mage_creeps_created)}: ${statistics.mageCreepsCreated}")
        }
    }
}

@Composable
fun StatisticsUpdateDialog(
    statistics: Statistics,
    languageState: com.example.checkers.ui.theme.LanguageState,
    onConfirm: (Statistics) -> Unit,
    onDismiss: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
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
        title = {
            Text(languageState.getLocalizedString(context, R.string.update_statistics_dialog_title))
        },
        text = {
            Column {
                Text(
                    languageState.getLocalizedString(context, R.string.username_field) + ": ${statistics.username}",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = gamesPlayed,
                    onValueChange = { gamesPlayed = it },
                    label = { Text(languageState.getLocalizedString(context, R.string.games_played)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = wins,
                    onValueChange = { wins = it },
                    label = { Text(languageState.getLocalizedString(context, R.string.wins)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = losses,
                    onValueChange = { losses = it },
                    label = { Text(languageState.getLocalizedString(context, R.string.losses)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = duelsPlayed,
                    onValueChange = { duelsPlayed = it },
                    label = { Text(languageState.getLocalizedString(context, R.string.duels_played)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = lightForcesWins,
                    onValueChange = { lightForcesWins = it },
                    label = { Text(languageState.getLocalizedString(context, R.string.light_forces_wins)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = darkForcesWins,
                    onValueChange = { darkForcesWins = it },
                    label = { Text(languageState.getLocalizedString(context, R.string.dark_forces_wins)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = winStreak,
                    onValueChange = { winStreak = it },
                    label = { Text(languageState.getLocalizedString(context, R.string.win_streak)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = creepsKilled,
                    onValueChange = { creepsKilled = it },
                    label = { Text(languageState.getLocalizedString(context, R.string.creeps_killed)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = mageCreepsCreated,
                    onValueChange = { mageCreepsCreated = it },
                    label = { Text(languageState.getLocalizedString(context, R.string.mage_creeps_created)) },
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
                Text(languageState.getLocalizedString(context, R.string.update_button))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(languageState.getLocalizedString(context, R.string.cancel_button))
            }
        }
    )
}

@Composable
fun DeleteStatisticsConfirmationDialog(
    statistics: Statistics,
    languageState: com.example.checkers.ui.theme.LanguageState,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(languageState.getLocalizedString(context, R.string.delete_user_dialog_title)) },
        text = {
            Text(
                languageState.getLocalizedString(
                    context,
                    R.string.delete_button,
                    statistics.username
                )
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(languageState.getLocalizedString(context, R.string.delete_button))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(languageState.getLocalizedString(context, R.string.cancel_button))
            }
        }
    )
}