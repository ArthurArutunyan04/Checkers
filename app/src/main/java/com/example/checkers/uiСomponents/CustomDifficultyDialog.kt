package com.example.checkers.uiСomponents

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.checkers.R
import com.example.checkers.activities.MainActivity
import com.example.checkers.gamelogic.Difficulty
import com.example.checkers.ui.theme.Colus
import com.example.checkers.ui.theme.LocalLanguage

@Composable
fun CustomDifficultyDialog(onSelect: (Difficulty) -> Unit, onDismiss: () -> Unit) {
    val view = LocalView.current
    val context = LocalContext.current
    val languageState = LocalLanguage.current
    val window = (view.context as? Activity)?.window
    val statusBarColor = MaterialTheme.colorScheme.secondaryContainer.toArgb()

    window?.let {
        it.statusBarColor = statusBarColor
        WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightStatusBars = false
    }

    @Composable
    fun getLocalizedDifficultyName(difficulty: Difficulty): String {
        return when (difficulty) {
            Difficulty.EASY -> languageState.getLocalizedString(context, R.string.difficulty_easy)
            Difficulty.MEDIUM -> languageState.getLocalizedString(context, R.string.difficulty_medium)
            Difficulty.HARD -> languageState.getLocalizedString(context, R.string.difficulty_hard)
            Difficulty.DUEL -> languageState.getLocalizedString(context, R.string.difficulty_duel)
        }
    }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.tertiary,
                            MaterialTheme.colorScheme.background
                        ),
                        radius = 800f
                    )
                )
                .wrapContentSize(Alignment.Center)
        ) {
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = languageState.getLocalizedString(context, R.string.difficulty_level_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Colus,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Difficulty.values().forEach { difficulty ->
                        val buttonColor = when (difficulty) {
                            Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD -> MaterialTheme.colorScheme.onBackground
                            Difficulty.DUEL -> MaterialTheme.colorScheme.background
                        }

                        val textColor = when (difficulty) {
                            Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD -> MaterialTheme.colorScheme.onSurface
                            Difficulty.DUEL -> MaterialTheme.colorScheme.onSecondaryContainer
                        }

                        val textStyle = when (difficulty) {
                            Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD -> TextStyle(
                                textDecoration = TextDecoration.LineThrough,
                                color = MaterialTheme.colorScheme.background
                            )
                            else -> TextStyle()
                        }

                        Button(
                            onClick = { onSelect(difficulty) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor,
                                contentColor = textColor
                            )
                        ) {
                            Text(
                                text = getLocalizedDifficultyName(difficulty),
                                style = textStyle.copy(
                                    fontSize = 16.sp,
                                    fontFamily = Colus
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

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
    }
}