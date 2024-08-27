package com.example.capstoneapp.nav

import SubScreen
import android.annotation.SuppressLint
import android.content.Context
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
import com.example.capstoneapp.cafe.ui.Screens.CafeHomeScreen
import com.example.capstoneapp.cafe.ui.Screens.CafeKioskScreen
import com.example.capstoneapp.cafe.ui.Guide.Guide1
import com.example.capstoneapp.cafe.ui.Guide.Guide2
import com.example.capstoneapp.cafe.ui.Guide.Guide3
import com.example.capstoneapp.cafe.ui.Guide.Guide4
import com.example.capstoneapp.cafe.ui.Screens.GuideScreen
import com.example.capstoneapp.cafe.ui.Screens.KioskCafePractice0
import com.example.capstoneapp.cafe.ui.Screens.KioskCafePractice5
import com.example.capstoneapp.cafe.ui.Screens.KioskCafePractice6
import com.example.capstoneapp.cafe.ui.Screens.TouchScreenCafe
import com.example.capstoneapp.chatbot.api.RetrofitInstance
import com.example.capstoneapp.chatbot.ui.components.ChatGuide
import com.example.capstoneapp.chatbot.ui.components.ChatbotHomeScreen
import com.example.capstoneapp.chatbot.ui.components.ChatUI
import com.example.capstoneapp.fastfood.data.model.OrderViewModel
import com.example.capstoneapp.fastfood.ui.frame.NotificationScreen
import com.example.capstoneapp.fastfood.ui.guide.Fastfood_Guide1
import com.example.capstoneapp.fastfood.ui.guide.Fastfood_Guide2
import com.example.capstoneapp.fastfood.ui.screens.DessertChickenScreen
import com.example.capstoneapp.fastfood.ui.screens.DrinkCoffeeScreen
import com.example.capstoneapp.fastfood.ui.screens.FastFoodHomeScreen
import com.example.capstoneapp.fastfood.ui.screens.FastfoodGuideScreenPreview
import com.example.capstoneapp.fastfood.ui.screens.ItemMenu
import com.example.capstoneapp.fastfood.ui.screens.OrderScreen
import com.example.capstoneapp.fastfood.ui.screens.PaymentScreen
import com.example.capstoneapp.fastfood.ui.screens.PracticeHomeScreen
import com.example.capstoneapp.fastfood.ui.screens.RecommendScreen
import com.example.capstoneapp.fastfood.ui.screens.TouchScreen
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.kakatalk.ui.Screens.ChattingScreen
import com.example.capstoneapp.kakatalk.ui.Screens.KakaoGuide0
import com.example.capstoneapp.kakatalk.ui.Screens.KakaoPractice0
import com.example.capstoneapp.kakatalk.ui.Screens.Kakao_FriendChatList
import com.example.capstoneapp.kakatalk.ui.Screens.Kakao_List
import com.example.capstoneapp.kakatalk.ui.Screens.Kakao_Menu
import com.example.capstoneapp.kakatalk.ui.Screens.PhotoChatPractice
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory
import com.example.capstoneapp.phone.ui.screens.PhoneCallGuide
import com.example.capstoneapp.phone.ui.screens.PhoneCameraGuide
import com.example.capstoneapp.phone.ui.screens.PhoneContactGuide
import com.example.capstoneapp.phone.ui.screens.PhoneGuide0
import com.example.capstoneapp.phone.ui.screens.PhoneMessageGuide

import com.example.capstoneapp.taxi.ui.screens.TaxiHome
import com.example.capstoneapp.taxi.ui.screens.TaxiProblem
import com.example.capstoneapp.taxi.ui.screens.guide.Taxi_Guide
import com.example.capstoneapp.taxi.ui.screens.practice.TaxiInform
import com.example.capstoneapp.taxi.ui.screens.practice.TaxiPay
import com.example.capstoneapp.taxi.ui.screens.practice.TaxiRequest
import com.example.capstoneapp.taxi.ui.screens.practice.ChooseTaxiScreen
import com.example.capstoneapp.taxi.ui.screens.practice.SetGoalScreen
import com.example.capstoneapp.taxi.ui.screens.practice.TaxiConfirm
import com.example.capstoneapp.taxi.ui.screens.practice.TaxiMain


@SuppressLint("RememberReturnType")
@Composable
fun AppNavigation(problemViewModel: ProblemViewModel, context: Context) {
    val navController = rememberNavController()
    val viewModel: OrderViewModel = viewModel()
    val (showBorder, setShowBorder) = remember { mutableStateOf(false) } // 아이콘 테두리 상태 관리

    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val problem by problemViewModel.problem.observeAsState()
    val kakaotalkproblem by problemViewModel.kakaotalkproblem.observeAsState()

    val menuItemsViewModelFactory = MenuItemsViewModelFactory()
    val menuItemsViewModel: MenuItemsViewModel = viewModel(factory = menuItemsViewModelFactory)

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = "Guide0"
    ) {
        composable(route = "HamburgerHomeScreen"){
            FastFoodHomeScreen(navController = navController)
        }

        composable(route = "HamburgerGuideScreen"){
            FastfoodGuideScreenPreview(navController = navController)
        }

        composable(route = "Fastfood_Guide1") {
            Fastfood_Guide1(navController = navController, showBorder)
        }

        composable(route = "Fastfood_Guide2") {
            Fastfood_Guide2(navController = navController, problem!!, true)
        }

        composable(route = "HamburgerPracticeHomeScreen"){
            LaunchedEffect(navBackStackEntry) {
                if (navBackStackEntry?.destination?.route == "HamburgerPracticeHomeScreen") {
                    problemViewModel.createProblem()
                }
            }
            PracticeHomeScreen(navController = navController,problem!!)
        }

        composable(route = "touchToStart") {
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                screenType=1,
                content = { TouchScreen(navController = navController, showBorder) }
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }
        }

        composable(route = "payment") {
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                screenType=1,
                content = { PaymentScreen(navController = navController, showBorder) }
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }
        }

        composable(route = "itemMenu"){
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                screenType=1,
                content = { ItemMenu(navController = navController, viewModel, showBorder, problem!!) }
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }
        }

        composable(route = "setDessert") {
            //SelectSetDessertScreen()
        }

        composable(route = "finalOrder") {
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                screenType=1,
                content = { OrderScreen(navController = navController, viewModel, showBorder,problem!!) }
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }
        }

        composable(route = "recommend") {
            // DessertChickenScreen 컴포저블을 여기에 정의합니다.
            // 이 예제에서는 문제가 `problemViewModel.getProblemValue()`를 통해 전달된다고 가정합니다.
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                screenType = 1,
                content = { RecommendScreen(navController = navController, viewModel, showBorder, problem = problemViewModel.getProblemValue()!!) }
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }
        }

        // 여기서부터 새로운 경로 추가
        composable(route = "dessertChicken") {
            // DessertChickenScreen 컴포저블을 여기에 정의합니다.
            // 이 예제에서는 문제가 `problemViewModel.getProblemValue()`를 통해 전달된다고 가정합니다.
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                screenType = 1,
                content = { DessertChickenScreen(navController = navController, viewModel, showBorder, problem = problemViewModel.getProblemValue()!!) }
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }
        }

        composable(route = "drinkCoffee") {
            // DrinkCoffeeScreen 컴포저블을 여기에 정의합니다.
            // 이 예제에서는 문제가 `problemViewModel.getProblemValue()`를 통해 전달된다고 가정합니다.
            NotificationScreen(
                problem = problemViewModel.getProblemValue()!!,
                screenType = 1,
                content = { DrinkCoffeeScreen(navController = navController, viewModel, showBorder, problem = problemViewModel.getProblemValue()!!) }
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }
        }

        //메인(제일 처음)
        composable(route = "Guide0") {
            GuideScreen(navController = navController)
        }

        //카페 가이드, 연습 선택 화면
        composable(route = "CafeHomeScreen") {
            CafeHomeScreen(navController = navController)
        }

        //카페 가이드 첫번째 화면
        composable(route = "Guide1_touchscreen") {
            Guide1(navController = navController, showBorder)
        }

        composable(route = "Guide2_kiosk") {
            LaunchedEffect(navBackStackEntry) {
                if (navBackStackEntry?.destination?.route == "Guide2_kiosk") {
                    problemViewModel.createProblem() // 문제 생성
                }
            }
            Guide2(navController, menuItemsViewModel, problem!!, true)
        }

        composable(route = "Guide3_checkOrder") {
            Guide3(navController = navController, menuItemsViewModel, problem!!,true)
        }

        composable(route = "Guide4_payment") {
            Guide4(navController = navController, menuItemsViewModel, problem!!,true)
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

        composable(route = "touchToStartCafe") {
            NotificationScreen(
                problem = problem!!,
                screenType=2,
                content = { TouchScreenCafe(navController = navController, showBorder) }
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }
        }

        //카페 연습 메뉴 선택 화면
        composable(route = "CafeKioskScreen") {
            LaunchedEffect(navBackStackEntry) {
                if (navBackStackEntry?.destination?.route == "CafeKioskScreen") {
                    menuItemsViewModel.clearMenuItem()
                }
            }
            NotificationScreen(
                problem = problem!!,
                screenType = 2,
                content = { CafeKioskScreen(navController = navController, menuItemsViewModel, problem!!,showBorder) }
            ) { setShowBorder(!showBorder)
            }
        }

        //카페 연습 메뉴 확인 화면
        composable(route = "KioskCafePractice5") {
            NotificationScreen(
                problem = problem!!,
                screenType=2,
                content = { KioskCafePractice5(navController = navController, menuItemsViewModel, problem!!,showBorder) }
            ) { setShowBorder(!showBorder) }
            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }
        }

        //카페 연습 결제 선택 화면
        composable(route = "KioskCafePractice6") {
            NotificationScreen(
                problem = problem!!,
                screenType=2,
                content = { KioskCafePractice6(navController = navController, menuItemsViewModel, problem!!,showBorder) }
            ) { setShowBorder(!showBorder) }

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

            NotificationScreen(
                problem = problem!!,
                screenType=3,
                content = { Kakao_List(navController = navController, kakaotalkproblem!!,showBorder)}
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }
        }

        //카카오톡 연습 시작 화면
        composable(route = "KakaoPractice0") {
            LaunchedEffect(navBackStackEntry) {
                if (navBackStackEntry?.destination?.route == "KakaoPractice0") {
                    problemViewModel.createKakaotalkProblem()
                }
            }
            KakaoPractice0(navController = navController, kakaotalkproblem!!)
        }

        //카카오톡 연습 화면 - 친구목록
        composable(route = "Kakao_FriendList") {
            NotificationScreen(
                problem = problem!!,
                screenType=3,
                content = { Kakao_FriendChatList(navController = navController,showBorder,kakaotalkproblem!!) }
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }
        }

        //카카오톡 연습 화면 - 채팅방
        composable(route = "ChattingScreen") {
            NotificationScreen(
                problem = problem!!,
                screenType=3,
                content = { ChattingScreen(navController = navController,showBorder, kakaotalkproblem!!) }
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }

        }

        composable(route = "PhotoChatPractice") {
            NotificationScreen(
                problem = problem!!,
                screenType=3,
                content = { PhotoChatPractice(navController = navController, kakaotalkproblem!!) }
            ) { setShowBorder(!showBorder) }

            LaunchedEffect(navController.currentBackStackEntry) {
                setShowBorder(false)
            }

        }

        //택시 메인화면
        composable(route = "TaxiHome") {

            TaxiHome(navController = navController)
        }
        composable(route = "TaxiMain") {
            NotificationScreen(
                problem = problem!!,
                screenType=4,
                content = { TaxiMain(navController = navController,showBorder)}
            ) { setShowBorder(!showBorder) }
        }
        //택시 가이드 첫번째 화면
        composable(route = "Taxi_Guide") {
            Taxi_Guide(navController = navController)
        }
        composable(route = "TaxiInform") {
            NotificationScreen(
                problem = problem!!,
                screenType=4,
                content = { TaxiInform(navController = navController )}
            ) { setShowBorder(!showBorder) }

        }
        composable(route = "taxi_sub_screen") {
            NotificationScreen(
                problem = problem!!,
                screenType=4,
                content = {  SubScreen(navController = navController,showBorder)}
            ) { setShowBorder(!showBorder) }

        }

        composable(route = "TaxiPay") {
            TaxiPay(problem = problem!!, navController = navController )
        }
        composable(route = "TaxiRequest") {
            NotificationScreen(
                problem = problem!!,
                screenType=4,
                content = {   TaxiRequest( problem = problem!!, navController = navController )}
            ) { setShowBorder(!showBorder) }
        }

        composable(route = "taxi_set_goal") {
            NotificationScreen(
                problem = problem!!,
                screenType=4,
                content = { SetGoalScreen(navController = navController)}
            ) { setShowBorder(!showBorder) }
        }
        composable(route = "TaxiChoose") {
            NotificationScreen(
                problem = problem!!,
                screenType=4,
                content = {  ChooseTaxiScreen(navController = navController)}
            ) { setShowBorder(!showBorder) }
        }
        composable(route = "TaxiChooseConfirm") {
            NotificationScreen(
                problem = problem!!,
                screenType=4,
                content = {  TaxiConfirm(navController = navController)}
            ) { setShowBorder(!showBorder) }
        }
        composable(route = "TaxiProblem") {
            TaxiProblem(navController = navController)
        }


        composable(route = "chatbotHome") {
            ChatbotHomeScreen(navController = navController)
        }

        val chatService = RetrofitInstance.api
        composable("chatUI") {
            ChatUI(navController, chatService)
        }

        //챗봇 가이드 첫번째 화면
        composable(route = "Chat_Guide") {
            ChatGuide(navController = navController)
        }

        composable(route = "Phone_Call_Guide"){
            PhoneCallGuide(navController = navController)
        }

        composable(route = "Phone_Guide"){
            PhoneGuide0(navController = navController)
        }

        composable(route = "Phone_Contact_Guide"){
            PhoneContactGuide(navController = navController)
        }

        composable(route = "Phone_Message_Guide"){
            PhoneMessageGuide(navController = navController)
        }
        composable(route = "Phone_Camera_Guide"){
            PhoneCameraGuide(navController = navController)
        }
    }
}

