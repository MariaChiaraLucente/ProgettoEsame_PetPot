import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.progettoesame_petpot.R


@Composable
fun BottomNavBar(
    selectedScreen: String,
    onScreenSelected: (String) -> Unit
) {
    val screens = listOf("calendar", "HomePage", "Recent")
    val icons = listOf(
        R.drawable.calendar,  // Placeholder per icona Program
        R.drawable.home,     // Placeholder per icona Home
        R.drawable.recent    // Placeholder per icona Recent
    )

    val density = LocalDensity.current
    val screenHeightPx = with(density) { LocalConfiguration.current.screenHeightDp.dp.toPx() }
    val outerRadius = screenHeightPx / 2 // Raggio esterno
    val innerRadius = outerRadius - with(density) { 100.dp.toPx() } // Raggio interno per creare il "buco"
    val iconOffsetPx = with(density) { 100.dp.toPx() } // Precalcolo dell'offset delle icone

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 70.dp) // Abbassiamo la ciambella
            .height((outerRadius / 1.2f).dp) // Riduciamo la sua altezza
    ) {
        // Disegna la semi-ciambella più piccola e abbassata
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-30).dp)
                .height((outerRadius / 1.5f).dp) // Riduciamo la sua altezza per renderla più compatta
        ) {
            drawArc(
                color = Color(0xFF2E3A59), // Colore sfondo della semi-ciambella
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false, // Solo il bordo
                size = Size(width = size.width, height = outerRadius * 1.4f), // Riduciamo la curvatura
                style = Stroke(
                    width = outerRadius - innerRadius, // Spessore pari alla differenza tra i raggi
                    cap = StrokeCap.Butt
                )
            )
        }

        // Icona della batteria al centro dello spazio vuoto
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-90).dp) // Posiziona l'icona sotto la ciambella
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bluetooth), // Placeholder per batteria
                contentDescription = "Battery Status",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "88%", // Testo batteria
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Posizionamento delle icone in layout curvo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // Icone laterali (Program e Recent)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .offset(y = (-90).dp), // Posizione inferiore rispetto all'icona Home
                horizontalArrangement = Arrangement.SpaceBetween // Distanzia le icone ai lati
            ) {

               BottomNavIcon(
                    isSelected = selectedScreen == "calendar",
                    iconResId = R.drawable.calendar,
                    label = "calendar",
                    onClick = {
                        onScreenSelected("calendar")
                        },
                    modifier = Modifier.offset(x = 22.dp),
                    verticalOffset = 10.dp // Più in basso rispetto a Home
                )

                // Recent
                BottomNavIcon(
                    isSelected = selectedScreen == "Recent",
                    iconResId = R.drawable.recent,
                    label = "Recent",
                    onClick = { onScreenSelected("Recent") },
                    modifier = Modifier.offset(x = (-22).dp),
                    verticalOffset = 10.dp // Più in basso rispetto a Home
                )
            }

            // Icona centrale (Home)
            BottomNavIcon(
                isSelected = selectedScreen == "Drawers",
                iconResId = R.drawable.home,
                label = "Home",
                onClick = { onScreenSelected("Drawers") },
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Allinea al centro della ciambella
                    .offset(y = (-150).dp), // Posizione più alta
                verticalOffset = 10.dp // Offset specifico per Home
            )
        }
    }
}

@Composable
fun BottomNavIcon(
    isSelected: Boolean,
    iconResId: Int,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    verticalOffset: Dp
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF8F9EDC) else Color.Transparent,
        animationSpec = tween(durationMillis = 300)
    )
    val iconSize by animateDpAsState(
        targetValue = if (isSelected) 50.dp else 40.dp,
        animationSpec = tween(durationMillis = 300)
    )
    val iconTint by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.Gray,
        animationSpec = tween(durationMillis = 300)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .size(90.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .offset(y = verticalOffset) // Applica l'offset verticale
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = iconTint
        )
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}







