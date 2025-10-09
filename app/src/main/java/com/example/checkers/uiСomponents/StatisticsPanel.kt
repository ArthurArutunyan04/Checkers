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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.R
import com.example.checkers.activities.MainActivity
import com.example.checkers.ui.theme.Colus
import com.example.checkers.ui.theme.LocalLanguage
import com.example.checkers.ui.theme.Red

@Composable
fun StatePanel() {
    val context = LocalContext.current
    val languageState = LocalLanguage.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = languageState.getLocalizedString(context, R.string.general_statistics),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontFamily = Colus,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.games_played),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.wins),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.losses),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Red
                )
            }

            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.win_percentage),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Text(
                text = languageState.getLocalizedString(context, R.string.duel),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = Colus,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.duels_played),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.light_forces_wins),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.dark_forces_wins),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = Red
                )
            }

            Text(
                text = languageState.getLocalizedString(context, R.string.additional),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = Colus,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.win_streak),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.avg_game_time),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.creeps_killed),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.mage_creeps_created),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "1",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
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
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.cancel),
                    fontSize = 16.sp,
                    fontFamily = Colus
                )
            }
        }
    }
}