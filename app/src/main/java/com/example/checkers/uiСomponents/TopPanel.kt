package com.example.checkers.uiСomponents

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.checkers.ui.theme.Colus
import com.example.checkers.ui.theme.TopBarColor
import com.example.checkers.ui.theme.White

@Composable
fun TopPanel(title: String, onClick: (() -> Unit)? = null) {
    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val statusBarColor = TopBarColor.toArgb()

    window?.let {
        it.statusBarColor = statusBarColor
        WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightStatusBars = false
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(TopBarColor)
            .let { modifier ->
                if (onClick != null) modifier.clickable { onClick() } else modifier
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Colus
        )
    }
}