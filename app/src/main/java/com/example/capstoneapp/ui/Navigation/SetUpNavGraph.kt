package com.example.capstoneapp.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.capstoneapp.data.Repository.ProblemRepository
import com.example.capstoneapp.ui.Screens.CafeKioskScreen
import com.example.capstoneapp.ui.Screens.CafeHomeScreen
import com.example.capstoneapp.ui.Screens.KioskCafeGuide0
import com.example.capstoneapp.ui.Screens.KioskCafePractice0
import com.example.capstoneapp.ui.Screens.KioskCafePractice5
import com.example.capstoneapp.ui.Screens.KioskCafePractice6
import com.example.capstoneapp.data.ViewModel.SharedViewModel
import com.example.capstoneapp.data.ViewModel.SharedViewModelFactory
import com.example.capstoneapp.ui.Screens.Guide0
import com.example.capstoneapp.ui.Screens.Kakao_Menu
import com.example.capstoneapp.ui.Screens.ProtectorHome

@Composable
fun SetUpNavGraph(navController:NavHostController) {

    //viewModelFactory : SharedViewModel 객체 생성 때 필요함
    val viewModelFactory = SharedViewModelFactory(ProblemRepository)
    val viewModel: SharedViewModel = viewModel(factory = viewModelFactory)

    NavHost(
        navController = navController,
        startDestination = "Guide0"
    ) {
        //메인(제일 처음)
        composable(route="Guide0"){
            Guide0(navController = navController)
        }
        //카톡 가이드 첫번째 화면
        composable(route="Kakao_Menu"){
            Kakao_Menu(navController = navController)
        }
        //위치추적 가이드 첫번째 화면
        composable(route="ProtectorHome"){
            ProtectorHome(navController = navController)
        }
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


