package com.example.checkers.uiСomponents

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.R
import com.example.checkers.activities.MainActivity
import com.example.checkers.ui.theme.Colus

@Composable
fun StatePanel() {
    val view = LocalView.current
    val context = LocalContext.current

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
                text = stringResource(R.string.general_statistics),
                color = Color.White,
                fontFamily = Colus,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = stringResource(R.string.games_played),
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
                    text = stringResource(R.string.wins),
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
                    text = stringResource(R.string.losses),
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
                    text = stringResource(R.string.win_percentage),
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.White
                )
            }

            Text(
                text = stringResource(R.string.duel),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = Colus,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = stringResource(R.string.duels_played),
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
                    text = stringResource(R.string.light_forces_wins),
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
                    text = stringResource(R.string.dark_forces_wins),
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.Red
                )
            }


            Text(
                text = stringResource(R.string.additional),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = Colus,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = stringResource(R.string.win_streak),
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
                    text = stringResource(R.string.avg_game_time),
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
                    text = stringResource(R.string.creeps_killed),
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
                    text = stringResource(R.string.mage_creeps_created),
                    color = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Color.White
                )
            }
            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    (context as? Activity)?.finish()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF12140F),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    fontSize = 16.sp,
                    fontFamily = Colus
                )
            }
        }
    }
}