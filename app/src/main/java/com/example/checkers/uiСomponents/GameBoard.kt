package com.example.checkers.uiСomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

@Composable
fun GameBoard(
    pieces: Map<Int, Int>,
    onCellClick: (Int) -> Unit = {},
    selectedCell: Int? = null,
    isGameOver: Boolean = false,
    possibleMoves: List<Int> = emptyList()
) {
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
                    val cellColor = if (isBlack) Color.Black else Color.White
                    val isSelected = index == selectedCell && !isGameOver
                    val isPossible = index in possibleMoves && !isGameOver

                    Box(
                        modifier = Modifier
                            .size(width = 30.dp, height = 43.dp)
                            .background(cellColor)
                            .then(
                                if (isSelected) {
                                    Modifier.background(Color.Yellow.copy(alpha = 0.3f))
                                } else if (isPossible) {
                                    Modifier.border(
                                        width = 2.dp,
                                        color = Color.Green.copy(alpha = 0.3f)
                                    )
                                } else {
                                    Modifier
                                }
                            )
                            .clickable(enabled = !isGameOver) { onCellClick(index) },
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