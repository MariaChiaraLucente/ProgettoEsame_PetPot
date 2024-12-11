import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.example.progettoesame_petpot.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isDarkMode by remember { mutableStateOf(false) }
    var isDeafMode by remember { mutableStateOf(false) }
    var isNotificationsActive by remember { mutableStateOf(false) }
    var showGif by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(306.dp)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF5576B4))
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
                    Row (
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
                    Text("Set a light for a deaf dog to understand when the pot is ready to eat",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        lineHeight = 16.sp,
                        color = Color.White
                    )
                    Column (
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF5576B4))
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("", fontSize = 20.sp) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF5576B4)
                        ),
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Settings"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { /* Handle profile click */ }) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Profile"
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Home Screen", fontSize = 24.sp, color = Color.White)
                        Button(onClick = { showGif = true }) {
                            Text("Start GIF")
                        }
                        if (showGif) {
                            LoadingGif()
                        }
                    }
                }

            }
            }
    }
}

@Composable
fun LoadingGif() {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(GifDecoder.Factory())
        }
        .build()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.loading_dog)
                .build(),
            contentDescription = "Loading Animation",
            imageLoader = imageLoader
        )
    }
}