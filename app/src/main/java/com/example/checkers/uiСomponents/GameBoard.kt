package com.example.checkers.uiСomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign

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
//            .fillMaxHeight(0.537f)
            .height(383.dp)
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
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(start = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (i in 0 until 8) {
                    val letter = ('A' + i).toString()
                    Text(
                        text = letter,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.width(30.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .width(16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (i in 8 downTo 1) {
                        Text(
                            text = i.toString(),
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .height(43.dp)
                                .wrapContentHeight()
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(1f)
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
                                                    width = 1.dp,
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

                Column(
                    modifier = Modifier
                        .width(16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (i in 8 downTo 1) {
                        Text(
                            text = i.toString(),
                            textAlign = TextAlign.Center,
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .height(43.dp)
                                .wrapContentHeight()
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(start = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (i in 0 until 8) {
                    val letter = ('A' + i).toString()
                    Text(
                        text = letter,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.width(30.dp)
                    )
                }
            }
        }
    }
}