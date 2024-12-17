import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.progettoesame_petpot.R
import com.example.progettoesame_petpot.ui.theme.ProgettoEsamePetPotTheme
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProgettoEsamePetPotTheme {
                RegistrationScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF5576B4))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image: Painter = painterResource(R.drawable.logo_petpot)
        Image(
            painter = image,
            contentDescription = "logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(200.dp).height(200.dp)
        )
        Spacer(modifier = Modifier.height(46.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Username") },
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                val db = Firebase.database.reference
                val user = mapOf("username" to username, "password" to password)
                db.child("users").push().setValue(user)
                    .addOnSuccessListener { Log.d("RIUSCITO?", "SI") }
                    .addOnFailureListener { Log.d("RIUSCITO?", "NO") }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF2e3eb8)),
            modifier = Modifier.width(200.dp).height(45.dp),
        ) {
            Text("Submit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    ProgettoEsamePetPotTheme {
        RegistrationScreen()
    }
}