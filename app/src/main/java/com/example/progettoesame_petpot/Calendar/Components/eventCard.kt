package com.example.progettoesame_petpot.Calendar.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.progettoesame_petpot.CalendarGrid

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun FeedCreationScreen(
    viewModel: CalendarViewModel = viewModel(),
    startDay: Int?, // Giorno di inizio passato dal calendario
    endDay: Int?,   // Giorno di fine passato dal calendario
    startMonth: Int?, // Mese di inizio passato dal calendario
    endMonth: Int?,
    onSave: () -> Unit
) {
    var selectedStartDay by remember { mutableStateOf(startDay) }
    var selectedEndDay by remember { mutableStateOf(endDay) }
    var selectedStartMonth by remember { mutableStateOf<Int?>(null) }
    var selectedEndMonth by remember { mutableStateOf<Int?>(null) }
    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedQuantity by remember { mutableStateOf(100f) }
    var isCalendarExpanded by remember { mutableStateOf(false) }
    var currentMonth by remember { mutableStateOf(1) }

    val monthNames = listOf("January", "February", "March")
    val daysInMonths = listOf(31, 28, 31)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5576B4))
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Programma i prossimi pasti", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1F2B85)
                ),
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                ,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),

                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color (0xFF1F2B85))
                            .clip(RoundedCornerShape(16.dp)) // Apply rounded corners

                    ){      Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)) // Apply rounded corners
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White)// Apply rounded corners

                        ) {
                            OutlinedTextField(
                                value = viewModel.feedTitle.value,
                                onValueChange = { viewModel.updateFeedTitle(it) },
                                label = { Text("Titolo") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)// Apply rounded corners
                        ) {
                            OutlinedTextField(
                                value = viewModel.feedDescription.value,
                                onValueChange = { viewModel.updateFeedDescription(it) },
                                label = { Text("Descrizione") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    } }}



                item {
                        // Card cliccabile per selezionare i giorni
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable { isCalendarExpanded = !isCalendarExpanded }, // Espandi o chiudi il calendario
                            elevation = CardDefaults.cardElevation(8.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.Center,
                                ) {
                                Text(
                                    text = "${selectedStartDay ?: "Non selezionato"}(Mese: ${startMonth})",
                                    fontSize = 16.sp,
                                    color = Color(0xFF2F34BE),
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(60.dp))
                                Text(
                                    text = "${selectedEndDay ?: "Non selezionato"}(Mese: ${endMonth})",
                                    fontSize = 16.sp,
                                    color = Color(0xFF2F34BE),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    // Animazione smooth per la CalendarGrid
                    AnimatedVisibility(
                        visible = isCalendarExpanded,
                        enter = androidx.compose.animation.expandVertically(
                            animationSpec = tween(durationMillis = 300)
                        ),
                        exit = androidx.compose.animation.shrinkVertically(
                            animationSpec = tween(durationMillis = 300)
                        )
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Selezione del mese
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(onClick = {
                                        if (currentMonth > 1) currentMonth -= 1
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Mese precedente",
                                            tint = Color(0xFF2F34BE)
                                        )
                                    }
                                    Text(
                                        text = "Mese corrente: $currentMonth",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2F34BE)
                                    )
                                    IconButton(onClick = {
                                        if (currentMonth < 12) currentMonth += 1
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Mese successivo",
                                            tint = Color(0xFF2F34BE)
                                        )
                                    }
                                }
                                CalendarGrid(
                                    daysInMonth = daysInMonths[currentMonth],
                                    selectedStartDay = selectedStartDay,
                                    selectedEndDay = selectedEndDay,
                                    selectedStartMonth = startMonth,
                                    selectedEndMonth =endMonth,
                                    currentMonth = currentMonth,
                                    onDayClick = { day ->
                                        if (selectedStartDay == null) {
                                            // Se non è selezionato alcun giorno di inizio
                                            selectedStartDay = day
                                            selectedStartMonth = currentMonth
                                        } else if (selectedEndDay == null) {
                                            // Se il giorno di fine non è ancora selezionato
                                            if (selectedStartMonth == currentMonth && day >= selectedStartDay!!) {
                                                // Caso: stesso mese e giorno selezionato dopo il giorno di inizio
                                                selectedEndDay = day
                                                selectedEndMonth = currentMonth
                                            } else if (currentMonth > selectedStartMonth!!) {
                                                // Caso: mese successivo a quello di inizio
                                                selectedEndDay = day
                                                selectedEndMonth = currentMonth
                                            } else {
                                                // Se il giorno selezionato è prima del giorno di inizio, resettiamo
                                                selectedStartDay = day
                                                selectedStartMonth = currentMonth
                                                selectedEndDay = null
                                                selectedEndMonth = null
                                            }
                                        } else {
                                            // Se entrambi i giorni sono già selezionati, resettiamo
                                            selectedStartDay = day
                                            selectedStartMonth = currentMonth
                                            selectedEndDay = null
                                            selectedEndMonth = null
                                        }
                                    }
                                )

                                    // Bottone per confermare e chiudere il calendario
                                    Button(
                                        onClick = { isCalendarExpanded = false },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F2B85)),
                                        modifier = Modifier.padding(top = 16.dp)
                                    ) {
                                        Text("Conferma", color = Color.White)
                                    }
                                }
                            }
                        }
                    }

                item {
                    // Resto del codice per l'orario e la quantità
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Orario: ${viewModel.feedOrarioFisso.value}",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 24.sp,
                            color = Color(0xFF2F34BE),
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(280.dp)
                                    // Imposta una larghezza maggiore per contenere entrambe le colonne
                                    .height(210.dp), // Imposta l'altezza per la card
                                elevation = CardDefaults.cardElevation(8.dp),

                                ) {
                                // Row per affiancare le due LazyColumn
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,  // Spazio equo tra le due colonne
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Colonna per le ore
                                    LazyColumn(
                                        modifier = Modifier
                                            .width(130.dp)  // Imposta larghezza per la colonna delle ore
                                            .fillMaxHeight()
                                            .background(Color(0xFFA2B0CA)),
                                    ) {
                                        items(24) { index1 ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        selectedHour = index1
                                                        viewModel.updateFeedOrarioFisso(
                                                            "%02d:%02d".format(
                                                                selectedHour,
                                                                selectedMinute
                                                            )
                                                        )
                                                    }
                                                    .background(
                                                        color = if ("%02d".format(index1) == "%02d".format(
                                                                selectedHour
                                                            )
                                                        ) Color(0xFF7F96C1) else Color.Transparent
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = "${"%02d".format(index1)}",
                                                    modifier = Modifier.padding(16.dp),
                                                    fontSize = 24.sp,
                                                    color = Color(0xFF2F34BE),
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }

                                    // Colonna per i minuti
                                    LazyColumn(
                                        modifier = Modifier
                                            .width(130.dp)  // Imposta larghezza per la colonna dei minuti
                                            .fillMaxHeight()
                                            .background(Color(0xFFA2B0CA)),
                                    ) {
                                        items(60) { index ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        selectedMinute = index
                                                        viewModel.updateFeedOrarioFisso(
                                                            "%02d:%02d".format(
                                                                selectedHour,
                                                                selectedMinute
                                                            )
                                                        )
                                                    }
                                                    .background(
                                                        color = if ("%02d".format(index) == "%02d".format(
                                                                selectedMinute
                                                            )
                                                        ) Color(0xFF7F96C1) else Color.Transparent
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = "${"%02d".format(index)}",
                                                    modifier = Modifier.padding(16.dp),
                                                    fontSize = 24.sp,
                                                    color = Color(0xFF2F34BE),
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Grammi a pasto: ${viewModel.feedQuantita.value}g",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 24.sp,
                        color = Color(0xFF2F34BE),
                        fontWeight = FontWeight.Bold
                    )
                    Card(
                        modifier = Modifier
                            .width(280.dp)  // Imposta una larghezza per la Card
                            .height(210.dp),  // Imposta l'altezza per la Card
                        elevation = CardDefaults.cardElevation(8.dp),
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFA2B0CA)),  // Sfondo per la LazyColumn
                        ) {
                            items((0..500 step 50).toList()) { quantity ->  // Crea una lista da 0 a 500 con step 50
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedQuantity = quantity.toFloat()
                                            viewModel.updateFeedQuantita(selectedQuantity)
                                        }
                                        .background(
                                            color = if (selectedQuantity == quantity.toFloat()) Color(
                                                0xFF7F96C1
                                            ) else Color.Transparent
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "${quantity}g",
                                        modifier = Modifier.padding(16.dp),
                                        fontSize = 24.sp,
                                        color = Color(0xFF2F34BE),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                }

                item {
                    Button(
                        onClick = {
                            viewModel.createFeedInDatabase(
                                startDay = selectedStartDay,
                                startMonth = selectedStartMonth,
                                endMonth = selectedEndMonth,
                                endDay = selectedEndDay,
                                onSuccess = { onSave() },
                                onError = { exception -> println("Errore: ${exception.message}") }
                            )
                        },
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F2B85)),
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Feed",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

