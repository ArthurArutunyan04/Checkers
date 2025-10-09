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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.R
import com.example.checkers.activities.MainActivity
import com.example.checkers.gamelogic.*
import com.example.checkers.ui.theme.*

@Composable
fun SettingPanel() {
    val context = LocalContext.current
    val themeMode = remember { mutableStateOf(getSavedThemeMode(context)) }
    Log.d("SettingPanel", "Initial theme mode: ${themeMode.value}")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(TopBarColor)
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
                        ThemeMode.SYSTEM -> ThemeMode.LIGHT
                        ThemeMode.LIGHT -> ThemeMode.DARK
                        ThemeMode.DARK -> ThemeMode.SYSTEM
                    }
                    Log.d("SettingPanel", "Switching to theme: $newTheme")
                    themeMode.value = newTheme
                    saveThemeMode(context, newTheme)
                    (context as? Activity)?.let {
                        Log.d("SettingPanel", "Recreating activity")
                        it.recreate()
                    } ?: Log.e("SettingPanel", "Context is not an Activity")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Field,
                    contentColor = White
                )
            ) {
                Text(
                    text = "${getLocalizedString("theme_mode", context)}: ${
                        when (themeMode.value) {
                            ThemeMode.SYSTEM -> stringResource(R.string.system_theme)
                            ThemeMode.LIGHT -> stringResource(R.string.light_theme)
                            ThemeMode.DARK -> stringResource(R.string.dark_theme)
                        }
                    }",
                    fontSize = 16.sp,
                    fontFamily = Colus
                )
            }

            Button(
                onClick = {
                    Log.d("SettingPanel", "Navigating to MainActivity")
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    (context as? Activity)?.finish()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TopBarColor,
                    contentColor = White
                )
            ) {
                Text(
                    text = stringResource(R.string.back),
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
    val currentLang = remember { getSavedLanguage(context) }

    Button(
        onClick = {
            val newLang = if (currentLang == "ru") "en" else "ru"
            Log.d("SettingPanel", "Switching language to: $newLang")
            saveLanguage(context, newLang)
            setAppLanguage(context, newLang)
            (context as? Activity)?.let {
                Log.d("SettingPanel", "Recreating activity for language change")
                it.recreate()
            } ?: Log.e("SettingPanel", "Context is not an Activity")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .aspectRatio(4f),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Field
        )
    ) {
        Text(
            text = "${getLocalizedString("language", context)}: ${getCurrentLanguageDisplayName(context)}",
            color = White
        )
    }
}

@Composable
fun SoundVolumeSlider() {
    val context = LocalContext.current
    val savedVolume = getSavedSoundVolume(context)
    val soundVolume = remember { mutableFloatStateOf(savedVolume) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Text(
            text = "🔊 ${getLocalizedString("sound_volume", context)}",
            color = White,
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
                thumbColor = ThumbColor,
                activeTrackColor = ActiveTrackColor,
                inactiveTrackColor = InactiveTrackColor
            )
        )
        Text(
            text = "${(soundVolume.floatValue * 100).toInt()}%",
            color = White,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun MusicVolumeSlider() {
    val context = LocalContext.current
    val savedVolume = getSavedMusicVolume(context)
    val musicVolume = remember { mutableFloatStateOf(savedVolume) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Text(
            text = "🎵 ${getLocalizedString("music_volume", context)}",
            color = White,
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
                thumbColor = ThumbColor,
                activeTrackColor = ActiveTrackColor,
                inactiveTrackColor = InactiveTrackColor
            )
        )
        Text(
            text = "${(musicVolume.floatValue * 100).toInt()}%",
            color = White,
            modifier = Modifier.align(Alignment.End)
        )
    }
}