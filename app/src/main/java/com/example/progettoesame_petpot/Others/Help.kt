package com.example.progettoesame_petpot.Others

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(navController: NavController) {
    val faqs = listOf(
        "Come registrarsi?" to "Per registrarti, inserisci il tuo username e password nella schermata di registrazione. Poi, segui i passi per aggiungere il profilo del tuo animale.",
        "Come collegare il dispositivo?" to "Accendi il Bluetooth e segui le istruzioni nella sezione 'Dispositivo' per associare PetPot al tuo telefono.",
        "Come impostare un pasto programmato?" to "Vai nella sezione 'Calendario' e aggiungi un evento con l'orario e la quantità di cibo desiderata.",
        "Come vedere lo storico dei pasti?" to "Puoi controllare i pasti recenti nella sezione 'Recenti', dove troverai l'elenco dei feed completati.",
        "Come attivare la modalità per cani sordi?" to "Apri il menu laterale e attiva 'Deaf Mode' per abilitare segnali visivi per il tuo cane.",
        "Come cambiare la modalità scura?" to "Puoi attivare la Dark Mode nel menu impostazioni, oppure lasciarla seguire automaticamente il tema del sistema."
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & FAQ") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text("Domande Frequenti", fontSize = 22.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            faqs.forEach { (question, answer) ->
                ExpandableFAQCard(question, answer)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ExpandableFAQCard(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = question, fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = answer, fontSize = 16.sp)
            }
        }
    }
}
