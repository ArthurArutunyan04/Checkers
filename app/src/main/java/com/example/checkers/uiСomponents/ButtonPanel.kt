package com.example.checkers.uiСomponents

import AnimatedButton
import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.checkers.activities.GameActivity

@Composable
fun ButtonPanel() {
    val context = LocalContext.current
    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val navBarColor = 0xFF2E211C.toInt()

    window?.let {
        it.navigationBarColor = navBarColor
        WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightNavigationBars = false
    }



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2E211C))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AnimatedButton(
            text = "Играть",
            icon = Icons.Filled.PlayArrow,
            onClick = {
                println("Играть нажато!")
                val intent = Intent(context, GameActivity::class.java)
                context.startActivity(intent)
            },
            startDelay = 0L
        )

        AnimatedButton(
            text = "Настройки",
            icon = Icons.Filled.Settings,
            onClick = { /* TODO: Settings */ },
            startDelay = 500L
        )

        AnimatedButton(
            text = "Меню",
            icon = Icons.Filled.Menu,
            onClick = { /* TODO: Menu */ },
            startDelay = 1000L
        )
    }
}