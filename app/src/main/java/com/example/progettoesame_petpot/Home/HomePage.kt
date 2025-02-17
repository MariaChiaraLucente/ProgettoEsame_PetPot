import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.progettoesame_petpot.viewmodel.ProfileViewModel


@Composable
fun HomePage(navController: NavController, homeViewModel: ProfileViewModel = viewModel(), userId: String) {
    val totalFoodStorage by homeViewModel.totalFoodStorage.collectAsState()
    val bowlLevel by homeViewModel.bowlLevel.collectAsState()

    LaunchedEffect(userId) {
        homeViewModel.loadFoodLevels(userId)
    }

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

        Spacer(modifier = Modifier.height(24.dp))

        // Indicatore di cibo
        //FoodIndicators()
        FoodDispenserView(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            foodLevel = totalFoodStorage / 1000f,
            bowlLevel = bowlLevel / 50f
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Pulsante centrale "Quick Feed"
        QuickFeedButton(navController)

        // Barra inferiore con icone
        BottomNavBar(
            selectedScreen = "Drawers",
            onScreenSelected = { navController.navigate(it) }
        )
    }
}

