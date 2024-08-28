package com.example.capstoneapp.cafe.ui.Guide

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.texttospeech.v1.TextToSpeechClient
import com.google.cloud.texttospeech.v1.TextToSpeechSettings
import java.io.InputStream

@Composable
fun Guide1(navController: NavController, showBorder: Boolean) {
    // 팝업을 표시할지 여부를 관리하는 상태
    var showPopup by remember { mutableStateOf(true) }

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


    BackHandler {
        showPopup = false
        navController.navigate("CafeHomeScreen") {
            popUpTo("CafeHomeScreen") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp) // 화면 가장자리 여백
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(2.dp, Color.Gray, RoundedCornerShape(25.dp))
                .then(
                    if (showBorder) Modifier.border(
                        BorderWidth,
                        BorderColor,
                        BorderShape
                    ) else Modifier
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // 화면 중앙에 배치
        ) {
            Icon(
                painter = painterResource(id = R.drawable.touch_icon),
                contentDescription = null,
                modifier = Modifier.size(128.dp) // 아이콘 크기 조정
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "화면을 터치해 주세요",
                style = TextStyle(
                    fontSize = 32.sp, // 텍스트 크기 조정
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

    // 팝업을 화면 중앙이 아닌 지정된 위치에 배치
    if (showPopup) {
        GuidePopup(
            isPopupVisible = showPopup,
            onDismiss = {
                showPopup = false
                navController.navigate("Guide2_kiosk")
            },
            title = "광고",
            message = "광고 화면입니다. 화면을 터치하여 주문을 시작해주세요!",
            highlights = listOf("광고", "터치"),
            verticalAlignment = VerticalAlignment.Top, //
            ttsPlaybackHandler = ttsPlaybackHandler // TTS 기능을 활성화합니다
        )
    }

}

@Preview(showBackground = true)
@Composable
fun Guide1Preview() {
    val navController = rememberNavController()
    Guide1(navController = navController, showBorder = false)
}