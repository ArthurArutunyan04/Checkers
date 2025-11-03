package com.example.checkers.uiСomponents

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.R
import com.example.checkers.activities.MainActivity
import com.example.checkers.gamelogic.*
import com.example.checkers.ui.theme.Colus
import com.example.checkers.ui.theme.LocalLanguage
import com.example.checkers.ui.theme.LocalThemeMode
import com.example.checkers.ui.theme.SettingActivityColor
import com.example.checkers.ui.theme.StatisticActivityColor

@Composable
fun SettingPanel() {
    val context = LocalContext.current
    val themeModeState = LocalThemeMode.current
    val languageState = LocalLanguage.current
    val themeMode = remember { mutableStateOf(getSavedThemeMode(context)) }

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
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LanguageSwitchButton()
            SoundVolumeSlider()
            MusicVolumeSlider()

            Button(
                onClick = {
                    val newTheme = when (themeMode.value) {
                        ThemeMode.LIGHT -> ThemeMode.DARK
                        ThemeMode.DARK -> ThemeMode.LIGHT
                    }
                    Log.d("SettingPanel", "Switching to theme: $newTheme")
                    themeMode.value = newTheme
                    saveThemeMode(context, newTheme)
                    themeModeState.themeMode = newTheme
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(
                    text = "${languageState.getLocalizedString(context, R.string.theme_mode)}: ${
                        when (themeMode.value) {
                            ThemeMode.LIGHT -> languageState.getLocalizedString(context, R.string.light_theme)
                            ThemeMode.DARK -> languageState.getLocalizedString(context, R.string.dark_theme)
                        }
                    }",
                    fontSize = 16.sp,
                    fontFamily = Colus
                )
            }

            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra("activity_color", SettingActivityColor.toArgb())
                    intent.putExtra("activity_name", languageState.getLocalizedString(context, R.string.settings))
                    context.startActivity(intent)
                    (context as? Activity)?.finish()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(
                    text = languageState.getLocalizedString(context, R.string.back),
                    fontSize = 16.sp,
                    fontFamily = Colus
                )
            }
        }
    }
}

@Composable
fun LanguageSwitchButton() {
    val context = LocalContext.current
    val languageState = LocalLanguage.current

    Button(
        onClick = {
            val newLang = if (languageState.languageCode == "ru") "en" else "ru"
            Log.d("SettingPanel", "Switching language to: $newLang")
            saveLanguage(context, newLang)
            languageState.updateLanguage(newLang)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .aspectRatio(4f),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(
            text = "${languageState.getLocalizedString(context, R.string.language)}: ${getCurrentLanguageDisplayName(context)}",
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun SoundVolumeSlider() {
    val context = LocalContext.current
    val languageState = LocalLanguage.current
    val savedVolume = getSavedSoundVolume(context)
    val soundVolume = remember { mutableFloatStateOf(savedVolume) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Text(
            text = "🔊 ${languageState.getLocalizedString(context, R.string.sound_volume)}",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Slider(
            value = soundVolume.floatValue,
            onValueChange = {
                soundVolume.floatValue = it
                saveSoundVolume(context, it)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
        Text(
            text = "${(soundVolume.floatValue * 100).toInt()}%",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun MusicVolumeSlider() {
    val context = LocalContext.current
    val languageState = LocalLanguage.current
    val savedVolume = getSavedMusicVolume(context)
    val musicVolume = remember { mutableFloatStateOf(savedVolume) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Text(
            text = "🎵 ${languageState.getLocalizedString(context, R.string.music_volume)}",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Slider(
            value = musicVolume.floatValue,
            onValueChange = {
                musicVolume.floatValue = it
                saveMusicVolume(context, it)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
        Text(
            text = "${(musicVolume.floatValue * 100).toInt()}%",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.End)
        )
    }
}