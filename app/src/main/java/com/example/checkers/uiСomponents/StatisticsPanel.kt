package com.example.checkers.uiСomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.ui.theme.Colus

@Composable
fun StatePanel() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(Color(0xFF2E211C))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "Общая статистика",
                color = Color.White,
                fontFamily = Colus,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Игр сыграно: ",
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.White
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Побед: ",
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.Green
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Поражений: ",
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.Red
                )
            }

            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = "Процент побед: ",
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.White
                )
            }

            Text(
                text = "Дуэль",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = Colus,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Дуэлей проведено: ",
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.White
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Побед Сил Света: ",
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.Green
                )
            }
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Побед Сил Тьмы: ",
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.Red
                )
            }


            Text(
                text = "Дополнительно",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = Colus,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Серия побед: ",
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.White
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Среднее время игры: ",
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.White
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Крипов добито: ",
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.White
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Крипов-магов создано: ",
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.White
                )
            }
        }
    }
}