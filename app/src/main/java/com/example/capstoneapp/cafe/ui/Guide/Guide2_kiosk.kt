package com.example.capstoneapp.cafe.ui.Guide

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.ui.Frame.GuidePopup
import com.example.capstoneapp.cafe.ui.Frame.VerticalAlignment
import com.example.capstoneapp.chatbot.utils.TtsPlaybackHandler
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.texttospeech.v1.TextToSpeechClient
import com.google.cloud.texttospeech.v1.TextToSpeechSettings
import java.io.InputStream

@Composable
fun Guide2(
    navController: NavController,
    menuItemsViewModel: MenuItemsViewModel,
    problem: Problem,
    showBorder: Boolean
) {
    var showPopup by remember { mutableStateOf(true) }
    var navigateToHome by remember { mutableStateOf(false) }
    var currentStep by remember { mutableStateOf(1) } // 현재 단계 관리 변수

    val messageStep1 = "상단의 홈 버튼을 누르면 광고 화면으로 돌아가게 됩니다."
    val messageStep2 = "주문하는 법을 안내드립니다! 예시로 ${problem.c_menu}를 주문하는 법입니다."
    val messageStep3 = "먼저 화면에서 ${problem.c_menu}를 찾아주세요. ${problem.c_menu}는 ${
        getMenuCategory(problem.c_menu)
    }에 있습니다!"
    val messageStep4 = "이 창에서는 주문 내역을 확인할 수 있습니다. 제품과 수량을 확인해 주세요!"
    val messageStep5 = "x를 누르면 제품을 삭제합니다. -와 +로 수량을 조절할 수 있습니다."
    val messageStep6 = "주문한 제품과 수량이 맞다면 결제 버튼을 눌러주세요."

    //TTS를 위해 추가해야 하는 부분-----------------------------------
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // TTS 클라이언트 및 핸들러 초기화
    val ttsClient = remember {
        val credentialsStream: InputStream =
            context.resources.openRawResource(R.raw.service_account_key) // 서비스 계정 키 파일
        val credentials = GoogleCredentials.fromStream(credentialsStream)
        val settings = TextToSpeechSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .build()
        TextToSpeechClient.create(settings)
    }

    val ttsPlaybackHandler = remember {
        TtsPlaybackHandler(context, ttsClient, coroutineScope)
    }

    // 컴포저블이 소멸될 때 TTS 클라이언트를 정리하는 코드 추가
    DisposableEffect(Unit) {
        onDispose {
            ttsPlaybackHandler.shutdown()
        }
    }

     //   ,ttsPlaybackHandler = dummyTtsPlaybackHandler 를 guidePopup에 추가


    //----------------------------------------------------------


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
        problem = problem
    )

    if (showPopup) {
        GuidePopup(
            isPopupVisible = showPopup,
            onDismiss = { // 다음 단계로 이동
                currentStep += 1
                if (currentStep > 6) {
                    showPopup = false
                    navController.navigate("Guide3_checkOrder")
                }
            },
            title = when (currentStep) {
                1 -> "화면 기능 안내"
                2 -> "주문 안내"
                3 -> "주문 안내"
                4 -> "주문 내역 확인"
                5 -> "주문 내역 확인"
                6 -> "결제 안내"
                else -> ""
            },
            message = when (currentStep) {
                1 -> messageStep1
                2 -> messageStep2
                3 -> messageStep3
                4 -> messageStep4
                5 -> messageStep5
                else -> messageStep6
            },
            highlights = when (currentStep) {
                1 -> listOf("홈 버튼", "수량 조절")
                2 -> listOf(problem.c_menu)
                3 -> listOf(problem.c_menu, getMenuCategory(problem.c_menu))
                4 -> listOf("주문 내역", "제품", "수량")
                5 -> listOf("x", "-", "+")
                else -> listOf("결제 버튼")
            },
            verticalAlignment = when (currentStep) {
                3,4,5 -> VerticalAlignment.Bottom
                else -> VerticalAlignment.Center
            },
            ttsPlaybackHandler = ttsPlaybackHandler
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
