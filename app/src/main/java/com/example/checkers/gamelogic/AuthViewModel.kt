package com.example.checkers.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkers.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    data class AuthState(
        val username: String = "",
        val password: String = "",
        val usernameError: String? = null,
        val passwordError: String? = null,
        val isLoginMode: Boolean = true
    )

    val authState = mutableStateOf(AuthState())
    val isLoggedIn = mutableStateOf(false)
    val currentUsername = mutableStateOf("User")
    private val _snackbarEvent = MutableSharedFlow<String>()
    val snackbarEvent = _snackbarEvent.asSharedFlow()

    fun updateUsername(username: String) {
        authState.value = authState.value.copy(
            username = username,
            usernameError = null
        )
    }

    fun updatePassword(password: String) {
        authState.value = authState.value.copy(
            password = password,
            passwordError = null
        )
    }

    fun toggleMode() {
        authState.value = authState.value.copy(
            isLoginMode = !authState.value.isLoginMode,
            usernameError = null,
            passwordError = null
        )
    }

    fun validateAndSubmit(context: Context): Boolean {
        val state = authState.value
        var isValid = true
        val usernameError: String?
        val passwordError: String?

        usernameError = when {
            state.username.isBlank() -> context.getString(R.string.username_empty_error)
            state.username.length < 3 -> context.getString(R.string.username_length_error)
            else -> null
        }

        passwordError = when {
            state.password.isBlank() -> context.getString(R.string.password_empty_error)
            state.password.length < 6 -> context.getString(R.string.password_length_error)
            else -> null
        }

        if (usernameError != null || passwordError != null) {
            isValid = false
            authState.value = authState.value.copy(
                usernameError = usernameError,
                passwordError = passwordError
            )
            return isValid
        }

        val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        if (state.isLoginMode) {
            val savedUsername = sharedPrefs.getString("username", null)
            val savedPassword = sharedPrefs.getString("password", null)
            if (savedUsername == state.username && savedPassword == state.password) {
                with(sharedPrefs.edit()) {
                    putBoolean("is_logged_in", true)
                    apply()
                }
                isLoggedIn.value = true
                currentUsername.value = state.username
                viewModelScope.launch {
                    _snackbarEvent.emit(context.getString(R.string.login_success))
                }
            } else {
                isValid = false
                viewModelScope.launch {
                    _snackbarEvent.emit(context.getString(R.string.invalid_credentials))
                }
            }
        } else {
            val savedUsername = sharedPrefs.getString("username", null)
            if (savedUsername == state.username) {
                isValid = false
                authState.value = authState.value.copy(
                    usernameError = context.getString(R.string.username_exists_error)
                )
                viewModelScope.launch {
                    _snackbarEvent.emit(context.getString(R.string.username_exists_error))
                }
            } else {
                with(sharedPrefs.edit()) {
                    putString("username", state.username)
                    putString("password", state.password)
                    putBoolean("is_logged_in", true)
                    apply()
                }
                isLoggedIn.value = true
                currentUsername.value = state.username
                viewModelScope.launch {
                    _snackbarEvent.emit(context.getString(R.string.registration_success))
                }
            }
        }
        return isValid
    }

    fun logout(context: Context) {
        val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putBoolean("is_logged_in", false)
            apply()
        }
        isLoggedIn.value = false
        currentUsername.value = sharedPrefs.getString("username", context.getString(R.string.default_username)) ?: context.getString(R.string.default_username)
        authState.value = AuthState()
        viewModelScope.launch {
            _snackbarEvent.emit(context.getString(R.string.logout_success))
        }
    }

    fun loadInitialState(context: Context) {
        val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        isLoggedIn.value = sharedPrefs.getBoolean("is_logged_in", false)
        currentUsername.value = sharedPrefs.getString("username", context.getString(R.string.default_username)) ?: context.getString(R.string.default_username)
    }
}