package com.example.checkers.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.checkers.AppContainer
import com.example.checkers.uiСomponents.AuthScreen
import com.example.checkers.ui.theme.CheckersTheme
import com.example.checkers.ui.theme.LocalThemeMode
import com.example.checkers.ui.theme.ProvideLanguage
import com.example.checkers.ui.theme.ProvideThemeMode
import com.example.checkers.viewmodel.AuthViewModel

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProvideThemeMode(this) {
                ProvideLanguage(this) {
                    val themeMode = LocalThemeMode.current.themeMode
                    CheckersTheme(themeMode = themeMode) {
                        val authViewModel = AppContainer.getAuthViewModel(this)
                        val userCRUDViewModel = AppContainer.getUserCRUDViewModel(this)

                        LaunchedEffect(Unit) {
                            authViewModel.initializeDatabase(this@AuthActivity)
                            userCRUDViewModel.initializeDatabase(com.example.checkers.database.UserDatabase.getDatabase(this@AuthActivity))
                        }

                        AuthScreen(
                            authViewModel = authViewModel,
                            userCRUDViewModel = userCRUDViewModel
                        )
                    }
                }
            }
        }
    }
}