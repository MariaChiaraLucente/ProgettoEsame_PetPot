import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TopBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
    ) {
        // Icona Settings (a sinistra)
        IconButton(
            onClick = {
                // Navigazione verso la schermata Settings
                navController.navigate("settings_screen") {
                    launchSingleTop = true
                }
            },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }

        // Icona Notifiche (centrale, più in basso)
        //SE CI SONO NOTIFICHE, DIVENTA DI DEFAULT, SE NO, OUTLINED
        Box(
            modifier = Modifier.align(Alignment.Center).padding(top = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    // Mostra notifiche (placeholder per ora)
                    //showNotificationBox()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "Notifications",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Icona Profilo (a destra)
        IconButton(
            onClick = {
                // Navigazione verso la schermata Profilo
                navController.navigate("profile_screen") {
                    launchSingleTop = true
                }
            },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

// Funzione per mostrare il box delle notifiche
@Composable
fun showNotificationBox() {
    // Placeholder per un piccolo box popup
    Box(
        modifier = Modifier
            .padding(top = 64.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(text = "Notifications will appear here.", color = Color.Black)
    }
}