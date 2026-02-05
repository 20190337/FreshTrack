package com.freshtrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val auth = Firebase.auth
    var isDarkMode by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ListItem(
                headlineContent = { Text("Account") },
                supportingContent = { Text(auth.currentUser?.email ?: "Not signed in") },
                leadingContent = { Icon(Icons.Default.AccountCircle, null) }
            )
            
            Divider()

            ListItem(
                headlineContent = { Text("Dark Mode") },
                leadingContent = { Icon(Icons.Default.DarkMode, null) },
                trailingContent = {
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { isDarkMode = it }
                    )
                }
            )

            Divider()

            ListItem(
                headlineContent = { Text("Sign Out") },
                leadingContent = { 
                    Icon(Icons.Default.Logout, null, tint = MaterialTheme.colorScheme.error) 
                },
                modifier = Modifier.clickable {
                    auth.signOut()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}