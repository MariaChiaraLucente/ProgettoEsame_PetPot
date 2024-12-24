import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomePage(navController: NavController) {
    // Colonna principale per il layout verticale
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5576B4)), // Colore di sfondo della schermata
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sezione superiore con icone e il nome
        //TopBar(navController)

        // Nome dell'animale con icone decorative
        PetNameSection()

        Spacer(modifier = Modifier.height(32.dp))

        // Indicatore di cibo
        FoodIndicators()

        // Pulsante centrale "Quick Feed"
        QuickFeedButton(navController)

        // Barra inferiore con icone
        BottomNavBar(
            selectedScreen = "Home",
            onScreenSelected = { navController.navigate(it) }
        )
    }
}