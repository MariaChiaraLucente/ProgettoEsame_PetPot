import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.progettoesame_petpot.R

@Composable
fun PetNameSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.paw),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .rotate(-45f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Pet name", //andrà preso dal login/registrazione il nome dell'animale
            style = MaterialTheme.typography.headlineLarge.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(R.drawable.paw),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .rotate(45f)
        )
    }
}