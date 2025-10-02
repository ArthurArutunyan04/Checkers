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
import com.example.checkers.ui.theme.Colus
import java.util.Locale

private const val PREFS_NAME = "app_settings"
private const val KEY_LANGUAGE = "language"
private const val KEY_SOUND_VOLUME = "sound_volume"
private const val KEY_MUSIC_VOLUME = "music_volume"

fun saveLanguage(context: Context, languageCode: String) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
}

fun getSavedLanguage(context: Context): String {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getString(KEY_LANGUAGE, getCurrentLanguage(context)) ?: getCurrentLanguage(context)
}

fun saveSoundVolume(context: Context, volume: Float) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putFloat(KEY_SOUND_VOLUME, volume).apply()
}

fun getSavedSoundVolume(context: Context): Float {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getFloat(KEY_SOUND_VOLUME, 0.7f)
}

fun saveMusicVolume(context: Context, volume: Float) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putFloat(KEY_MUSIC_VOLUME, volume).apply()
}

fun getSavedMusicVolume(context: Context): Float {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getFloat(KEY_MUSIC_VOLUME, 0.5f)
}

@Composable
fun SettingPanel() {
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
            containerColor = Color(0xFF12140F)
        )
    ) {
        Text(
            text = "${getLocalizedString("language", context)}: ${getCurrentLanguageDisplayName(context)}",
            color = Color.White
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
            color = Color.White,
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
                thumbColor = Color(0xFFD4AF37),
                activeTrackColor = Color(0xFFD4AF37),
                inactiveTrackColor = Color(0x66D4AF37)
            )
        )
        Text(
            text = "${(soundVolume.floatValue * 100).toInt()}%",
            color = Color.White,
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
            color = Color.White,
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
                thumbColor = Color(0xFFD4AF37),
                activeTrackColor = Color(0xFFD4AF37),
                inactiveTrackColor = Color(0x66D4AF37)
            )
        )
        Text(
            text = "${(musicVolume.floatValue * 100).toInt()}%",
            color = Color.White,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

fun getCurrentLanguage(context: Context): String {
    val config = context.resources.configuration
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        config.locales[0].language
    } else {
        config.locale.language
    }
}

fun getCurrentLanguageDisplayName(context: Context): String {
    val savedLang = getSavedLanguage(context)
    return when (savedLang) {
        "ru" -> context.getString(R.string.russian)
        else -> context.getString(R.string.english)
    }
}

fun setAppLanguage(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val resources = context.resources
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)

    resources.updateConfiguration(configuration, resources.displayMetrics)

    saveLanguage(context, languageCode)
}

fun getLocalizedString(key: String, context: Context): String {
    return when (key) {
        "language" -> context.getString(R.string.language)
        "sound_volume" -> context.getString(R.string.sound_volume)
        "music_volume" -> context.getString(R.string.music_volume)
        else -> key
    }
}

fun initializeAppLanguage(context: Context) {
    val savedLanguage = getSavedLanguage(context)
    if (savedLanguage != getCurrentLanguage(context)) {
        setAppLanguage(context, savedLanguage)
    }
}