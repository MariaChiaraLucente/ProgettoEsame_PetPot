import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.progettoesame_petpot.R

@Composable
fun FoodIndicators() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {
        // Indicatore centrale: ciotola del cane
        DogBowlIndicator(
            currentWeight = 50,
            totalWeight = 100,
            progressColor = Color(0xFFFFC107) // Giallo
        )

        // Riga con indicatori laterali
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // Elementi ai lati
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Indicatore sinistro: pollo
            FoodDispenserSideIndicator(
                icon = R.drawable.paw, // Usa icona del pollo
                currentWeight = 500,
                totalWeight = 1000,
                progressColor = Color(0xFF4CAF50), // Verde
                alignment = Alignment.Start, // Lato sinistro
                isRightFacing = true, // Semicerchio rivolto a destra
            )

            // Indicatore destro: carne
            FoodDispenserSideIndicator(
                icon = R.drawable.paw, // Usa icona della carne
                currentWeight = 300,
                totalWeight = 1000,
                progressColor = Color(0xFFF44336), // Rosso
                alignment = Alignment.End, // Lato destro
                isRightFacing = false, // Semicerchio rivolto a sinistra
            )
        }
    }
}

@Composable
fun FoodDispenserSideIndicator(
    icon: Int,
    currentWeight: Int,
    totalWeight: Int,
    progressColor: Color,
    alignment: Alignment.Horizontal,
    isRightFacing: Boolean, // Indica se il semicerchio è rivolto a destra o sinistra
) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .offset(x = if (isRightFacing) (-100).dp else (100).dp)
            .padding(horizontal = if (alignment == Alignment.Start) 0.dp else 0.dp), // Attaccato ai lati
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(200.dp)
        ) {
            val sweepAngle = 180f * (currentWeight / totalWeight.toFloat())
            val startAngle = if (isRightFacing) 270f else 90f // Destra o sinistra

            // Semicerchio grigio (sfondo)
            drawArc(
                color = Color.LightGray,
                startAngle = startAngle,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = 50.dp.toPx())
            )
            // Semicerchio colorato (progresso)
            drawArc(
                color = progressColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 50.dp.toPx())
            )
        }
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(30.dp).offset(y = (-10).dp, x = (if (isRightFacing) 25 else -25).dp)
        )
        Text(
            text = "$currentWeight",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.BottomCenter).offset(y = (-60).dp, x = (if (isRightFacing) 25 else -25).dp)
        )
    }
}

@Composable
fun DogBowlIndicator(
    currentWeight: Int,
    totalWeight: Int,
    progressColor: Color
) {
    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center

    ) {
        Canvas(modifier = Modifier.size(150.dp)) {
            val sweepAngle = 180f * (currentWeight / totalWeight.toFloat())
            // Semicerchio grigio (sfondo)
            drawArc(
                color = Color.LightGray,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = 30.dp.toPx())
            )
            // Semicerchio colorato (progresso)
            drawArc(
                color = progressColor,
                startAngle = 180f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 30.dp.toPx())
            )

            // Scritte ai bordi dell'indicatore
            drawContext.canvas.nativeCanvas.apply {
                val textPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 40f
                    textAlign = android.graphics.Paint.Align.CENTER
                    typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
                }
                // "50g" sopra la parte gialla
                drawText("50g", size.width * 0.01f, size.height * 0.45f, textPaint)
                // "100g" sopra la parte grigia
                drawText("100g", size.width * 0.98f, size.height * 0.45f, textPaint)
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.bowl), // Placeholder per icona
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(50.dp) // Ingrandire l'icona
                .offset(y = (-10).dp) // Spostarla più in basso
        )
    }
}


