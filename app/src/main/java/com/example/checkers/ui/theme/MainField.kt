package com.example.checkers.ui.theme

import AnimatedButton
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.checkers.GameActivity
import org.w3c.dom.Text


@Composable
fun TopPanel(title: String) {
    val view = LocalView.current
    val window = (view.context as? android.app.Activity)?.window
    val statusBarColor = 0xFF2E211C.toInt()

    window?.let {
        it.statusBarColor = statusBarColor
        WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightStatusBars = false
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color(0xFF2E211C))
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color(0xFFFFFFFF),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Colus
        )
    }
}

@Composable
fun GameBoard(pieces: Map<Int, Int>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.51f)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFD7B899),
                        Color(0xFF8B5A2B),
                        Color(0xFF5C4033)
                    ),
                    radius = 600f
                )
            )
            .padding(10.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 8),
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E1714)),
            content = {
                items(count = 64) { index ->
                    val isBlack = (index / 8 + index % 8) % 2 != 0
                    Box(
                        modifier = Modifier
                            .size(width = 30.dp, height = 43.dp)
                            .background(if (isBlack) Color.Black else Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        pieces[index]?.let { imageRes ->
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(0.9f),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ButtonPanel() {
    val context = LocalContext.current
    val view = LocalView.current
    val window = (view.context as? android.app.Activity)?.window
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
