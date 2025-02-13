package com.example.progettoesame_petpot


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.progettoesame_petpot.ui.theme.ProgettoEsamePetPotTheme
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    //aggiungo qui per ora il view model



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProgettoEsamePetPotTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Button(onClick = {
        val db = Firebase.database.reference
        db.child("Example")
            .child("setFloat")
            .setValue(1.42f)
            .addOnSuccessListener { /* done! */ }
            .addOnFailureListener { /* problem! */ }
    }
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProgettoEsamePetPotTheme {
        Greeting("Android")
    }
}