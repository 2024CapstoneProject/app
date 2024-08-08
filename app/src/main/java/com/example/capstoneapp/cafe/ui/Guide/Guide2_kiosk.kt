package com.example.capstoneapp.cafe.ui.Guide

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.cafe.ui.Frame.GuidePopup
import com.example.capstoneapp.cafe.ui.Frame.VerticalAlignment
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository

@Composable
fun Guide2(
    navController: NavController,
    menuItemsViewModel: MenuItemsViewModel,
    problem: Problem,
    showBorder: Boolean
) {
    var showPopup by remember { mutableStateOf(true) }
    var randomProblem by remember { mutableStateOf(problem) }
    var navigateToHome by remember { mutableStateOf(false) }
    var currentStep by remember { mutableStateOf(1) } // 현재 단계 관리 변수

    val messageStep1 = "상단의 홈 버튼을 누르면 광고 화면으로 돌아가게 됩니다."
    val messageStep2 = "주문하는 법을 안내드립니다! 예시로 ${randomProblem.c_menu}를 주문하는 법입니다."
    val messageStep3 = "먼저 화면에서 ${randomProblem.c_menu}를 찾아주세요. ${randomProblem.c_menu}는 ${
        getMenuCategory(randomProblem.c_menu)
    }에 있습니다!"
    val messageStep4 = "이 창에서는 주문 내역을 확인할 수 있습니다. 제품과 수량을 확인해 주세요!"
    val messageStep5 = "주문한 제품과 수량이 맞다면 결제 버튼을 눌러주세요."

    // BackHandler에서 팝업을 닫고 상태를 초기화합니다.
    BackHandler {
        showPopup = false
        navigateToHome = true // 네비게이션 플래그 설정
    }

    // 상태 변경 시 네비게이션 처리
    LaunchedEffect(navigateToHome) {
        if (navigateToHome) {
            // 네비게이션 수행
            navController.navigate("CafeHomeScreen") {
                popUpTo("CafeHomeScreen") { inclusive = true }
            }
            // 주문 상태 초기화
            menuItemsViewModel.clearMenuItem()
        }
    }

    GuideCafeMenuScreen(
        navController = navController,
        menuItemsViewModel = menuItemsViewModel,
        showBorder = showBorder,
        currentStep = currentStep, // currentStep 전달
        problem = randomProblem
    )

    if (showPopup) {
        GuidePopup(
            isPopupVisible = showPopup,
            onDismiss = { // 다음 단계로 이동
                currentStep += 1
                if (currentStep > 5) {
                    showPopup = false
                }
            },
            title = when (currentStep) {
                1 -> "화면 기능 안내"
                2 -> "주문 안내"
                3 -> "주문 안내"
                4 -> "주문 내역 확인"
                else -> "결제 안내"
            },
            message = when (currentStep) {
                1 -> messageStep1
                2 -> messageStep2
                3 -> messageStep3
                4 -> messageStep4
                else -> messageStep5
            },
            highlights = when (currentStep) {
                1 -> listOf("홈 버튼", "수량 조절")
                2 -> listOf(randomProblem.c_menu)
                3 -> listOf(randomProblem.c_menu, getMenuCategory(randomProblem.c_menu))
                4 -> listOf("주문 내역", "제품", "수량")
                else -> listOf("결제 버튼")
            },
            verticalAlignment = when (currentStep) {
                1,2,5 -> VerticalAlignment.Center
                4 -> VerticalAlignment.Top
                else -> VerticalAlignment.Bottom
            }
        )
    }
}


fun getMenuCategory(menuName: String): String {
    return when (menuName) {
        "HOT아메리카노", "HOT카페라떼" -> "커피(HOT)"
        "ICE아메리카노", "ICE바닐라라떼" -> "커피(ICE)"
        "녹차", "캐모마일" -> "티(TEA)"
        else -> "메뉴"
    }
}

@Preview
@Composable
fun Guide2Preview() {
    val navController = rememberNavController()
    val menuItemsViewModelFactory = MenuItemsViewModelFactory()
    val menuItemsViewModel: MenuItemsViewModel = viewModel(factory = menuItemsViewModelFactory)

    // 예시로 랜덤 문제를 설정하여 Guide2를 미리보기
    Guide2(navController, menuItemsViewModel, ProblemRepository.createProblem(), true)
}
