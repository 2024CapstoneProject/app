package com.example.capstoneapp.kakatalk.ui.Screens

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
fun KakaoGuide0(navController: NavController) {
    var currentImageIndex by remember { mutableStateOf(0) }
    var isImageClicked by remember { mutableStateOf(false) }
    var clickedImageResource by remember { mutableStateOf(0) }

    val imageResources = listOf(
        R.drawable.kakao_guide_000,
        R.drawable.kakao_guide_001,
        R.drawable.kakao_guide_002,
        R.drawable.kakao_guide_004,
        R.drawable.kakao_guide_003,
        R.drawable.kakao_guide_005,
        R.drawable.kakao_guide_006,
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
        R.drawable.kakao_guide_000 -> "카카오톡"
        R.drawable.kakao_guide_001 -> "친구 목록"
        R.drawable.kakao_guide_002 -> "프로필 확인"
        R.drawable.kakao_guide_004 -> "채팅 보내기"
        R.drawable.kakao_guide_003 -> "사진 보내기 1"
        R.drawable.kakao_guide_005 -> "사진 보내기 2"
        R.drawable.kakao_guide_006 -> "사진 보내기 3"
        else -> "Unknown"
    }
}

/*가이드 텍스트*/
@Composable
fun guideText(currentImageIndex: Int) {
    val textList = listOf(
        Triple("사진을 옆으로 넘기거나", "화살표를 눌러 확인해주세요.", "사진을 누르면 확대됩니다."),
        Triple("카카오톡을 실행하면", "친구목록이 나타납니다.", "원하는 사람의 이름을 눌러주세요."),
        Triple("프로필을 확인할 수 있습니다.", "\"메세지\"를 보내고 싶으시다면,", "\"채팅\"을 눌러주세요."),
        Triple("보내고 싶은 말을 입력해주세요.", "입력이 끝났다면, 오른쪽에 있는", "화살표 모양 버튼을 눌러주세요"),
        Triple("화면 왼쪽 아래에 있는", "+ 버튼을 눌러주세요.", ""),
        Triple("\"앨범\"을 눌러주세요", "", ""),
        Triple("원하는 사진을 선택해주세요.", "최대 30장까지 선택할 수 있습니다.", "사진을 선택했다면 화살표를 눌러주세요.")
    )

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        val (text1, text2, text3) = textList[currentImageIndex]
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text1, wordsToColor = mapOf(
                "프로필" to Color.Green, "앨범" to Color.Blue, "사진" to Color.Blue
            )
        )
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text2, wordsToColor = mapOf(
                "친구목록" to Color.Blue, "메세지" to Color.Blue, "화면 오른쪽 아래" to Color.Red,"최대 30장" to Color.Red
            )
        )
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text3, wordsToColor = mapOf(
                "이름" to Color.Red, "채팅" to Color.Red,"화살표" to Color.Green
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun kakaoGuideScreenPreview() {
    val navController = rememberNavController()
    KakaoGuide0(navController)

}
