package com.example.checkers.uiСomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.gamelogic.GameState

@Composable
fun BottomGamePanel(gameState: GameState) {
    val listState = rememberLazyListState()

    LaunchedEffect(gameState.historyLog.size) {
        if (gameState.historyLog.isNotEmpty()) {
            listState.animateScrollToItem(gameState.historyLog.size - 1)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Статистика игры",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                state = listState
            ) {
                items(gameState.historyLog.size) { index ->
                    Text(
                        text = gameState.historyLog[index],
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}