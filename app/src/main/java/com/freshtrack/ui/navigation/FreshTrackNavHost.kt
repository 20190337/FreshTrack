package com.freshtrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.freshtrack.ui.screens.*

@Composable
fun FreshTrackNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("inventory") { InventoryScreen(navController) }
        composable("scan") { ScanScreen(navController) }
        composable("add_product/{barcode}") { backStackEntry ->
            val barcode = backStackEntry.arguments?.getString("barcode")
            AddProductScreen(navController, barcode)
        }
        composable("product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(navController, productId)
        }
        composable("settings") { SettingsScreen(navController) }
    }
}
