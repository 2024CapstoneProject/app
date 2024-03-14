package com.example.capstoneapp.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.Screens.CafeKioskScreen
import com.example.capstoneapp.Screens.CafeMenuScreen
import com.example.capstoneapp.component.KioskCafePractice0
import com.example.capstoneapp.component.KioskCafePractice5
import com.example.capstoneapp.component.PayTest

@Composable
fun SetUpNavGraph(navController:NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "KioskCafePractice0"
    ) {
        composable(route="KioskCafePractice0"){
            KioskCafePractice0(navController = navController)
        }

        composable(route="CafeKioskScreen"){
            CafeKioskScreen(navController = navController)
        }
        composable(route ="KioskCafePractice5"){
            KioskCafePractice5(navController = navController)
        }
    }
} // End of setUpNavGraph

enum class NavScreen(){
    Start,
    Menu,
    Pay,
    Card,
    Coupon,
}

