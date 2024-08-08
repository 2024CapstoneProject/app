package com.example.capstoneapp.phone.ui.screens

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
import com.example.capstoneapp.chatbot.ui.components.showNextImage
import com.example.capstoneapp.chatbot.ui.components.showPreviousImage
import com.example.capstoneapp.chatbot.ui.components.EnlargedImagePopup
import com.example.capstoneapp.chatbot.ui.components.TextWithColoredWords
import kotlinx.coroutines.launch


@Composable
fun PhoneMessageGuide(navController: NavController) {
    var currentImageIndex by remember { mutableStateOf(0) }
    var isImageClicked by remember { mutableStateOf(false) }
    var clickedImageResource by remember { mutableStateOf(0) }

    val imageResources = listOf(
        R.drawable.message_guide_000,
        R.drawable.message_guide_001,
        R.drawable.message_guide_002,
        R.drawable.message_guide_003,
        R.drawable.message_guide_004,
        R.drawable.message_guide_005,
        R.drawable.message_guide_006,
        R.drawable.message_guide_007,
        R.drawable.message_guide_008,
        R.drawable.message_guide_009,
        R.drawable.message_guide_010,
        R.drawable.message_guide_011,
        R.drawable.message_guide_012,
        R.drawable.message_guide_013,
        R.drawable.message_guide_014,
        R.drawable.message_guide_015,
        R.drawable.message_guide_016,
        R.drawable.message_guide_017,
        R.drawable.message_guide_018,
        R.drawable.message_guide_019,
        R.drawable.message_guide_020
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f),
    ) {
        messageGuideImage(
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
        messageGuideText(currentImageIndex)
    }

    if (isImageClicked) {
        EnlargedImagePopup(
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
fun messageGuideImage(
    currentImageIndex: Int,
    imageResources: List<Int>,
    onImageIndexChanged: (Int) -> Unit,
    onImageClicked: () -> Unit
) {
    val currentImageResourceId = imageResources[currentImageIndex]
    val context = LocalContext.current
    val imageName = messageGetResourceName(currentImageResourceId, context)

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
fun messageGetResourceName(resourceId: Int, context: Context): String {
    return when (resourceId) {
        R.drawable.message_guide_000 -> "메시지"
        R.drawable.message_guide_001 -> "기본 화면"
        R.drawable.message_guide_002 -> "메시지 보내기"
        R.drawable.message_guide_003 -> "1:1 대화 & 단체 문자"
        R.drawable.message_guide_004 -> "대화 상대 선택"
        R.drawable.message_guide_005 -> "메시지 작성"
        R.drawable.message_guide_006 -> "메시지 보내기"
        R.drawable.message_guide_007 -> "기타 보내기"
        R.drawable.message_guide_008 -> "기타 보내기"
        R.drawable.message_guide_009 -> "카메라 찍어서 보내기"
        R.drawable.message_guide_010 -> "사진 촬영 & 동영상 촬영"
        R.drawable.message_guide_011 -> "카메라 찍어서 보내기"
        R.drawable.message_guide_012 -> "카메라 찍어서 보내기"
        R.drawable.message_guide_013 -> "카메라 찍어서 보내기"
        R.drawable.message_guide_014 -> "카메라 찍어서 보내기"
        R.drawable.message_guide_015 -> "갤러리 이미지 보내기"
        R.drawable.message_guide_016 -> "갤러리 이미지 보내기"
        R.drawable.message_guide_017 -> "갤러리 이미지 보내기"
        R.drawable.message_guide_018 -> "기타 기능"
        R.drawable.message_guide_019 -> "기타 기능 중 메시지 삭제"
        R.drawable.message_guide_020 -> "기타 기능 중 메시지 삭제"
        else -> "Unknown"
    }
}

/*가이드 텍스트*/
@Composable
fun messageGuideText(currentImageIndex: Int) {
    val textList = listOf(
        Triple("사진을 옆으로 넘기거나", "화살표를 눌러 확인해주세요.", "사진을 누르면 확대됩니다."),
        Triple("기본 홈 화면입니다.", "아래 메시지 버튼을", "눌러주세요."),
        Triple("메시지를 보내는 방법입니다.", "아래의 \"메시지\"버튼을 눌러주세요.", ""),
        Triple("1:1 대화나 단체 문자로 메시지를 보냅니다.", "1:1로 문자를 보내는 예시입니다.", "\"1:1 대화\"를 눌러주세요."),
        Triple("이름이나 전화번호를 입력해주세요.", "또는 목록에서 연락처를 선택해주세요", ""),
        Triple("상대에게 보낼 메시지를 입력해주세요.", "입력이 끝나면", "오른쪽에 \"보내기\"버튼을 눌러주세요."),
        Triple("메시지가 보내졌습니다!", "", ""),
        Triple("하단의 \"+\"버튼을 누르면", "다른 연락처, 이미지 등을", "보낼 수 있습니다."),
        Triple("\"+\"버튼을 눌렀을 때", "화면에 보이는 것들을 보낼 수 있습니다.", ""),
        Triple("하단의 \"카메라\"버튼은 누르면", "사진이나 동영상을 찍어", "메시지를 보낼 수 있습니다."),
        Triple("사진 또는 동영상을 선택합니다.", "", ""),
        Triple("보내고 싶은 사진을 촬영해주세요.", "하단 가운데에 있는 하얀 원을 누르면 촬영이 됩니다.", ""),
        Triple("다시 시도 또는 확인을 눌러주세요.", "다시 시도는 재촬영을 합니다.", "확인은 촬영한 사진을 보냅니다."),
        Triple("하단 오른쪽에 \"보내기\"버튼을 눌러주세요.", "사진이 보내집니다.", ""),
        Triple("촬영한 사진이 보내졌습니다!", "", ""),
        Triple("하단의 \"이미지\"버튼을 누르면", "갤러리에 저장되어 있는", "사진을 보낼 수 있습니다."),
        Triple("보내고 싶은 사진을 선택 후", "하단 오른쪽에 \"보내기\"버튼을 눌러주세요.", ""),
        Triple("선택한 사진이 보내졌습니다!", "", ""),
        Triple("상단의 \"세로로 된 점 3개\"를 누르면", "메시지를 삭제하거나.", "메시지를 검색할 수 있습니다."),
        Triple("메시지를 삭제하는 방법입니다.", "\"메시지 삭제\"를 눌러주세요.", ""),
        Triple("삭제할 메시지를 선택한 후", "\"모두 삭제\"를 눌러주세요.", "메시지가 삭제됩니다."),
    )

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        val (text1, text2, text3) = textList[currentImageIndex]
        TextWithColoredWords(
            text = text1, wordsToColor = mapOf(
                "사진" to Color.Blue, "1:1 대화" to Color.Blue, "단체 문자" to Color.Blue,
                "+" to Color.Red, "카메라" to Color.Red, "선택" to Color.Blue, "촬영" to Color.Blue,
                "다시 시도" to Color.Blue, "확인" to Color.Blue, "보내기" to Color.Red,
                "이미지" to Color.Red, "세로로 된 점 3개" to Color.Red, "동영상" to Color.Blue
            )
        )
        TextWithColoredWords(
            text = text2, wordsToColor = mapOf(
                "메시지" to Color.Blue, "하얀 원" to Color.Red, "갤러리" to Color.Blue,
                "보내기" to Color.Red, "메시지 삭제" to Color.Red, "모두 삭제" to Color.Red
            )
        )
        TextWithColoredWords(
            text = text3, wordsToColor = mapOf(
                "1:1 대화" to Color.Red, "보내기" to Color.Red,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun messageGuideScreenPreview() {
    val navController = rememberNavController()
    PhoneMessageGuide(navController)
}
