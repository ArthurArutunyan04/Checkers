package com.example.checkers.uiСomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
