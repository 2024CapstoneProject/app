package com.example.capstoneapp.chatbot.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.capstoneapp.cafe.ui.Screens.showNextImage
import com.example.capstoneapp.cafe.ui.Screens.showPreviousImage
import kotlinx.coroutines.launch

@Composable
fun ChatGuide(navController: NavController) {
    var currentImageIndex by remember { mutableStateOf(0) }
    var isImageClicked by remember { mutableStateOf(false) }
    var clickedImageResource by remember { mutableStateOf(0) }

    val imageResources = listOf(
        R.drawable.chat_guide_001,
        R.drawable.chat_guide_002,
        R.drawable.chat_guide_003,
        R.drawable.chat_guide_004,
        R.drawable.chat_guide_005,
        R.drawable.chat_guide_006,
        R.drawable.chat_guide_007,
        R.drawable.chat_guide_008,
        R.drawable.chat_guide_009,
        R.drawable.chat_guide_010,
        R.drawable.chat_guide_011,
        R.drawable.chat_guide_012,
        R.drawable.chat_guide_013,
        R.drawable.chat_guide_014,
        R.drawable.chat_guide_015,
        R.drawable.chat_guide_016,
        R.drawable.chat_guide_017,
        R.drawable.chat_guide_018,
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f),
    ) {
        guideImage(
            currentImageIndex = currentImageIndex,
            imageResources = imageResources, // 이미지 리소스를 함수로 전달
            onImageIndexChanged = { newIndex ->
                currentImageIndex = newIndex
            },
            onImageClicked = {
                isImageClicked = true
                clickedImageResource = imageResources[currentImageIndex]
            }
        )
        guideText(currentImageIndex)
    }

    if (isImageClicked) {
        com.example.capstoneapp.cafe.ui.Screens.EnlargedImagePopup(
            imageResource = clickedImageResource,
            onClose = {
                isImageClicked = false
            }
        )
    }
}

/*
* 가이드 이미지, 화살표 버튼
* */
@Composable
fun guideImage(
    currentImageIndex: Int,
    imageResources: List<Int>,
    onImageIndexChanged: (Int) -> Unit,
    onImageClicked: () -> Unit
) {
    val currentImageResourceId = imageResources[currentImageIndex]
    val context = LocalContext.current
    val imageName = getResourceName(currentImageResourceId, context)

    // 드래그 동작에 대한 제스처 인식기
    val offsetX = remember { mutableStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        // 드래그로 인한 X 좌표 이동
                        offsetX.value += delta
                    },
                    onDragStopped = { velocity ->
                        // 드래그로 인한 페이지 변경
                        if (offsetX.value > 150 || (offsetX.value < -150 && velocity > 0)) {
                            coroutineScope.launch {
                                onImageIndexChanged(
                                    showPreviousImage(imageResources.size, currentImageIndex)
                                )
                            }
                            offsetX.value = 0f
                        } else if (offsetX.value < -150 || (offsetX.value > 150 && velocity < 0)) {
                            coroutineScope.launch {
                                onImageIndexChanged(
                                    showNextImage(imageResources.size, currentImageIndex)
                                )
                            }
                            offsetX.value = 0f
                        }
                    }
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = imageName,
                style = TextStyle(fontSize = 30.sp),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    onImageIndexChanged(
                        showPreviousImage(
                            imageResources.size, currentImageIndex
                        )
                    )
                }) {
                    Image(
                        painter = painterResource(id = R.mipmap.arrow_back),
                        contentDescription = "Previous"
                    )
                }

                Spacer(modifier = Modifier.width(0.dp))

                Box(
                    modifier = Modifier.clickable(onClick = onImageClicked)
                ) {
                    Image(
                        painter = painterResource(id = imageResources[currentImageIndex]),
                        contentDescription = null,
                        modifier = Modifier.size(width = 250.dp, height = 500.dp)
                    )
                }

                Spacer(modifier = Modifier.width(0.dp))

                IconButton(onClick = {
                    onImageIndexChanged(
                        showNextImage(
                            imageResources.size, currentImageIndex
                        )
                    )
                }) {
                    Image(
                        painter = painterResource(id = R.mipmap.arrow_forward),
                        contentDescription = "Next"
                    )
                }
            }
        }
    }
}

@Composable
fun getResourceName(resourceId: Int, context: Context): String {
    return when (resourceId) {
        R.drawable.chat_guide_001 -> "기본 화면"
        R.drawable.chat_guide_002 -> "질문하기"
        R.drawable.chat_guide_003 -> "말하기"
        R.drawable.chat_guide_004 -> "다시 듣기"
        R.drawable.chat_guide_005 -> "새 대화 하기"
        R.drawable.chat_guide_006 -> "질문하기"
        R.drawable.chat_guide_008 -> "질문하기"
        R.drawable.chat_guide_009 -> "질문하기"
        R.drawable.chat_guide_010 -> "질문하기"
        R.drawable.chat_guide_007 -> "질문하기"
        R.drawable.chat_guide_011 -> "말하기로 질문하기"
        R.drawable.chat_guide_012 -> "말하기로 질문하기"
        R.drawable.chat_guide_013 -> "말하기로 질문하기"
        R.drawable.chat_guide_014 -> "말하기로 질문하기"
        R.drawable.chat_guide_015 -> "대답 다시 듣기"
        R.drawable.chat_guide_016 -> "대답 다시 듣기"
        R.drawable.chat_guide_017 -> "새 대화 하기"
        R.drawable.chat_guide_018 -> "새 대화 하기"
        else -> "Unknown"
    }
}

/*가이드 텍스트*/
@Composable
fun guideText(currentImageIndex: Int) {
    val textList = listOf(
        Triple("사진을 옆으로 넘기거나", "화살표를 눌러 확인해주세요.", "사진을 누르면 확대됩니다."),
        Triple("질문을 하는 칸입니다.", "질문할 내용을 입력한 후", "오른쪽 보내기 버튼을 눌러주세요."),
        Triple("말하기 버튼입니다.", "하고싶은 질문을 말로 해주세요.", ""),
        Triple("다시 듣기 버튼입니다.", "챗봇이 대답을 음성으로 말해줍니다.", ""),
        Triple("새 대화 하기 버튼입니다.", "기존의 질문을 지우고", "새 창을 원한다면 눌러주세요."),
        Triple("챗봇에게 질문하는 방법입니다.", "질문할 내용을 입력한 후", "오른쪽 보내기 버튼을 눌러주세요."),
        Triple("보내기 버튼을 누르면", "챗봇이 대답을 합니다.", "계속 대화를 이어 가세요!"),
        Triple("만약 대답이 4줄 이상이라면", "대답이 팝업으로 나옵니다.", "화살표를 누르면 다음 대답을 볼 수 있습니다."),
        Triple("\"닫기\" 버튼을 누르면", "팝업이 사라집니다.", ""),
        Triple("\"다시 듣기\" 버튼을 누르면", "화면에 나오는 대답을", "음성으로 들을 수 있습니다."),
        Triple("음성으로 질문하는 방법입니다.", "음성으로 질문하고 싶다면", "\"말하기\" 버튼을 눌러주세요"),
        Triple("다음과 같은 창이 뜹니다.", "이때 질문할 내용을 말해주세요.", "챗봇이 질문을 기다립니다!"),
        Triple("다음은 예시입니다.", "\"버거킹에서 불고기 와퍼 주문하고 싶어\"를", "말하고 있습니다."),
        Triple("챗봇이 음성을 들었다면", "질문하는 칸에 말했던 내용이 나타납니다!", "질문이 맞다면 보내기 버튼을 눌러주세요."),
        Triple("챗봇이 제공한 대답을", "음성으로 듣고싶다면", "\"다시 듣기\" 버튼을 눌러주세요."),
        Triple("챗봇이 대답을 말해주는 상태입니다!", "", ""),
        Triple("기존의 질문과 답을 지우고", "새로운 창을 원한다면", "\"새 대화 하기\" 버튼을 눌러주세요."),
        Triple("새 창이 나왔습니다.", "챗봇에게 질문해주세요!", "")
        )

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        val (text1, text2, text3) = textList[currentImageIndex]
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text1, wordsToColor = mapOf(
                "말하기" to Color.Red, "다시 듣기" to Color.Red, "새 대화 하기" to Color.Red, "4줄 이상" to Color.Blue, "닫기" to Color.Red
            )
        )
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text2, wordsToColor = mapOf(
                "음성" to Color.Blue, "새로운 창" to Color.Green
            )
        )
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text3, wordsToColor = mapOf(
                "말하기" to Color.Red, "다시 듣기" to Color.Red, "새 대화 하기" to Color.Red
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun chatGuideScreenPreview() {
    val navController = rememberNavController()
    ChatGuide(navController)
}
