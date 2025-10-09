package com.example.checkers.uiСomponents

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.checkers.ui.theme.Colus
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

@Composable
fun TopPanel(
    title: String,
    onClick: (() -> Unit)? = null,
    onAuthClick: (() -> Unit)? = null
) {
    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val statusBarColor = MaterialTheme.colorScheme.secondaryContainer.toArgb()

    window?.let {
        it.statusBarColor = statusBarColor
        WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightStatusBars = false
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .let { modifier ->
                if (onClick != null) modifier.clickable { onClick() } else modifier
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Colus
        )

        if (onAuthClick != null) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Authorization",
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .clickable { onAuthClick() }
            )
        }
    }
}