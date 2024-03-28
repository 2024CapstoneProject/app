package com.example.capstoneapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.data.model.OrderViewModel
import com.example.capstoneapp.data.model.ProblemViewModel
import com.example.capstoneapp.ui.frame.NotificationScreen
import com.example.capstoneapp.ui.screens.CafeGuideScreenPreview
import com.example.capstoneapp.ui.screens.GreetingPreview
import com.example.capstoneapp.ui.screens.OrderScreen
import com.example.capstoneapp.ui.screens.PaymentScreen
import com.example.capstoneapp.ui.screens.PracticeHomeScreen
import com.example.capstoneapp.ui.screens.itemMenu
import com.example.capstoneapp.ui.screens.touchScreen

@Composable
fun AppNavigation(problemViewModel : ProblemViewModel) {
    val navController = rememberNavController()
    val viewModel: OrderViewModel = viewModel()
    val problem by problemViewModel.problem.observeAsState()

    NavHost(
        navController = navController,
        startDestination = "HamburgerHomeScreen"
    ) {
        composable(route = "HamburgerHomeScreen"){
            GreetingPreview(navController = navController)
        }

        composable(route = "HamburgerGuideScreen"){
            CafeGuideScreenPreview(navController = navController)
        }

        composable(route = "HamburgerPracticeHomeScreen"){
            PracticeHomeScreen(navController = navController)
        }

        composable(route = "touchToStart"){
            NotificationScreen(problemViewModel.getProblemValue()!!){
                touchScreen(navController = navController)
            }
        }

        composable(route="payment") {
            NotificationScreen(problemViewModel.getProblemValue()!!) {
                PaymentScreen(navController = navController)//SelectSetDessertScreen()
            }
        }

        composable(route = "itemMenu"){
            NotificationScreen(problemViewModel.getProblemValue()!!) {
                itemMenu(navController = navController, viewModel)
            }
        }

        composable(route="setDessert") {
            //SelectSetDessertScreen()
        }

        composable(route="finalOrder") {
            NotificationScreen(problemViewModel.getProblemValue()!!) {
                OrderScreen(navController = navController, viewModel)
            }
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