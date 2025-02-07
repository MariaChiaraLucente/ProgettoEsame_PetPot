import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.progettoesame_petpot.R
import android.graphics.Paint as NativePaint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@Composable
fun FoodDispenserView(
    modifier: Modifier = Modifier,
    foodLevel: Float, // Valore tra 0.0 (vuoto) e 1.0 (pieno)
    bowlLevel: Float, // Valore tra 0.0 (vuoto) e 1.0 (pieno)
    gradientEnd: Float = 1.0f // End point of the gradient (0.0 to 1.0)
) {
    val bowl: Painter = painterResource(R.drawable.pet_bowl)
    val gradientColors = when {
        foodLevel > 0.6f -> arrayOf(1f-foodLevel to Color.Transparent, 1f-foodLevel to Color(0xFF8CD78B))
        foodLevel > 0.3f -> arrayOf(1f-foodLevel to Color.Transparent, 1f-foodLevel to Color(0xFFD3A85F)) // Orange
        else -> arrayOf(1f-foodLevel to Color.Transparent, 1f-foodLevel to Color(0xFFCA413F))
    }
    val gradientBowlColors = when {
        bowlLevel > 0.6f -> arrayOf(bowlLevel to Color(0xFF8CD78B), bowlLevel to Color.White)
        bowlLevel > 0.3f -> arrayOf(bowlLevel to Color(0xFFD3A85F), bowlLevel to Color.White) // Orange
        else -> arrayOf(bowlLevel to Color(0xFFCA413F), bowlLevel to Color.White)
    }
    Box(modifier = modifier ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .zIndex(0f)) {
                val width = size.width
                val height = size.height

                val containerPath = Path().apply {

                    moveTo(width * 0.2f, 0f)
                    lineTo(width * 0.8f, 0f)
                    lineTo(width * 0.75f, height * 0.3f)
                    lineTo(width * 0.6f, height * 0.7f)
                    lineTo(width * 0.6f, height * 0.9f)
                    lineTo(width * 0.4f, height * 0.9f)
                    lineTo(width * 0.4f, height * 0.7f)
                    lineTo(width * 0.25f, height * 0.3f)
                    close()

                }
                drawPath(
                    path = containerPath,
                    color = Color.White,
                    style = Stroke(width = 2.dp.toPx()) // Adjust the border width as needed
                )
                drawPath(
                    path = containerPath,
                    brush = Brush.verticalGradient(
                        colorStops = gradientColors,
                        startY = 0f,
                        endY = height,
                    ),

                )
                drawIntoCanvas { canvas ->
                    val paint = NativePaint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f
                        isFakeBoldText = true
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                    canvas.nativeCanvas.drawText(
                        "${((foodLevel * 3.0)*100).roundToInt() / 100.0} / 3 Kg",
                        width / 2,
                        height / 2,
                        paint
                    )
                }

            }
            Image(
                painter = bowl,
                contentDescription = "bowl",
                modifier = Modifier
                    .offset(y = (-60).dp)
                    .zIndex(1f)
                    .width(95.dp)
                    .height(95.dp)
            )
            Canvas(modifier = Modifier
                .weight(0.1f)
                .fillMaxSize()
                .offset(y = (-50).dp)
                .zIndex(0f)) {
                val width = size.width
                val height = size.height
                val rectWidth = width * 0.3f
                val rectHeight = height * 1.2f

                val bowlPath = Path().apply {
                    addRoundRect(
                        RoundRect(
                            left = (width - rectWidth) / 2,
                            top = height - rectHeight, // Posizionato in basso
                            right = (width + rectWidth) / 2,
                            bottom = height,
                            CornerRadius(8.dp.toPx(), 8.dp.toPx())
                        )
                    )
                }
                drawPath(
                    path = bowlPath,
                    brush = Brush.horizontalGradient(
                        colorStops = gradientBowlColors,
                        startX = (width - rectWidth) / 2,
                        endX = (width + rectWidth) / 2
                    )
                )
            }
            Text(
                text = "${(bowlLevel * 50).toInt()} / 50 gr",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier
                    .offset(y = (-40).dp)
                    .zIndex(1f)
            )
        }
    }
}
