package com.example.checkers.uiСomponents

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.R
import com.example.checkers.activities.MainActivity
import com.example.checkers.ui.theme.Colus
import com.example.checkers.ui.theme.Field
import com.example.checkers.ui.theme.TopBarColor
import com.example.checkers.ui.theme.White
import com.example.checkers.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    val context = LocalContext.current
    val authState = viewModel.authState.value
    val isLoggedIn = viewModel.isLoggedIn.value
    val currentUsername = viewModel.currentUsername.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadInitialState(context)
    }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collect { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TopBarColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopPanel(
                title = if (isLoggedIn) stringResource(R.string.account_title)
                else if (authState.isLoginMode) stringResource(R.string.login_title)
                else stringResource(R.string.register_title)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(Field)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isLoggedIn) {
                        Text(
                            text = stringResource(R.string.welcome_message, currentUsername),
                            color = White,
                            fontSize = 24.sp,
                            fontFamily = Colus,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Button(
                            onClick = {
                                viewModel.logout(context)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TopBarColor,
                                contentColor = White
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.logout_button),
                                fontSize = 16.sp,
                                fontFamily = Colus
                            )
                        }
                    } else {
                        OutlinedTextField(
                            value = authState.username,
                            onValueChange = { viewModel.updateUsername(it) },
                            label = { Text(stringResource(R.string.username_label), color = White, fontFamily = Colus) },
                            isError = authState.usernameError != null,
                            supportingText = {
                                if (authState.usernameError != null) {
                                    Text(authState.usernameError, color = White, fontSize = 12.sp)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                color = White,
                                fontFamily = Colus
                            )
                        )

                        OutlinedTextField(
                            value = authState.password,
                            onValueChange = { viewModel.updatePassword(it) },
                            label = { Text(stringResource(R.string.password_label), color = White, fontFamily = Colus) },
                            isError = authState.passwordError != null,
                            supportingText = {
                                if (authState.passwordError != null) {
                                    Text(authState.passwordError, color = White, fontSize = 12.sp)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                color = White,
                                fontFamily = Colus
                            )
                        )

                        Button(
                            onClick = { viewModel.toggleMode() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TopBarColor,
                                contentColor = White
                            )
                        ) {
                            Text(
                                text = if (authState.isLoginMode) stringResource(R.string.switch_to_register_button)
                                else stringResource(R.string.switch_to_login_button),
                                fontSize = 16.sp,
                                fontFamily = Colus
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.validateAndSubmit(context)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TopBarColor,
                                contentColor = White
                            )
                        ) {
                            Text(
                                text = if (authState.isLoginMode) stringResource(R.string.login_button)
                                else stringResource(R.string.register_button),
                                fontSize = 16.sp,
                                fontFamily = Colus
                            )
                        }

                        Button(
                            onClick = {
                                val intent = Intent(context, MainActivity::class.java)
                                context.startActivity(intent)
                                (context as? Activity)?.finish()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TopBarColor,
                                contentColor = White
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.back_button),
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
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}