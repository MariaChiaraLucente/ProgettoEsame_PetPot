import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.colorspace.Rgb
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.progettoesame_petpot.R
import android.graphics.Paint as NativePaint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import com.example.progettoesame_petpot.Home.Components.FoodSelector
import com.example.progettoesame_petpot.viewmodel.ProfileViewModel
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@Composable
fun FoodDispenserView(
    modifier: Modifier = Modifier,
    foodLevel: Float, // Valore tra 0.0 (vuoto) e 1.0 (pieno)
    bowlLevel: Float, // Valore tra 0.0 (vuoto) e 1.0 (pieno)
    homeViewModel: ProfileViewModel,
    userId: String,
    gradientEnd: Float = 1.0f // End point of the gradient (0.0 to 1.0)
) {
    val bowl: Painter = painterResource(R.drawable.pet_bowl)
    val totalFoodStorage by homeViewModel.totalFoodStorage.collectAsState()
    var showFoodSetupDialog by remember { mutableStateOf(false) }
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
    var selectedFood by remember { mutableStateOf("") }
    Box(modifier = modifier ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .zIndex(0f)
                .clickable(onClick = { showFoodSetupDialog = true })
            )
            {
                val width = size.width
                val height = size.height

                val containerPath = Path().apply {

                    moveTo(width * 0.27f, 0f)
                    lineTo(width * 0.73f, 0f)
                    lineTo(width * 0.68f, height * 0.35f)
                    lineTo(width * 0.56f, height * 0.75f)
                    lineTo(width * 0.56f, height * 0.9f)
                    lineTo(width * 0.44f, height * 0.9f)
                    lineTo(width * 0.44f, height * 0.75f)
                    lineTo(width * 0.32f, height * 0.35f)
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
                        color = android.graphics.Color.WHITE
                        textSize = 40f
                        isFakeBoldText = true
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                    val borderPaint = NativePaint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f
                        isFakeBoldText = true
                        textAlign = android.graphics.Paint.Align.CENTER
                        style = NativePaint.Style.STROKE
                        strokeWidth = 7f
                    }
                    canvas.nativeCanvas.drawText(
                        "${((foodLevel * 1000f).roundToInt())} / 1000 g",
                        width / 2,
                        height / 3,
                        borderPaint
                    )
                    canvas.nativeCanvas.drawText(
                        "${((foodLevel * 1000f).roundToInt())} / 1000 g",
                        width / 2,
                        height / 3,
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
                    .width(75.dp)
                    .height(75.dp)
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
                text = "${((bowlLevel * 50f).roundToInt())} / 50 g",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                modifier = Modifier
                    .offset(y = (-45).dp)
                    .zIndex(1f)
            )
        }
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp).offset(x = (50).dp, y = (110).dp)
        ) {
            // ✅ Aggiungiamo il selettore del cibo
            FoodSelector(selectedFood = selectedFood, onFoodSelected = { newFood -> selectedFood = newFood })
        }
    }
    @Composable
    fun FoodSetupDialog(
        initialFood: Int,
        onConfirm: (Int) -> Unit,
        onDismiss: () -> Unit
    ) {
        var selectedFood by remember { mutableStateOf(initialFood) }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Set Initial Food Storage", color = MaterialTheme.colorScheme.onBackground) },
            containerColor = MaterialTheme.colorScheme.secondary,
            textContentColor = MaterialTheme.colorScheme.onBackground,
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Select the amount of food available (max 1000g):", color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(8.dp))
                    Slider(
                        value = selectedFood.toFloat(),
                        onValueChange = { selectedFood = it.toInt() },
                        valueRange = 0f..1000f,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.onBackground,
                            activeTrackColor = MaterialTheme.colorScheme.onBackground,
                            inactiveTrackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    )
                    Text("$selectedFood g", color = MaterialTheme.colorScheme.onBackground)
                }
            },
            confirmButton = {
                Button(onClick = { onConfirm(selectedFood) }) {
                    Text("Confirm", color = MaterialTheme.colorScheme.onBackground)
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onBackground)
                }
            }
        )
    }

    if (showFoodSetupDialog) {
        FoodSetupDialog(
            initialFood = totalFoodStorage.toInt(),
            onConfirm = { newFoodAmount->
                homeViewModel.updateTotalFood(userId, newFoodAmount.toFloat())
                showFoodSetupDialog = false
            },
            onDismiss = { showFoodSetupDialog = false }
        )
    }
}
