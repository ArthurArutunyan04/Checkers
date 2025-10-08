package com.example.checkers.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.checkers.gamelogic.initializeAppLanguage
import com.example.checkers.uiСomponents.AuthScreen
import com.example.checkers.ui.theme.CheckersTheme
import com.example.checkers.viewmodel.AuthViewModel

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeAppLanguage(this)

        setContent {
            CheckersTheme {
                val viewModel: AuthViewModel = viewModel()
                AuthScreen(viewModel = viewModel)
            }
        }
    }
}