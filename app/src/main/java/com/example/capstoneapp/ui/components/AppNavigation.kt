package com.example.capstoneapp.ui.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.ui.screens.itemMenu
import androidx.navigation.compose.composable
import com.example.capstoneapp.ui.screens.SelectSetDessertScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.example.capstoneapp.data.model.OrderViewModel
import com.example.capstoneapp.data.repository.MenuItem
import com.example.capstoneapp.ui.screens.OrderScreen
import com.example.capstoneapp.ui.screens.PaymentScreen
import com.example.capstoneapp.ui.screens.touchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: OrderViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "touchToStart"
    ) {
        composable(route = "touchToStart"){
            touchScreen(navController = navController)
        }
        composable(route="payment") {
            PaymentScreen(navController = navController)//SelectSetDessertScreen()
        }


        composable(route = "itemMenu"){
           itemMenu(navController = navController, viewModel)
        }
        composable(route="setDessert") {
            //SelectSetDessertScreen()
        }

        composable(route="finalOrder") {
            OrderScreen(navController = navController,viewModel)
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