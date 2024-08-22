package com.example.capstoneapp.cafe.ui.Guide

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.ui.Components.CafeMenuBarFormat
import com.example.capstoneapp.cafe.ui.Frame.GuidePopup
import com.example.capstoneapp.cafe.ui.Frame.VerticalAlignment
import com.example.capstoneapp.cafe.ui.Screens.Dialog10
import com.example.capstoneapp.cafe.ui.Screens.Dialog11
import com.example.capstoneapp.cafe.ui.Screens.Dialog7
import com.example.capstoneapp.cafe.ui.Screens.NumberDialog
import com.example.capstoneapp.cafe.ui.Screens.PointDialog
import com.example.capstoneapp.cafe.ui.theme.firaSansFamily
import com.example.capstoneapp.chatbot.utils.TtsPlaybackHandler
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.texttospeech.v1.TextToSpeechClient
import com.google.cloud.texttospeech.v1.TextToSpeechSettings
import java.io.InputStream


@Composable
fun Guide4(
    navController: NavController,
    menuItemsViewModel: MenuItemsViewModel,
    problem: Problem,
    showBorder: Boolean
) {
    var showPopup by remember { mutableStateOf(true) }
    var navigateToHome by remember { mutableStateOf(false) }
    var currentStep by remember { mutableStateOf(1) } // 현재 단계 관리 변수

    var popup7 by remember { mutableStateOf(false) }
    var popup10 by remember { mutableStateOf(false) }
    var popup11 by remember { mutableStateOf(false) }
    var pointPopup by remember { mutableStateOf(false) }
    var numberPopup by remember { mutableStateOf(false) }
    var closeDialog by remember { mutableStateOf(false) }


    val messageStep1 = "결제수단을 선택할 수 있습니다. 카드결제 또는 쿠폰을 사용할 수 있습니다!"
    val messageStep2 = "만약 현금으로 결제하신다면 카운터에서 주문을 다시 해주세요."
    val messageStep3 = "이번 예제는 ${problem.c_pay}로 진행하겠습니다. 이제는 버튼을 직접 눌러 확인해주세요!"

    // 메시지 설정
    var message by remember { mutableStateOf("") }

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

    // Handle the sequence of popups based on user actions and currentStep
    LaunchedEffect(currentStep) {
        when (currentStep) {
            1 -> message = messageStep1
            2 -> message = messageStep2
            3 -> message = messageStep3
            4 -> {
                // currentStep이 4일 때 팝업 표시
                when (problem.c_pay) {
                    "카드 결제" -> pointPopup = true
                    "쿠폰 사용" -> popup7 = true
                }
            }
            else -> message = ""
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CafeMenuBarFormat {
            MenuText6(navController)
        }
        Screen6(navController, menuItemsViewModel, showBorder, problem, currentStep) {}
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (popup7) {
            Dialog7(
                onDismiss = {
                    popup7 = false
                    popup10 = true
                },
                onConfirm = {
                    popup7 = false
                    popup10 = true
                },
            )
        }
        if (pointPopup) {
            PointDialog(
                onDismiss = {
                    pointPopup = false
                    numberPopup = true
                },
                isYesPoint = {
                    pointPopup = false
                    numberPopup = true
                },
                isNoPoint = {
                    pointPopup = false
                    popup11 = true
                },
            )
        }
        if (numberPopup) {
            NumberDialog(
                onDismiss = {
                    numberPopup = false
                    popup11 = true
                },
                onConfirm = {
                    numberPopup = false
                    popup11 = true
                },
            )
        }
        if (popup11) {
            Dialog11(
                onDismiss = {
                    popup11 = false
                    popup10 = true
                },
                onConfirm = {
                    popup11 = false
                    popup10 = true
                },
            )
        }
        if (popup10) {
            Dialog10(
                onDismiss = {
                    popup10 = false
                },
                onConfirm = {
                    closeDialog = true
                },
            )
        }
        if (closeDialog) {
            ClosePopup(
                onDismiss = {
                    closeDialog = false
                    navController.navigate("CafeHomeScreen") {
                        popUpTo("CafeHomeScreen") { inclusive = true }
                    }
                    menuItemsViewModel.clearMenuItem()
                }
            )
        }
    }

    if (showPopup) {
        GuidePopup(
            isPopupVisible = showPopup,
            onDismiss = {
                currentStep += 1
                if (currentStep > 3) { // 모든 단계가 끝난 후
                    showPopup = false
                }
            },
            title = "결제수단 선택",
            message = message,
            highlights = when (currentStep) {
                1 -> listOf("결제수단", "카드결제", "쿠폰을 사용")
                2 -> listOf("현금", "카운터")
                3 -> listOf(problem.c_pay)
                else -> listOf("")
            },
            verticalAlignment = when (currentStep) {
                2 -> VerticalAlignment.Center
                else -> VerticalAlignment.Bottom // 하단에 위치하도록 수정
            },
            ttsPlaybackHandler = ttsPlaybackHandler
        )
    }
}

@Composable
fun Screen6(
    navController: NavController,
    viewModel: MenuItemsViewModel,
    showBorder: Boolean,
    problem: Problem,
    currentStep: Int,
    onClick: () -> Unit
) {
    val totalAmount by viewModel.totalOrderAmount.observeAsState()
    Surface(
        color = Color.White,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            PayButton(navController, onClick, showBorder, problem, currentStep)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "금액",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = firaSansFamily
                )
                Text(
                    text = totalAmount.toString() + "원",
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Red,
                    fontFamily = firaSansFamily
                )
            }
        }
    }
}

@Composable
fun PayButton(
    navController: NavController,
    onClick: () -> Unit,
    showBorder: Boolean,
    problem: Problem,
    currentStep: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { },
            modifier = Modifier
                .size(300.dp, 180.dp)
                .then(
                    if (showBorder && (currentStep == 1 || (currentStep == 3 && problem.c_pay == "카드 결제"))) Modifier.border(
                        BorderWidth, BorderColor, BorderShape
                    ) else Modifier
                ),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFCA0D)),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "카드결제",
                fontSize = 32.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = firaSansFamily
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .size(300.dp, 180.dp)
                .then(
                    if (showBorder && (currentStep == 1 || (currentStep == 3 && problem.c_pay == "쿠폰 사용"))) Modifier.border(
                        BorderWidth, BorderColor, BorderShape
                    ) else Modifier
                ),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "쿠폰 사용",
                fontSize = 32.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = firaSansFamily
            )
        }
    }
}

@Composable
fun MenuText6(navController: NavController) {
    Column(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .wrapContentSize()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "setting",
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
            )
        }
        Text(
            text = "결제수단 선택",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = firaSansFamily,
            color = Color.Black,
        )

    }
}

@Preview
@Composable
fun Guide4PreView() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val menuItemsViewModelFactory = MenuItemsViewModelFactory()
    val menuItemsViewModel: MenuItemsViewModel = viewModel(factory = menuItemsViewModelFactory)

    Guide4(
        navController,
        menuItemsViewModel,
        problemViewModel.getProblemValue()!!,
        true
    )
}