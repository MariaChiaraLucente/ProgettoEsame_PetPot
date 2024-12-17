package com.example.progettoesame_petpot.Home.Components

import HomePage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.example.progettoesame_petpot.R
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawers(navController: NavController) {
    var showLeftDrawer by remember { mutableStateOf(false) }
    var showRightDrawer by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }
    var isDeafMode by remember { mutableStateOf(false) }
    var isNotificationsActive by remember { mutableStateOf(false) }
    var showGif by remember { mutableStateOf(false) }
    var isEditMode by remember { mutableStateOf(false) }
    var breed by remember { mutableStateOf("Golden Retriever") }
    var favoriteFood by remember { mutableStateOf("Meat") }
    var allergies by remember { mutableStateOf("Cats") }
    var vetName by remember { mutableStateOf("Mario Rossi") }
    var vetPhone by remember { mutableStateOf("0123456789") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5576B4))
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("", fontSize = 20.sp) },
                    modifier = Modifier.padding(15.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF5576B4)
                    ),
                    navigationIcon = {
                        IconButton(onClick = { showLeftDrawer = true }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                tint = Color.White,
                                contentDescription = "Settings",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { showRightDrawer = true }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                tint = Color.White,
                                contentDescription = "Profile",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF5576B4)),
                contentAlignment = Alignment.Center
            ) {
                HomePage(navController)
            }
        }

        if (showLeftDrawer) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { showLeftDrawer = false }
            )
        }

        AnimatedVisibility(
            visible = showLeftDrawer,
            enter = slideInHorizontally { -it } + fadeIn(),
            exit = slideOutHorizontally { -it } + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(306.dp)
                    .background(Color(0xFF5576B4))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(56.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text("Dark Mode", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { isDarkMode = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF3A5383),
                                checkedTrackColor = Color.White,
                                uncheckedThumbColor = Color(0xFF5576B4),
                                uncheckedTrackColor = Color.White
                            ),
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text("Notifications", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { isNotificationsActive = !isNotificationsActive }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                modifier = Modifier.size(35.dp),
                                contentDescription = "Notifications",
                                tint = if (isNotificationsActive) Color.White else Color.Black
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text("Deaf Mode", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = isDeafMode,
                            onCheckedChange = { isDeafMode = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF3A5383),
                                checkedTrackColor = Color.White,
                                uncheckedThumbColor = Color(0xFF5576B4),
                                uncheckedTrackColor = Color.White
                            ),
                        )
                    }
                    Text(
                        "Set a light for a deaf dog to understand when the pot is ready to eat",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        lineHeight = 16.sp,
                        color = Color.White
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text("About", fontSize = 30.sp, fontWeight = FontWeight.Light, color = Color.White)
                        Text("Contact", fontSize = 30.sp, fontWeight = FontWeight.Light, color = Color.White)
                        Text("Help", fontSize = 30.sp, fontWeight = FontWeight.Light, color = Color.White)
                        Spacer(modifier = Modifier.height(36.dp))
                        Text("Pet Pot ® All right reserved",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }

        if (showRightDrawer) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { showRightDrawer = false }
            )
        }

        AnimatedVisibility(
            visible = showRightDrawer,
            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(306.dp)
                        .background(Color(0xFF5576B4))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(26.dp))
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = Color.White,
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.End)
                        )
                        /*
                        Image(
                            painter = painterResource(R.drawable.user),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                         */
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            IconButton(onClick = { isEditMode = !isEditMode }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Profile",
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text("Edit", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(36.dp))
                        if (isEditMode) {
                            Text("Breed:", fontWeight = FontWeight.Bold, color = Color.White)
                            OutlinedTextField(
                                value = breed,
                                onValueChange = { breed = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Favorite Food:", fontWeight = FontWeight.Bold, color = Color.White)
                            OutlinedTextField(
                                value = favoriteFood,
                                onValueChange = { favoriteFood = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Allergies:", fontWeight = FontWeight.Bold, color = Color.White)
                            OutlinedTextField(
                                value = allergies,
                                onValueChange = { allergies = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Vet Name:", fontWeight = FontWeight.Bold, color = Color.White)
                            OutlinedTextField(
                                value = vetName,
                                onValueChange = { vetName = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Vet Phone:", fontWeight = FontWeight.Bold, color = Color.White)
                            OutlinedTextField(
                                value = vetPhone,
                                onValueChange = { vetPhone = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { isEditMode = false },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("Save")
                            }
                        } else {
                            Row {
                                Text("Breed: ", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(breed, fontSize = 24.sp, fontWeight = FontWeight.Light, color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row {
                                Text("Favorite Food: ", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(favoriteFood, fontSize = 24.sp, fontWeight = FontWeight.Light, color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row {
                                Text("Allergies: ", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(allergies, fontSize = 24.sp, fontWeight = FontWeight.Light, color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Divider(color = Color.White, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
                            Spacer(modifier = Modifier.height(20.dp))
                            Row {
                                Text("Vet Name: ", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(vetName, fontSize = 24.sp, fontWeight = FontWeight.Light, color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row {
                                Text("Vet Phone: ", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(vetPhone, fontSize = 24.sp, fontWeight = FontWeight.Light, color = Color.White)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("Logout", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}