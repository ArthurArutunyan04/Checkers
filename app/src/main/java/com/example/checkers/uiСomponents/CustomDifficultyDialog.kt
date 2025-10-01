package com.example.checkers.uiСomponents

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.example.checkers.gamelogic.Difficulty
import com.example.checkers.ui.theme.Colus

@Composable
fun CustomDifficultyDialog(onSelect: (Difficulty) -> Unit, onDismiss: () -> Unit) {
    val view = LocalView.current
    val context = LocalContext.current
    val window = (view.context as? Activity)?.window
    val statusBarColor = 0xFF2E211C.toInt()

    window?.let {
        it.statusBarColor = statusBarColor
        WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightStatusBars = false
    }

    @Composable
    fun getLocalizedDifficultyName(difficulty: Difficulty): String {
        return when (difficulty) {
            Difficulty.EASY -> stringResource(R.string.difficulty_easy)
            Difficulty.MEDIUM -> stringResource(R.string.difficulty_medium)
            Difficulty.HARD -> stringResource(R.string.difficulty_hard)
            Difficulty.DUEL -> stringResource(R.string.difficulty_duel)
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
                            Color(0xFF3A2A24),
                            Color(0xFF12140F)
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
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFF2E211C))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.difficulty_level_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Colus,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Difficulty.values().forEach { difficulty ->
                        val buttonColor = when (difficulty) {
                            Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD -> Color(0xFF969292)
                            Difficulty.DUEL -> Color(0xFF12140F)
                        }

                        val textColor = when (difficulty) {
                            Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD -> Color.Black
                            Difficulty.DUEL -> Color.White
                        }

                        val textStyle = when (difficulty) {
                            Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD -> TextStyle(
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Black
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
                        onClick = onDismiss,
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
    }
}
