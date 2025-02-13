package com.example.progettoesame_petpot.Home.Components

import HomePage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.progettoesame_petpot.viewmodel.ProfileViewModel

object ThemeSettings {
    var isDarkMode by mutableStateOf(false)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawers(navController: NavController, userId: String, profileViewModel: ProfileViewModel = viewModel()) {

    val systemDarkMode = isSystemInDarkTheme()
    var isDarkMode by rememberSaveable { mutableStateOf(ThemeSettings.isDarkMode) }
    ThemeSettings.isDarkMode = isDarkMode

    var showLeftDrawer by remember { mutableStateOf(false) }
    var showRightDrawer by remember { mutableStateOf(false) }
    var isDeafMode by remember { mutableStateOf(false) }
    var isNotificationsActive by remember { mutableStateOf(false) }
    var breed by remember { mutableStateOf("") }
    var favoriteFood by remember { mutableStateOf("") }
    var allergies by remember { mutableStateOf("") }
    var vetName by remember { mutableStateOf("") }
    var vetPhone by remember { mutableStateOf("") }

    var isEditMode by remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        profileViewModel.loadProfile(userId)
    }

    val dogProfile = profileViewModel.dogProfile

    LaunchedEffect(dogProfile) {
        breed = dogProfile.breed
        favoriteFood = dogProfile.favoriteFood
        allergies = dogProfile.allergies
        vetName = dogProfile.vetName
        vetPhone = dogProfile.vetPhone
    }

    LaunchedEffect(showRightDrawer) {
        if (!showRightDrawer) {
            isEditMode = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("", fontSize = 20.sp) },
                    modifier = Modifier.padding(15.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
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
                    .background(MaterialTheme.colorScheme.background),
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
                    .background(MaterialTheme.colorScheme.background)
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
                            onCheckedChange = {
                                isDarkMode = it
                                ThemeSettings.isDarkMode = it
                                              },
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
                        .background(MaterialTheme.colorScheme.background)
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
                        }
                        Spacer(modifier = Modifier.height(36.dp))
                        if (isEditMode) {
                            EditableTextField("Breed:", breed) { breed = it }
                            EditableTextField("Favorite Food:", favoriteFood) { favoriteFood = it }
                            EditableTextField("Allergies:", allergies) { allergies = it }
                            EditableTextField("Vet Name:", vetName) { vetName = it }
                            EditableTextField("Vet Phone:", vetPhone) { vetPhone = it }

                            Button(
                                onClick = {
                                    isEditMode = false
                                    val updatedProfile = dogProfile.copy(
                                        breed = breed,
                                        favoriteFood = favoriteFood,
                                        allergies = allergies,
                                        vetName = vetName,
                                        vetPhone = vetPhone
                                    )
                                    profileViewModel.updateProfile(userId, updatedProfile)
                                },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("Save")
                            }
                        } else {
                            ProfileInfoRow("Breed:", dogProfile.breed)
                            ProfileInfoRow("Favorite Food:", dogProfile.favoriteFood)
                            ProfileInfoRow("Allergies:", dogProfile.allergies)
                            Divider(color = Color.White, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
                            ProfileInfoRow("Vet Name:", dogProfile.vetName)
                            ProfileInfoRow("Vet Phone:", dogProfile.vetPhone)
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                onClick = {
                                    navController.navigate("login")
                                    profileViewModel.logout()
                                },
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
                            ) {
                                Text(text = "Logout", color = MaterialTheme.colorScheme.onBackground)
                            }                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

// ✅ Componente per visualizzare le informazioni
@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row {
        Text("$label ", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(value, fontSize = 24.sp, fontWeight = FontWeight.Light, color = Color.White)
    }
    Spacer( modifier = Modifier.height(16.dp) )
}

// ✅ Componente per campi editabili
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(label, fontWeight = FontWeight.Bold, color = Color.White)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
