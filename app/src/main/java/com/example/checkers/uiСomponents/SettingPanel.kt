package com.example.checkers.uiСomponents

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.R
import com.example.checkers.activities.MainActivity
import com.example.checkers.gamelogic.getCurrentLanguageDisplayName
import com.example.checkers.gamelogic.getLocalizedString
import com.example.checkers.gamelogic.getSavedLanguage
import com.example.checkers.gamelogic.getSavedMusicVolume
import com.example.checkers.gamelogic.getSavedSoundVolume
import com.example.checkers.gamelogic.saveLanguage
import com.example.checkers.gamelogic.saveMusicVolume
import com.example.checkers.gamelogic.saveSoundVolume
import com.example.checkers.gamelogic.setAppLanguage
import com.example.checkers.ui.theme.ActiveTrackColor
import com.example.checkers.ui.theme.Colus
import com.example.checkers.ui.theme.Field
import com.example.checkers.ui.theme.InactiveTrackColor
import com.example.checkers.ui.theme.ThumbColor
import com.example.checkers.ui.theme.TopBarColor
import com.example.checkers.ui.theme.White


@Composable
fun SettingPanel() {
    val context = LocalContext.current

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
                .padding(12.dp)
        ) {
            LanguageSwitchButton()
            SoundVolumeSlider()
            MusicVolumeSlider()
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
            saveLanguage(context, newLang)
            setAppLanguage(context, newLang)
            (context as? Activity)?.recreate()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .aspectRatio(4f),
        shape = RoundedCornerShape(8.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
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
            colors = androidx.compose.material3.SliderDefaults.colors(
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
            colors = androidx.compose.material3.SliderDefaults.colors(
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
