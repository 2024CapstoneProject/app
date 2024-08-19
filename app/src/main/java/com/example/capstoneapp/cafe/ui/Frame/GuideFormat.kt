package com.example.capstoneapp.cafe.ui.Frame

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import com.example.capstoneapp.R
import com.example.capstoneapp.chatbot.utils.TtsPlaybackHandler
import kotlinx.coroutines.delay

@Composable
fun GuidePopup(
    isPopupVisible: Boolean,
    onDismiss: () -> Unit,
    title: String,
    message: String,
    highlights: List<String>,
    onConfirm: () -> Unit = {},
    delayMillis: Long = 150, // 지연 시간 (밀리초)
    verticalAlignment: VerticalAlignment, // 추가된 매개변수
    ttsPlaybackHandler: TtsPlaybackHandler // TTS Playback Handler 추가
) {
    // 팝업 표시 상태를 관리할 변수
    var showPopup by remember { mutableStateOf(false) }

    // LaunchedEffect를 사용하여 지연 후 팝업 표시
    LaunchedEffect(isPopupVisible, message) { // message 추가
        if (isPopupVisible) {
            delay(delayMillis)
            showPopup = true
            // 팝업 메시지를 TTS로 읽기
            ttsPlaybackHandler.playText(message)
        } else {
            showPopup = false
        }
    }

    if (showPopup) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent) // 반투명 배경으로 외부 클릭 방지
                .clickable {} // 클릭 이벤트를 무시하여 외부 클릭 방지
                .padding(10.dp)
        ) {
            Popup(
                alignment = when (verticalAlignment) {
                    VerticalAlignment.Top -> Alignment.TopCenter
                    VerticalAlignment.Center -> Alignment.Center
                    VerticalAlignment.Bottom -> Alignment.BottomCenter
                },
                properties = PopupProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false // 외부 클릭으로 팝업 닫기 방지
                )
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 350.dp) // 팝업의 최대 너비 설정
                        .background(Color.White, RoundedCornerShape(16.dp)) // 팝업의 배경을 흰색으로 설정
                        .border(
                            2.dp,
                            Color.Gray,
                            RoundedCornerShape(16.dp)
                        ) // 팝업의 테두리
                        .padding(16.dp)
                        .wrapContentSize()
                        .zIndex(1f) // 팝업이 최상위 레이어에 표시되도록 설정
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                        val annotatedString = buildAnnotatedString {
                            var currentIndex = 0
                            highlights.forEach { highlight ->
                                val startIndex = message.indexOf(highlight, currentIndex)
                                if (startIndex >= 0) {
                                    val endIndex = startIndex + highlight.length
                                    addStyle(
                                        style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                                        start = startIndex,
                                        end = endIndex
                                    )
                                    currentIndex = endIndex // Move past the highlighted section
                                }
                            }
                            append(message) // Append remaining message text after highlighting
                        }
                        Text(
                            text = annotatedString,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = {
                                    onConfirm()
                                    onDismiss()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                    contentDescription = "확인 버튼"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

// 위치 설정을 위한 enum 클래스
enum class VerticalAlignment {
    Top,
    Center,
    Bottom
}
interface TtsPlaybackHandler {
    fun playText(text: String)
}
/*
@Preview(showBackground = true)
@Composable
fun PopupPreview() {
    // 더미 TtsPlaybackHandler 생성
    val dummyTtsPlaybackHandler = object : TtsPlaybackHandler {
        override fun playText(text: String) {
            // Preview에서는 실제로 TTS를 재생하지 않음
            println("Dummy TTS: $text")
        }
    }

    GuidePopup(
        isPopupVisible = true,
        onDismiss = { },
        title = "광고",
        message = "광고 화면입니다. 화면을 터치하여 주문을 시작해주세요!",
        highlights = listOf("광고", "터치"),
        delayMillis = 150, // Preview에서 지연 시간 설정
        verticalAlignment = VerticalAlignment.Center, // 중앙 배치 예시
        ttsPlaybackHandler = dummyTtsPlaybackHandler // 더미 TTS Playback Handler 사용
    )
}*/
