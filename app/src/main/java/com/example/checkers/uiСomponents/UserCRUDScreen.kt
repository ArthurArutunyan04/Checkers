package com.example.checkers.uiСomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.checkers.R
import com.example.checkers.model.User
import com.example.checkers.viewmodel.UserCRUDViewModel
import com.example.checkers.ui.theme.LocalLanguage

@Composable
fun UserCRUDScreen(
    userCRUDViewModel: UserCRUDViewModel,
    onBack: () -> Unit
) {
    val languageState = LocalLanguage.current
    val context = androidx.compose.ui.platform.LocalContext.current
    val allUsers by userCRUDViewModel.allUsers.collectAsState()
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogType by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        userCRUDViewModel.loadAllUsers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = languageState.getLocalizedString(context, R.string.user_crud_title),
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
                dialogType = "create"
                selectedUser = User(username = "", password = "")
                showDialog = true
            }) {
                Text(languageState.getLocalizedString(context, R.string.create_user_button))
            }

            Button(onClick = onBack) {
                Text(languageState.getLocalizedString(context, R.string.back_button))
            }
        }

        LazyColumn {
            items(allUsers) { user ->
                UserItem(
                    user = user,
                    languageState = languageState,
                    onEdit = {
                        selectedUser = user
                        dialogType = "update"
                        showDialog = true
                    },
                    onDelete = {
                        selectedUser = user
                        dialogType = "delete"
                        showDialog = true
                    }
                )
            }
        }
    }

    if (showDialog) {
        when (dialogType) {
            "delete" -> {
                DeleteUserDialog(
                    user = selectedUser ?: User(username = "", password = ""),
                    languageState = languageState,
                    onConfirm = {
                        selectedUser?.let { user ->
                            userCRUDViewModel.deleteUser(user)
                        }
                        showDialog = false
                        selectedUser = null
                    },
                    onDismiss = {
                        showDialog = false
                        selectedUser = null
                    }
                )
            }
            else -> {
                UserDialog(
                    user = selectedUser ?: User(username = "", password = ""),
                    type = dialogType,
                    languageState = languageState,
                    onConfirm = { user ->
                        when (dialogType) {
                            "create" -> userCRUDViewModel.createUser(user)
                            "update" -> userCRUDViewModel.updateUser(user)
                        }
                        showDialog = false
                        selectedUser = null
                    },
                    onDismiss = {
                        showDialog = false
                        selectedUser = null
                    }
                )
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    languageState: com.example.checkers.ui.theme.LanguageState,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${languageState.getLocalizedString(context, R.string.username_field)}: ${user.username}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row {
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Edit,
                        contentDescription = languageState.getLocalizedString(context, R.string.edit_button),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(
                    onClick = onDelete
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                        contentDescription = languageState.getLocalizedString(context, R.string.delete_button),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun UserDialog(
    user: User,
    type: String,
    languageState: com.example.checkers.ui.theme.LanguageState,
    onConfirm: (User) -> Unit,
    onDismiss: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var username by remember { mutableStateOf(user.username) }
    var password by remember { mutableStateOf(user.password) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                when (type) {
                    "create" -> languageState.getLocalizedString(context, R.string.create_user_dialog_title)
                    "update" -> languageState.getLocalizedString(context, R.string.update_user_dialog_title)
                    else -> ""
                }
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(languageState.getLocalizedString(context, R.string.username_field)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(languageState.getLocalizedString(context, R.string.password_field)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedUser = user.copy(
                        username = username,
                        password = password
                    )
                    onConfirm(updatedUser)
                }
            ) {
                Text(languageState.getLocalizedString(context, R.string.confirm_button))
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
fun DeleteUserDialog(
    user: User,
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
                    R.string.delete_user_confirmation,
                    user.username
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