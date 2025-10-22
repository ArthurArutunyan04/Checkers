package com.example.checkers.uiСomponents

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import com.example.checkers.activities.AuthActivity
import com.example.checkers.ui.theme.Field
import com.example.checkers.ui.theme.Green
import com.example.checkers.uiComponents.TopPanel

@Composable
fun MainScreen(innerPadding: PaddingValues, pieces: Map<Int, Int>) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.background
                    ),
                    center = Offset.Unspecified,
                    radius = 800f
                )
            )
            .padding(innerPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopPanel(
            title = "Checkers",
            onAuthClick = {
                val intent = Intent(context, AuthActivity::class.java)
                context.startActivity(intent)
            },
            onInfoClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.10kor.ru/faq/igry-igrushki/pravila-igry-v-shashki/"))
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
            })
        GameBoard(pieces = pieces)
        ButtonPanel()
    }
}