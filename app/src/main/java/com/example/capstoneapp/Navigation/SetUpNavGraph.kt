package com.example.capstoneapp.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.capstoneapp.Repository.ProblemRepository
import com.example.capstoneapp.Screens.CafeKioskScreen
import com.example.capstoneapp.component.CafeHomeScreen
import com.example.capstoneapp.component.KioskCafeGuide0
import com.example.capstoneapp.component.KioskCafePractice0
import com.example.capstoneapp.component.KioskCafePractice5
import com.example.capstoneapp.component.KioskCafePractice6
import com.example.capstoneapp.ViewModel.SharedViewModel
import com.example.capstoneapp.ViewModel.SharedViewModelFactory

@Composable
fun SetUpNavGraph(navController:NavHostController) {

    //viewModelFactory : SharedViewModel 객체 생성 때 필요함
    val viewModelFactory = SharedViewModelFactory(ProblemRepository)
    val viewModel: SharedViewModel = viewModel(factory = viewModelFactory)

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
                    viewModel.createRandomProblem()
                }
            }
            //KioskCafePractice0(navController = navController, viewModel = viewModel)
            KioskCafePractice0(navController = navController)
        }

        //카페 연습 메뉴 선택 화면
        composable(route="CafeKioskScreen"){
            //CafeKioskScreen(navController = navController,viewModel = viewModel)
            CafeKioskScreen(navController = navController)
        }

        //카페 연습 메뉴 확인 화면
        composable(route ="KioskCafePractice5"){
            //KioskCafePractice5(navController = navController,viewModel = viewModel)
            KioskCafePractice5(navController = navController)
        }

        //카페 연습 결제 선택 화면
        composable(route ="KioskCafePractice6"){
            //KioskCafePractice6(navController = navController,viewModel = viewModel)
            KioskCafePractice6(navController = navController)
        }
    }
}


