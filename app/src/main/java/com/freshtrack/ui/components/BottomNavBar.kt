package com.freshtrack.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNavBar(navController: NavController, currentRoute: String) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan") },
            label = { Text("Scan") },
            selected = currentRoute == "scan",
            onClick = { navController.navigate("scan") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Inventory, contentDescription = "Inventory") },
            label = { Text("Inventory") },
            selected = currentRoute == "inventory",
            onClick = { navController.navigate("inventory") }
        )
    }
}