package com.example.diet_gamification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.diet_gamification.ui.theme.Diet_GamificationTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

// MainActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Diet_GamificationTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var userName by remember { mutableStateOf("Guest") }
    var userExp by remember { mutableStateOf(0) }
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Welcome, $userName!")
                        Text(text = "EXP: $userExp")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "todolist",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("todolist") { ToDoListScreen() }
            composable("workout") { WorkOutScreen() }
            composable("report") { ReportScreen() }
            composable("profile") { ProfileScreen(
                onProfileUpdated = { name, exp ->
                    userName = name
                    userExp = exp
                }
            ) }
        }
    }
}


// Bottom navigation bar composable
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "ToDoList") },
            label = { Text("To-Do List") },
            selected = navController.currentBackStackEntry?.destination?.route == "todolist",
            onClick = { navController.navigate("todolist") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.FitnessCenter, contentDescription = "Workout") },
            label = { Text("Workout") },
            selected = navController.currentBackStackEntry?.destination?.route == "workout",
            onClick = { navController.navigate("workout") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Assessment, contentDescription = "Report") },
            label = { Text("Report") },
            selected = navController.currentBackStackEntry?.destination?.route == "report",
            onClick = { navController.navigate("report") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = navController.currentBackStackEntry?.destination?.route == "profile",
            onClick = { navController.navigate("profile") }
        )
    }
}

// ToDoList screen composable
@Composable
fun ToDoListScreen() {
    Text("To-Do List Screen")
}

// WorkOut screen composable
@Composable
fun WorkOutScreen() {
    Text("Workout Screen")
}

// Report screen composable
@Composable
fun ReportScreen() {
    Text("Report Screen")
}

// Profile screen composable
@Composable
fun ProfileScreen(onProfileUpdated: (String, Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Profile Screen")
        Button(onClick = { onProfileUpdated("John Doe", 100) }) {
            Text("Update Profile")
        }
    }
}

// Preview function for MainScreen
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Diet_GamificationTheme {
        MainScreen()
    }
}
