import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun QuickFeedButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("QuickFeed") },
        shape = CircleShape,
        modifier = Modifier
            .size(110.dp)
            .offset(y = (-40).dp)
            .border(3.dp, Color.Black, CircleShape), // Add a black border, // Increase the button size
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = "Quick Feed",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall, // Use a larger text style
            textAlign = TextAlign.Center
        )
    }
}