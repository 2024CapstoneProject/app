package com.example.capstoneapp.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.capstoneapp.data.Repository.ProblemRepository
import com.example.capstoneapp.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.data.ViewModel.ProblemViewModel
import com.example.capstoneapp.data.ViewModel.ProblemViewModelFactory
import com.example.capstoneapp.ui.Screens.CafeHomeScreen
import com.example.capstoneapp.ui.Screens.CafeKioskScreen
import com.example.capstoneapp.ui.Screens.KioskCafeGuide0
import com.example.capstoneapp.ui.Screens.KioskCafePractice0
import com.example.capstoneapp.ui.Screens.KioskCafePractice5
import com.example.capstoneapp.ui.Screens.KioskCafePractice6

@Composable
fun SetUpNavGraph(navController:NavHostController) {

    //viewModelFactory : SharedViewModel 객체 생성 때 필요함
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel : ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val menuItemsViewModelFactory = MenuItemsViewModelFactory()
    val menuItemsViewModel : MenuItemsViewModel = viewModel(factory = menuItemsViewModelFactory)
    val problem by problemViewModel.problem.observeAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

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
            LaunchedEffect(navBackStackEntry) {
                if (navBackStackEntry?.destination?.route == "KioskCafePractice0") {
                    problemViewModel.createProblem()
                }
            }
            KioskCafePractice0(navController = navController,problem!!)
        }

        //카페 연습 메뉴 선택 화면
        composable(route="CafeKioskScreen"){
            CafeKioskScreen(navController = navController,menuItemsViewModel,problem!!)
        }

        //카페 연습 메뉴 확인 화면
        composable(route ="KioskCafePractice5"){
            KioskCafePractice5(navController = navController,menuItemsViewModel,problem!!)
        }

        //카페 연습 결제 선택 화면
        composable(route ="KioskCafePractice6"){
            KioskCafePractice6(navController = navController,menuItemsViewModel,problem!!)
        }
    }
}

