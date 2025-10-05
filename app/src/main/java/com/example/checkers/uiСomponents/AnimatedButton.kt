import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.ui.theme.Colus
import com.example.checkers.ui.theme.Field
import com.example.checkers.ui.theme.Transparent
import com.example.checkers.ui.theme.White
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AnimatedButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    startDelay: Long = 0L
) {
    var iconAngle by remember { mutableStateOf(0f) }
    var textAngle by remember { mutableStateOf(0f) }

    val iconOffsetX = remember { Random.nextInt(-6, 6).dp }
    val textOffsetX = remember { Random.nextInt(-6, 6).dp }

    LaunchedEffect(Unit) {
        delay(startDelay)
        while (true) {
            iconAngle = 0f
            textAngle = 0f
            delay(1000)

            iconAngle = 2f
            textAngle = -2f
            delay(1000)

            iconAngle = 0f
            textAngle = 0f
            delay(1000)

            iconAngle = -2f
            textAngle = 2f
            delay(1000)
        }
    }

    Surface(
        onClick = onClick,
        color = Transparent,
    ) {
        Column(
            modifier = Modifier
                .width(110.dp)
                .height(80.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(x = iconOffsetX)
                    .rotate(iconAngle)
                    .background(Field),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .offset(x = textOffsetX)
                    .rotate(textAngle)
                    .background(Field),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    fontFamily = Colus,
                    fontSize = 13.sp,
                    color = White,
                    modifier = Modifier.rotate(-textAngle)
                )
            }
        }
    }
}