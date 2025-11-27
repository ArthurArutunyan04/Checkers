package com.example.checkers.viewmodel

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkers.R
import com.example.checkers.database.UserDatabase
import com.example.checkers.model.Statistics
import com.example.checkers.model.User
import com.example.checkers.ui.theme.LanguageState
import com.example.checkers.ui.theme.LocalLanguage
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

    private var database: UserDatabase? = null

    fun initializeDatabase(context: Context) {
        if (database == null) {
            database = UserDatabase.getDatabase(context)
        }
    }

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

    fun validateAndSubmit(context: Context, languageState: LanguageState): Boolean {
        val state = authState.value
        var isValid = true
        val usernameError: String?
        val passwordError: String?

        usernameError = when {
            state.username.isBlank() -> languageState.getLocalizedString(
                context,
                R.string.username_empty_error
            )

            state.username.length < 3 -> languageState.getLocalizedString(
                context,
                R.string.username_length_error
            )

            else -> null
        }

        passwordError = when {
            state.password.isBlank() -> languageState.getLocalizedString(
                context,
                R.string.password_empty_error
            )

            state.password.length < 6 -> languageState.getLocalizedString(
                context,
                R.string.password_length_error
            )

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

        viewModelScope.launch {
            try {
                val userDao = database?.userDao()
                if (state.isLoginMode) {
                    val user = userDao?.getUser(state.username, state.password)
                    if (user != null) {
                        isLoggedIn.value = true
                        currentUsername.value = state.username
                        val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        with(sharedPrefs.edit()) {
                            putString("current_username", state.username)
                            apply()
                        }
                        _snackbarEvent.emit(
                            languageState.getLocalizedString(
                                context,
                                R.string.login_success
                            )
                        )
                    } else {
                        isValid = false
                        _snackbarEvent.emit(
                            languageState.getLocalizedString(
                                context,
                                R.string.invalid_credentials
                            )
                        )
                    }
                } else {
                    if (userDao?.userExists(state.username) == true) {
                        isValid = false
                        authState.value = authState.value.copy(
                            usernameError = languageState.getLocalizedString(
                                context,
                                R.string.username_exists_error
                            )
                        )
                        _snackbarEvent.emit(
                            languageState.getLocalizedString(
                                context,
                                R.string.username_exists_error
                            )
                        )
                    } else {
                        val newUser = User(state.username, state.password)
                        userDao?.insertUser(newUser)
                        val newStatistics = Statistics(state.username)
                        database?.statisticsDao()?.insertStatistics(newStatistics)

                        isLoggedIn.value = true
                        currentUsername.value = state.username
                        val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        with(sharedPrefs.edit()) {
                            putString("current_username", state.username)
                            apply()
                        }
                        _snackbarEvent.emit(
                            languageState.getLocalizedString(
                                context,
                                R.string.registration_success
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                isValid = false
                _snackbarEvent.emit("Error: ${e.message}")
            }
        }
        return isValid
    }

    fun logout(context: Context, languageState: LanguageState) {
        isLoggedIn.value = false
        currentUsername.value = languageState.getLocalizedString(context, R.string.default_username)
        authState.value = AuthState()

        val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            remove("current_username")
            apply()
        }

        viewModelScope.launch {
            _snackbarEvent.emit(languageState.getLocalizedString(context, R.string.logout_success))
        }
    }

    fun loadInitialState(context: Context, languageState: LanguageState) {
        initializeDatabase(context)
        val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPrefs.getString("current_username", null)

        if (!savedUsername.isNullOrEmpty()) {
            viewModelScope.launch {
                val userDao = database?.userDao()
                if (userDao?.getUserByUsername(savedUsername) != null) {
                    isLoggedIn.value = true
                    currentUsername.value = savedUsername
                } else {
                    with(sharedPrefs.edit()) {
                        remove("current_username")
                        apply()
                    }
                    currentUsername.value = languageState.getLocalizedString(context, R.string.default_username)
                }
            }
        } else {
            currentUsername.value = languageState.getLocalizedString(context, R.string.default_username)
        }
    }

    @Composable
    fun validateAndSubmit(context: Context) {
        val languageState = LocalLanguage.current
        validateAndSubmit(context, languageState)
    }

    @Composable
    fun logout(context: Context) {
        val languageState = LocalLanguage.current
        logout(context, languageState)
    }

    @Composable
    fun loadInitialState(context: Context) {
        val languageState = LocalLanguage.current
        loadInitialState(context, languageState)
    }
}