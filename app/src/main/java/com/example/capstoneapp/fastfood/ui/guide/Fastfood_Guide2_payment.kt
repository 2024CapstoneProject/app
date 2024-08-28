package com.example.capstoneapp.fastfood.ui.guide

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.ui.Frame.GuidePopup
import com.example.capstoneapp.cafe.ui.Frame.VerticalAlignment
import com.example.capstoneapp.chatbot.utils.TtsPlaybackHandler
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.fastfood.ui.theme.fontFamily
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
fun Fastfood_Guide2(navController: NavController, problem: Problem, showBorder: Boolean) {
    var showPopup by remember { mutableStateOf(true) }
    var currentStep by remember { mutableStateOf(1) } // 현재 단계 관리 변수

    val messageStep1 = "결제수단을 선택할 수 있습니다. 카드결제 또는 쿠폰 또는 현금을 사용할 수 있습니다!"
    val messageStep2 = "만약 현금으로 결제하신다면 카운터에서 주문을 다시 해주세요."
    val messageStep3 = "이번 예제는 ${problem.pay}로 진행하겠습니다."

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

    BackHandler {
        showPopup = false
        navController.navigate("HamburgerHomeScreen") {
            popUpTo("HamburgerHomeScreen") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(2.dp, Color.Gray, RoundedCornerShape(25.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "원하시는 결제방법을 선택해주세요",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
            ),
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .then(
                    if (showBorder && currentStep == 3 && problem.pay == "카드 결제") Modifier.border(
                        BorderWidth, BorderColor, BorderShape
                    ) else Modifier
                ),
            horizontalArrangement = Arrangement.SpaceBetween, // Arrange buttons with space in between
            verticalAlignment = Alignment.CenterVertically


        ) {
            Icon(
                painter = painterResource(id = R.drawable.cardicon),
                contentDescription = null,

                modifier = Modifier
                    .width(120.dp) // 아이콘의 너비를 48dp로 설정
                    .height(120.dp)
                    .weight(1f)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "카드",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                    )
                )
                Text(
                    text = "신용/체크카드 \n 모바일 금액권 \n 간편결제(페이)",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(48.dp))

        Divider(
            color = Color.Gray, // 선의 색상 지정
            thickness = 2.dp, // 선의 두께 지정
            modifier = Modifier.padding(horizontal = 16.dp) // 좌우 패딩 적용
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Apply horizontal padding
            horizontalArrangement = Arrangement.SpaceBetween,// Arrange buttons with space in between
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f) //왼쪽에 padding을 주기 위해
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pay),
                    contentDescription = null,
                    modifier = Modifier
                        .width(80.dp) // 아이콘의 너비를 48dp로 설정
                        .height(80.dp)
                )
                Text(
                    text = "디지털쿠폰/교환권",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Box(
                modifier = Modifier
                    .width(2.dp) // 선의 너비
                    .height(180.dp) // 선의 높이
                    .background(color = Color.Gray) // 선의 색상
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f) //왼쪽에 padding을 주기 위해
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cash),
                    contentDescription = null,
                    modifier = Modifier
                        .width(80.dp) // 아이콘의 너비를 48dp로 설정
                        .height(80.dp)
                )

                Text(
                    text = "현금",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }

    if (showPopup) {
        GuidePopup(
            isPopupVisible = showPopup,
            onDismiss = {
                currentStep += 1
                if (currentStep > 3) { // 모든 단계가 끝난 후
                    showPopup = false
                    navController.navigate("Fastfood_Guide3")
                }
            },
            title = "결제수단 선택",
            message = when (currentStep) {
                1 -> messageStep1
                2 -> messageStep2
                3 -> messageStep3
                else -> ""
            },
            highlights = when (currentStep) {
                1 -> listOf("결제수단", "카드결제", "쿠폰", "현금")
                2 -> listOf("현금", "카운터")
                3 -> listOf(problem.pay)
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

@Preview(showBackground = true)
@Composable
fun PaymentPreview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    Fastfood_Guide2(
        navController = navController,
        problemViewModel.getProblemValue()!!,
        showBorder = true
    )
}
