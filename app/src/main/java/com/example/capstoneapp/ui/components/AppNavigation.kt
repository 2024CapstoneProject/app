package com.example.capstoneapp.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.ui.screens.itemMenu
import androidx.navigation.compose.composable
import com.example.capstoneapp.ui.screens.SelectSetDessertScreen
import androidx.navigation.compose.NavHost

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "itemMenu"
    ) {
        composable(route = "itemMenu"){
           itemMenu(navController = navController)
        }
        composable(route="setDessert") {
            //SelectSetDessertScreen()
        }
        // Define other routes...
    }
}




enum class NavScreen()
{
    Start,
    Burger,
    Set,
    Dessert
}