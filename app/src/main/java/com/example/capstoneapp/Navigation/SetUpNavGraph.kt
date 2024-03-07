package com.example.capstoneapp.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.capstoneapp.component.PayTest

@Composable
fun SetUpNavGraph(//임의로 만들어놓은 함수. 맘대로 수정해도됨
    navController:NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ""
    ) {
        composable(route=Screen.pay.route){ PayTest() }
    }
} // End of setUpNavGraph