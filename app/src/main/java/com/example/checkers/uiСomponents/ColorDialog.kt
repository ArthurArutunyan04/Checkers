package com.example.checkers.uiСomponents

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.checkers.R
import com.example.checkers.gamelogic.PlayerColor
import com.example.checkers.ui.theme.Colus

@Composable
fun ColorDialog(onSelect: (PlayerColor) -> Unit, onDismiss: () -> Unit) {
    val view = LocalView.current
    val context = LocalContext.current
    val window = (view.context as? Activity)?.window
    val statusBarColor = 0xFF2E211C.toInt()

    window?.let {
        it.statusBarColor = statusBarColor
        WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightStatusBars = false
    }

    val getLocalizedPlayerColorName: @Composable (PlayerColor) -> String = { playerColor ->
        when (playerColor) {
            PlayerColor.WHITE -> stringResource(R.string.forces_of_light)
            PlayerColor.BLACK -> stringResource(R.string.forces_of_darkness)
        }
    }

    val getLocalizedPlayerColorDescription: @Composable (PlayerColor) -> String = { playerColor ->
        when (playerColor) {
            PlayerColor.WHITE -> stringResource(R.string.forces_of_light_desc)
            PlayerColor.BLACK -> stringResource(R.string.forces_of_darkness_desc)
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
                    .width(320.dp)
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
                        text = stringResource(R.string.choose_piece_color),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Colus,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                                .height(80.dp)
                                .clickable { onSelect(PlayerColor.WHITE) }
                                .background(
                                    color = Color(0xFFF5F5F5),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = R.drawable.white_win),
                                    contentDescription = getLocalizedPlayerColorDescription(PlayerColor.WHITE),
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = getLocalizedPlayerColorName(PlayerColor.WHITE),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = Colus,
                                    color = Color.Black
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                                .height(80.dp)
                                .clickable { onSelect(PlayerColor.BLACK) }
                                .background(
                                    color = Color(0xFF212121),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = R.drawable.black_win),
                                    contentDescription = getLocalizedPlayerColorDescription(PlayerColor.BLACK),
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = getLocalizedPlayerColorName(PlayerColor.BLACK),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = Colus,
                                    color = Color.White
                                )
                            }
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
                            fontFamily = Colus,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}