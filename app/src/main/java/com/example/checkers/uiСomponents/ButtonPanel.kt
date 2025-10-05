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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.checkers.R
import com.example.checkers.activities.GameActivity
import com.example.checkers.activities.SettingsActivity
import com.example.checkers.activities.StateActivity
import com.example.checkers.ui.theme.Field
import com.example.checkers.ui.theme.TopBarColor

@Composable
fun ButtonPanel() {
    val context = LocalContext.current
    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val navBarColor = Field.toArgb()

    window?.let {
        it.navigationBarColor = navBarColor
        WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightNavigationBars = false
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TopBarColor)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AnimatedButton(
            text = stringResource(R.string.play),
            icon = Icons.Filled.PlayArrow,
            onClick = {
                val intent = Intent(context, GameActivity::class.java)
                context.startActivity(intent)
            },
            startDelay = 0L
        )

        AnimatedButton(
            text = stringResource(R.string.statistics),
            icon = Icons.Filled.Info,
            onClick = {
                val intent = Intent(context, StateActivity::class.java)
                context.startActivity(intent)
            },
            startDelay = 1000L
        )

        AnimatedButton(
            text = stringResource(R.string.settings),
            icon = Icons.Filled.Settings,
            onClick = {
                 val intent = Intent(context, SettingsActivity::class.java)
                 context.startActivity(intent)
            },
            startDelay = 500L
        )
    }
}
