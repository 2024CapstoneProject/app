package com.example.capstoneapp.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

import com.example.capstoneapp.cafe.ui.Screens.CafeHomeScreen
import com.example.capstoneapp.cafe.ui.Screens.CafeKioskScreen
import com.example.capstoneapp.cafe.ui.Screens.Guide0
import com.example.capstoneapp.cafe.ui.Screens.KioskCafeGuide0
import com.example.capstoneapp.cafe.ui.Screens.KioskCafePractice0
import com.example.capstoneapp.cafe.ui.Screens.KioskCafePractice5
import com.example.capstoneapp.cafe.ui.Screens.KioskCafePractice6
import com.example.capstoneapp.fastfood.data.model.OrderViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel

import com.example.capstoneapp.fastfood.ui.frame.NotificationScreen
import com.example.capstoneapp.fastfood.ui.screens.CafeGuideScreenPreview
import com.example.capstoneapp.fastfood.ui.screens.GreetingPreview
import com.example.capstoneapp.fastfood.ui.screens.OrderScreen
import com.example.capstoneapp.fastfood.ui.screens.PaymentScreen
import com.example.capstoneapp.fastfood.ui.screens.PracticeHomeScreen
import com.example.capstoneapp.fastfood.ui.screens.itemMenu
import com.example.capstoneapp.fastfood.ui.screens.touchScreen
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.kakatalk.ui.Screens.ChattingScreen
import com.example.capstoneapp.kakatalk.ui.Screens.GuideScreen
import com.example.capstoneapp.kakatalk.ui.Screens.KakaoGuide0
import com.example.capstoneapp.kakatalk.ui.Screens.KakaoPractice0
import com.example.capstoneapp.kakatalk.ui.Screens.Kakao_FriendChatList
import com.example.capstoneapp.kakatalk.ui.Screens.Kakao_List
import com.example.capstoneapp.kakatalk.ui.Screens.Kakao_Menu
import com.example.capstoneapp.kakatalk.ui.Screens.PhotoChatPractice
import com.example.capstoneapp.kakatalk.ui.Screens.ProtectorHome
import com.example.capstoneapp.ui.Screens.Taxi_Guide

@Composable
fun AppNavigation(problemViewModel : ProblemViewModel) {
    val navController = rememberNavController()
    val viewModel: OrderViewModel = viewModel()
    val (showBorder, setShowBorder) = remember { mutableStateOf(false) } // 아이콘 테두리 상태 관리

    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: com.example.capstoneapp.nav.viewmodel.ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val problem by problemViewModel.problem.observeAsState()

    val menuItemsViewModelFactory = MenuItemsViewModelFactory()
    val menuItemsViewModel: MenuItemsViewModel = viewModel(factory = menuItemsViewModelFactory)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    NavHost(
        navController = navController,
        startDestination = "Guide0"
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
                }
            ) {
                setShowBorder(true) // "정답확인" 클릭 시 아이콘 테두리 표시
            }
        }

        composable(route="payment") {
            setShowBorder(false)
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                content = {
                    PaymentScreen(navController = navController, showBorder)//SelectSetDessertScreen()
                }
            ) {
                setShowBorder(true) // "정답확인" 클릭 시 아이콘 테두리 표시
            }
        }

        composable(route = "itemMenu"){
            setShowBorder(false)
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                content = {
                    itemMenu(navController = navController, viewModel, showBorder)//SelectSetDessertScreen()
                }
            ) {
                setShowBorder(true) // "정답확인" 클릭 시 아이콘 테두리 표시
            }
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
                }
            ) {
                setShowBorder(true) // "정답확인" 클릭 시 아이콘 테두리 표시
            }
            //SelectSetDessertScreen()
        }

        // Define other routes...
        //메인(제일 처음)
        composable(route = "Guide0") {
            GuideScreen(navController = navController)
        }

        //카페 가이드, 연습 선택 화면
        composable(route = "CafeHomeScreen") {
            CafeHomeScreen(navController = navController)
        }

        //카페 가이드 첫번째 화면
        composable(route = "KioskCafeGuide0") {
            KioskCafeGuide0(navController = navController)
        }

        //카페 연습 첫번째 화면
        composable(route = "KioskCafePractice0") {
            LaunchedEffect(navBackStackEntry) {
                if (navBackStackEntry?.destination?.route == "KioskCafePractice0") {
                    problemViewModel.createProblem()
                    menuItemsViewModel.clearMenuItem()
                }
            }
            KioskCafePractice0(navController = navController, problem!!)
        }

        //카페 연습 메뉴 선택 화면
        composable(route = "CafeKioskScreen") {
            LaunchedEffect(navBackStackEntry) {
                if (navBackStackEntry?.destination?.route == "CafeKioskScreen") {
                    menuItemsViewModel.clearMenuItem()
                }
            }
            CafeKioskScreen(navController = navController, menuItemsViewModel, problem!!)
        }

        //카페 연습 메뉴 확인 화면
        composable(route = "KioskCafePractice5") {
            KioskCafePractice5(navController = navController, menuItemsViewModel, problem!!)
        }

        //카페 연습 결제 선택 화면
        composable(route = "KioskCafePractice6") {
            KioskCafePractice6(navController = navController, menuItemsViewModel, problem!!)
        }

        //카톡 가이드 첫번째 화면
        composable(route = "Kakao_Menu") {
            Kakao_Menu(navController = navController)
        }

        //카카오톡 가이드 첫번째 화면
        composable(route = "KakaoGuide0") {
            KakaoGuide0(navController = navController)
        }
        //카카오톡 채팅 화면
        composable(route = "Kakao_List") {
            Kakao_List(navController = navController, problem!!)
        }

        //카카오톡 연습 시작 화면
        composable(route = "KakaoPractice0") {

            KakaoPractice0(navController = navController, problem!!)
        }

        //카카오톡 연습 화면 - 친구목록
        composable(route = "Kakao_FriendList") {
            Kakao_FriendChatList(navController = navController, problem!!)
        }

        //카카오톡 연습 화면 - 채팅방
        composable(route = "ChattingScreen") {

            ChattingScreen(navController = navController, problem!!)
        }

        composable(route = "PhotoChatPractice") {
            PhotoChatPractice(navController = navController, problem!!)
        }

        //위치추적 첫번째 화면
        composable(route = "ProtectorHome") {
            ProtectorHome(navController = navController)
        }

        //택시 가이드 첫번째 화면
        composable(route = "Taxi_Guide") {
            Taxi_Guide(navController = navController)
        }
    }
}

