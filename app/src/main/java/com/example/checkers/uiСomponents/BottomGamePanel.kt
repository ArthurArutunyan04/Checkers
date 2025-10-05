package com.example.checkers.uiСomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.R
import com.example.checkers.gamelogic.GameState
import com.example.checkers.ui.theme.Colus
import com.example.checkers.ui.theme.TopBarColor
import com.example.checkers.ui.theme.White

@Composable
fun BottomGamePanel(gameState: GameState) {
    val context = LocalContext.current
    val listState = rememberLazyListState()

    LaunchedEffect(gameState.historyLog.size) {
        if (gameState.historyLog.isNotEmpty()) {
            listState.animateScrollToItem(gameState.historyLog.size - 1)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(TopBarColor),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = TopBarColor,
            contentColor = White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .background(TopBarColor),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.game_progress),
                    fontSize = 16.sp,
                    fontFamily = Colus,
                    fontWeight = FontWeight.Bold,
                    color = White
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
                        fontFamily = Colus,
                        color = White,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}