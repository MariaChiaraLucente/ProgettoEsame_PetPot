package com.example.progettoesame_petpot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.progettoesame_petpot.ui.theme.ProgettoEsamePetPotTheme
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProgettoEsamePetPotTheme {
                AppNavigation()
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