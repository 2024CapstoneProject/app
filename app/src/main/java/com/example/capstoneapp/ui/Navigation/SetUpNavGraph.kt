package com.example.capstoneapp.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.capstoneapp.data.Repository.ProblemRepository
import com.example.capstoneapp.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.ui.Screens.CafeKioskScreen
import com.example.capstoneapp.ui.Screens.CafeHomeScreen
import com.example.capstoneapp.ui.Screens.KioskCafeGuide0
import com.example.capstoneapp.ui.Screens.KioskCafePractice0
import com.example.capstoneapp.ui.Screens.KioskCafePractice5
import com.example.capstoneapp.ui.Screens.KioskCafePractice6
import com.example.capstoneapp.data.ViewModel.SharedViewModel
import com.example.capstoneapp.data.ViewModel.SharedViewModelFactory

@Composable
fun SetUpNavGraph(navController:NavHostController) {

    //viewModelFactory : SharedViewModel 객체 생성 때 필요함
    val problemViewModelFactory = SharedViewModelFactory(ProblemRepository)
    val problemViewModel : SharedViewModel = viewModel(factory = problemViewModelFactory)
    val menuItemsViewModelFactory = MenuItemsViewModelFactory()
    val menuItemsViewModel : MenuItemsViewModel = viewModel(factory = menuItemsViewModelFactory)

    NavHost(
        navController = navController,
        startDestination = "CafeHomeScreen"
    ) {
        //카페 가이드, 연습 선택 화면
        composable(route="CafeHomeScreen"){
            CafeHomeScreen(navController = navController)
        }

        //카페 가이드 첫번째 화면
        composable(route="KioskCafeGuide0"){
            KioskCafeGuide0(navController = navController)
        }

        //카페 연습 첫번째 화면
        composable(route="KioskCafePractice0"){
            val currentBackStackEntry = navController.currentBackStackEntryAsState().value
            val isFromKioskCafePractice0 = currentBackStackEntry?.destination?.route == "KioskCafePractice0"

            LaunchedEffect(isFromKioskCafePractice0) {
                if (isFromKioskCafePractice0) {
                    problemViewModel.createRandomProblem()
                }
            }
            //KioskCafePractice0(navController = navController, viewModel = viewModel)
            KioskCafePractice0(navController = navController)
        }

        //카페 연습 메뉴 선택 화면
        composable(route="CafeKioskScreen"){
            //CafeKioskScreen(navController = navController,viewModel = problemViewModel)
            CafeKioskScreen(navController = navController,viewModel = menuItemsViewModel)
        }

        //카페 연습 메뉴 확인 화면
        composable(route ="KioskCafePractice5"){
            //KioskCafePractice5(navController = navController,viewModel = problemViewModel)
            KioskCafePractice5(navController = navController,menuItemsViewModel)
        }

        //카페 연습 결제 선택 화면
        composable(route ="KioskCafePractice6"){
            //KioskCafePractice6(navController = navController,viewModel = problemViewModel)
            KioskCafePractice6(navController = navController,menuItemsViewModel)
        }
    }
}

