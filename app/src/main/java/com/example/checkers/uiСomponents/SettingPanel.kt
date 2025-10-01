package com.example.checkers.uiСomponents

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun SettingPanel() {
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
        }
    }
}

@Composable
fun LanguageSwitchButton() {
    val context = LocalContext.current

    Button(
        onClick = {
            val currentLang = getCurrentLanguage(context)
            val newLang = if (currentLang == "ru") "en" else "ru"
            setAppLanguage(context, newLang)

            (context as? Activity)?.recreate()
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "🌐 ${getCurrentLanguageName(context)}")
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

fun getCurrentLanguageName(context: Context): String {
    return when (getCurrentLanguage(context)) {
        "ru" -> "Русский"
        else -> "English"
    }
}

fun setAppLanguage(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val resources = context.resources
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)

    resources.updateConfiguration(configuration, resources.displayMetrics)
}