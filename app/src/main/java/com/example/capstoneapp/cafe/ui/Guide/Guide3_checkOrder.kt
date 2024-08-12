package com.example.capstoneapp.cafe.ui.Guide

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.capstoneapp.chatbot.utils.TtsPlaybackHandler
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.texttospeech.v1.TextToSpeechClient
import com.google.cloud.texttospeech.v1.TextToSpeechSettings
import java.io.InputStream

@Composable
fun Guide3(
    navController: NavController,
    menuItemsViewModel: MenuItemsViewModel,
    problem: Problem,
    showBorder: Boolean
) {
    var showPopup by remember { mutableStateOf(true) }
    var navigateToHome by remember { mutableStateOf(false) }
    var currentStep by remember { mutableStateOf(1) } // 현재 단계 관리 변수

    val messageStep1 = "이 창에서는 주문내역을 한 번에 확인할 수 있습니다!"
    val messageStep2 = "최종으로 주문하는 수량과 가격을 확인해주세요!"
    val messageStep3 = "먹고가기와 포장하기를 선택하여 매장 식사 여부를 결정합니다!"

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

    Column(modifier = Modifier.fillMaxHeight()) {
        CafeMenuBarFormat {
            MenuText5()
        }
        Screen5(navController, menuItemsViewModel, showBorder, problem, currentStep)
    }

    GuidePopup(
        isPopupVisible = showPopup,
        onDismiss = {
            currentStep += 1
            if (currentStep > 3) {
                showPopup = false
                navController.navigate("KioskCafePractice6")
            }
        },
        title = when (currentStep) {
            1 -> "총 주문 내역 확인"
            2 -> "총 수량과 가격 확인"
            3 -> "매장 식사 여부"
            else -> ""
        },
        message = when (currentStep) {
            1 -> messageStep1
            2 -> messageStep2
            3 -> messageStep3
            else -> ""
        },
        highlights = when (currentStep) {
            1 -> listOf("주문내역")
            2 -> listOf("수량", "가격")
            3 -> listOf("먹고가기", "포장하기")
            else -> listOf("")
        },
        verticalAlignment = VerticalAlignment.Center,
        ttsPlaybackHandler = ttsPlaybackHandler
    )
}

@Composable
fun Screen5(
    navController: NavController,
    viewModel: MenuItemsViewModel,
    showBorder: Boolean,
    problem: Problem,
    currentStep: Int
) {
    val orderItems by viewModel.orderItems.observeAsState()
    val totalAmount by viewModel.totalOrderAmount.observeAsState()
    Surface(
        color = Color(0xFFCACACA),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(276.dp)
            ) {

                LazyColumn(
                    modifier = Modifier
                        .height(280.dp)
                        .fillMaxWidth()
                        .then(if (showBorder && currentStep == 1) Modifier.border(BorderWidth, BorderColor, BorderShape) else Modifier), // 조건에 따라 보더 적용
                ) {
                    orderItems?.size?.let {
                        items(it) {
                            val item = orderItems!![it]
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                            ) {
                                Text(
                                    text = item.first.name,
                                    modifier = Modifier
                                        .padding(start = 30.dp, top = 10.dp)
                                        .align(Alignment.CenterVertically),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                                Text(
                                    text = item.second.toString(),
                                    modifier = Modifier
                                        .padding(end = 30.dp, top = 10.dp)
                                        .align(Alignment.CenterVertically),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                    }

                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp, start = 28.dp, end = 28.dp)
                    .then(if (showBorder && currentStep == 2) Modifier.border(BorderWidth, BorderColor, BorderShape) else Modifier), // 조건에 따라 보더 적용
                ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "총 수량",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,

                        )
                    Text(
                        text = 1.toString(),//orderItems!!.size.toString()
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Red,

                        )
                    Text(
                        text = "개",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "금액",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                    Text(
                        text = totalAmount.toString(),
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Red,
                    )
                    Text(
                        text = "원",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .then(if (showBorder && currentStep == 3) Modifier.border(BorderWidth, BorderColor, BorderShape) else Modifier), // 조건에 따라 보더 적용
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                    },
                    modifier = Modifier
                        .size(150.dp, 80.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFCA0D)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "먹고가기",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                    },
                    modifier = Modifier
                        .size(150.dp, 80.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFB2929)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "포장하기",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Composable
fun MenuText5() {
    Text(
        text = "주문 세부내역 확인",
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(left = 30.dp),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
    )
}

@Preview
@Composable
fun Guide3PreView() {
    val navController = rememberNavController()
    val problemViewModelFactory =
        com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val menuItemsViewModelFactory = MenuItemsViewModelFactory()
    val menuItemsViewModel: MenuItemsViewModel = viewModel(factory = menuItemsViewModelFactory)

    Guide3(
        navController,
        menuItemsViewModel,
        problemViewModel.getProblemValue()!!,
        true
    )
}