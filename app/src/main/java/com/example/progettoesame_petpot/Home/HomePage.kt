import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomePage(navController: NavController) {
    var foodLevel by remember { mutableStateOf(0.8f) }
    var bowlLevel by remember { mutableStateOf(0.5f) }
    // Colonna principale per il layout verticale
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), // Colore di sfondo della schermata
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sezione superiore con icone e il nome
        //TopBar(navController)

        // Nome dell'animale con icone decorative
        PetNameSection()

        Spacer(modifier = Modifier.height(32.dp))

        // Indicatore di cibo
        //FoodIndicators()
        FoodDispenserView(
            modifier = Modifier
                .fillMaxWidth()
                .height(390.dp),
            foodLevel = foodLevel,
            bowlLevel = bowlLevel
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Pulsante centrale "Quick Feed"
        QuickFeedButton(navController)

        // Barra inferiore con icone
        BottomNavBar(
            selectedScreen = "Drawers",
            onScreenSelected = { navController.navigate(it) }
        )
    }
}