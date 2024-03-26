package com.example.capstoneapp.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.capstoneapp.data.Repository.ProblemRepository
import com.example.capstoneapp.data.ViewModel.ProblemViewModel
import com.example.capstoneapp.data.ViewModel.ProblemViewModelFactory
import com.example.capstoneapp.ui.Screens.Guide0
import com.example.capstoneapp.ui.Screens.KakaoGuide0
import com.example.capstoneapp.ui.Screens.KakaoHomeScreen
import com.example.capstoneapp.ui.Screens.KakaoPractice0
import com.example.capstoneapp.ui.Screens.KakaoPractice1

@Composable
fun SetUpNavGraph(navController: NavHostController) {

    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val problem by problemViewModel.problem.observeAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController, startDestination = "Guide0"
    ) {
        //메인(제일 처음)
        composable(route = "Guide0") {
            Guide0(navController = navController)
        }

        //카카오톡 가이드, 연습 선택 화면
        composable(route = "KakaoHomeScreen") {
            KakaoHomeScreen(navController = navController)
        }

        //카카오톡 가이드 첫번째 화면
        composable(route = "KakaoGuide0") {
            KakaoGuide0(navController = navController)
        }

        //카카오톡 연습 첫번째 화면
        composable(route = "KakaoPractice0") {

            KakaoPractice0(navController = navController, problem!!)
        }

        //카카오톡 연습 두번째 화면
        composable(route = "KakaoPractice1") {

            KakaoPractice1(navController = navController, problem!!)
        }
    }
}

