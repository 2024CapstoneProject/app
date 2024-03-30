package com.example.capstoneapp.fastfood.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.fastfood.data.model.OrderViewModel
import com.example.capstoneapp.fastfood.data.model.ProblemViewModel
import com.example.capstoneapp.fastfood.ui.frame.NotificationScreen
import com.example.capstoneapp.fastfood.ui.screens.CafeGuideScreenPreview
import com.example.capstoneapp.fastfood.ui.screens.GreetingPreview
import com.example.capstoneapp.fastfood.ui.screens.OrderScreen
import com.example.capstoneapp.fastfood.ui.screens.PaymentScreen
import com.example.capstoneapp.fastfood.ui.screens.PracticeHomeScreen
import com.example.capstoneapp.fastfood.ui.screens.itemMenu
import com.example.capstoneapp.fastfood.ui.screens.touchScreen

@Composable
fun AppNavigation(problemViewModel : ProblemViewModel) {
    val navController = rememberNavController()
    val viewModel: OrderViewModel = viewModel()
    val problem by problemViewModel.problem.observeAsState()
    val (showBorder, setShowBorder) = remember { mutableStateOf(false) } // 아이콘 테두리 상태 관리

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
            setShowBorder(false)
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                content = {
                    touchScreen(navController = navController, showBorder) // 아이콘 테두리 상태 전달
                },
                onAnswerCheckClicked = {
                    setShowBorder(true) // "정답확인" 클릭 시 아이콘 테두리 표시
                }
            )
        }

        composable(route="payment") {
            setShowBorder(false)
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                content = {
                    PaymentScreen(navController = navController, showBorder)//SelectSetDessertScreen()
                },
                onAnswerCheckClicked = {
                    setShowBorder(true) // "정답확인" 클릭 시 아이콘 테두리 표시
                }
            )
        }

        composable(route = "itemMenu"){
            setShowBorder(false)
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                content = {
                    itemMenu(navController = navController, viewModel, showBorder)//SelectSetDessertScreen()
                },
                onAnswerCheckClicked = {
                    setShowBorder(true) // "정답확인" 클릭 시 아이콘 테두리 표시
                }
            )
        }

        composable(route="setDessert") {
            //SelectSetDessertScreen()
        }

        composable(route="finalOrder") {
            setShowBorder(false)
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                content = {
                    OrderScreen(navController = navController, viewModel, showBorder)//SelectSetDessertScreen()
                },
                onAnswerCheckClicked = {
                    setShowBorder(true) // "정답확인" 클릭 시 아이콘 테두리 표시
                }
            )
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