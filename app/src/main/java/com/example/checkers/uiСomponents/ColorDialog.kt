package com.example.checkers.uiСomponents

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.checkers.R
import com.example.checkers.gamelogic.PlayerColor
import com.example.checkers.ui.theme.Colus
import com.example.checkers.ui.theme.LocalLanguage

@Composable
fun ColorDialog(onSelect: (PlayerColor) -> Unit, onDismiss: () -> Unit) {
    val view = LocalView.current
    val context = LocalContext.current
    val languageState = LocalLanguage.current
    val window = (view.context as? Activity)?.window
    val statusBarColor = MaterialTheme.colorScheme.background.toArgb()

    window?.let {
        it.statusBarColor = statusBarColor
        WindowInsetsControllerCompat(it, it.decorView).isAppearanceLightStatusBars = false
    }

    val getLocalizedPlayerColorName: @Composable (PlayerColor) -> String = { playerColor ->
        when (playerColor) {
            PlayerColor.WHITE ->  languageState.getLocalizedString(context, R.string.forces_of_light)
            PlayerColor.BLACK ->  languageState.getLocalizedString(context, R.string.forces_of_darkness)
        }
    }

    val getLocalizedPlayerColorDescription: @Composable (PlayerColor) -> String = { playerColor ->
        when (playerColor) {
            PlayerColor.WHITE ->  languageState.getLocalizedString(context, R.string.forces_of_light_desc)
            PlayerColor.BLACK ->  languageState.getLocalizedString(context, R.string.forces_of_darkness_desc)
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
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.tertiary
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
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text =  languageState.getLocalizedString(context, R.string.choose_piece_color),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Colus,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
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
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp)),
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
                                    color = MaterialTheme.colorScheme.onSurface
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
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(1.dp, MaterialTheme.colorScheme.onSurfaceVariant, RoundedCornerShape(8.dp)),
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
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
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
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Text(
                            text =  languageState.getLocalizedString(context, R.string.cancel),
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