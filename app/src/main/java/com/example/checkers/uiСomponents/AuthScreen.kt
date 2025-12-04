package com.example.checkers.uiСomponents

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.R
import com.example.checkers.activities.MainActivity
import com.example.checkers.ui.theme.AuthActivityColor
import com.example.checkers.ui.theme.Colus
import com.example.checkers.ui.theme.LocalLanguage
import com.example.checkers.ui.theme.SettingActivityColor
import com.example.checkers.uiComponents.TopPanel
import com.example.checkers.viewmodel.AuthViewModel
import com.example.checkers.viewmodel.UserCRUDViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    userCRUDViewModel: UserCRUDViewModel
) {
    val context = LocalContext.current
    val languageState = LocalLanguage.current
    val authState = authViewModel.authState.value
    val isLoggedIn = authViewModel.isLoggedIn.value
    val currentUsername = authViewModel.currentUsername.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showUserCRUD by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(languageState.languageCode) {
        Log.d("AuthScreen", "Current language code: ${languageState.languageCode}")
    }

    LaunchedEffect(Unit) {
        authViewModel.loadInitialState(context, languageState)
    }

    LaunchedEffect(Unit) {
        authViewModel.snackbarEvent.collect { message ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    if (showUserCRUD) {
        UserCRUDScreen(
            userCRUDViewModel = userCRUDViewModel,
            onBack = { showUserCRUD = false }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopPanel(
                    title = if (isLoggedIn) languageState.getLocalizedString(context, R.string.account_title)
                    else if (authState.isLoginMode) languageState.getLocalizedString(context, R.string.login_title)
                    else languageState.getLocalizedString(context, R.string.register_title)
                )

                Spacer(modifier = Modifier.height(64.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isLoggedIn) {
                            Text(
                                text = languageState.getLocalizedString(context, R.string.welcome_message, currentUsername),
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontSize = 24.sp,
                                fontFamily = Colus,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Button(
                                onClick = {
                                    authViewModel.logout(context, languageState)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            ) {
                                Text(
                                    text = languageState.getLocalizedString(context, R.string.logout_button),
                                    fontSize = 16.sp,
                                    fontFamily = Colus
                                )
                            }

                            Button(
                                onClick = {
                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            ) {
                                Text(
                                    text = languageState.getLocalizedString(context, R.string.back),
                                    fontSize = 16.sp,
                                    fontFamily = Colus
                                )
                            }

                        } else {
                            OutlinedTextField(
                                value = authState.username,
                                onValueChange = { authViewModel.updateUsername(it) },
                                label = { Text(languageState.getLocalizedString(context, R.string.username_label), color = MaterialTheme.colorScheme.onSecondaryContainer) },
                                isError = authState.usernameError != null,
                                supportingText = {
                                    if (authState.usernameError != null) {
                                        Text(authState.usernameError, color = MaterialTheme.colorScheme.onSecondaryContainer, fontSize = 12.sp)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )

                            OutlinedTextField(
                                value = authState.password,
                                onValueChange = { authViewModel.updatePassword(it) },
                                label = { Text(languageState.getLocalizedString(context, R.string.password_label), color = MaterialTheme.colorScheme.onSecondaryContainer) },
                                isError = authState.passwordError != null,
                                supportingText = {
                                    if (authState.passwordError != null) {
                                        Text(authState.passwordError, color = MaterialTheme.colorScheme.onSecondaryContainer, fontSize = 12.sp)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )

                            Button(
                                onClick = { authViewModel.toggleMode() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            ) {
                                Text(
                                    text = if (authState.isLoginMode) languageState.getLocalizedString(context, R.string.switch_to_register_button)
                                    else languageState.getLocalizedString(context, R.string.switch_to_login_button),
                                    fontSize = 16.sp,
                                    fontFamily = Colus
                                )
                            }

                            Button(
                                onClick = {
                                    authViewModel.validateAndSubmit(context, languageState)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            ) {
                                Text(
                                    text = if (authState.isLoginMode) languageState.getLocalizedString(context, R.string.login_button)
                                    else languageState.getLocalizedString(context, R.string.register_button),
                                    fontSize = 16.sp,
                                    fontFamily = Colus
                                )
                            }

                            Button(
                                onClick = {
                                    showUserCRUD = true
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Text(
                                    text = languageState.getLocalizedString(context, R.string.user_management_button),
                                    fontSize = 16.sp,
                                    fontFamily = Colus
                                )
                            }

                            Button(
                                onClick = {
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.putExtra("activity_color", AuthActivityColor.toArgb())
                                    intent.putExtra("activity_name", languageState.getLocalizedString(context, R.string.authorization))

                                    context.startActivity(intent)
                                    (context as? Activity)?.finish()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            ) {
                                Text(
                                    text = languageState.getLocalizedString(context, R.string.back_button),
                                    fontSize = 16.sp,
                                    fontFamily = Colus
                                )
                            }
                        }
                    }
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .padding(top = 54.dp),
                snackbar = { snackbarData ->
                    Snackbar(
                        modifier = Modifier.padding(top = 16.dp),
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        content = {
                            Text(
                                text = snackbarData.visuals.message,
                                fontFamily = Colus,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        },
                        action = {
                            snackbarData.visuals.actionLabel?.let { actionLabel ->
                                TextButton(
                                    onClick = { snackbarData.performAction() }
                                ) {
                                    Text(
                                        text = actionLabel,
                                        fontFamily = Colus,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    )
                }
            )
        }
    }
}