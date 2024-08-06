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
import com.example.capstoneapp.cafe.ui.Screens.showNextImage
import com.example.capstoneapp.cafe.ui.Screens.showPreviousImage
import kotlinx.coroutines.launch


@Composable
fun PhoneCameraGuide(navController: NavController) {
    var currentImageIndex by remember { mutableStateOf(0) }
    var isImageClicked by remember { mutableStateOf(false) }
    var clickedImageResource by remember { mutableStateOf(0) }

    val imageResources = listOf(
        R.drawable.camera_guide_000,
        R.drawable.camera_guide_001,
        R.drawable.camera_guide_002,
        R.drawable.camera_guide_003,
        R.drawable.camera_guide_004,
        R.drawable.camera_guide_005,
        R.drawable.camera_guide_006,
        R.drawable.camera_guide_007,
        R.drawable.camera_guide_008,
        R.drawable.camera_guide_009,
        R.drawable.camera_guide_010,
        R.drawable.camera_guide_011,
        R.drawable.camera_guide_012,
        R.drawable.camera_guide_013
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f),
    ) {
        cameraGuideImage(
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
        cameraGuideText(currentImageIndex)
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
fun cameraGuideImage(
    currentImageIndex: Int,
    imageResources: List<Int>,
    onImageIndexChanged: (Int) -> Unit,
    onImageClicked: () -> Unit
) {
    val currentImageResourceId = imageResources[currentImageIndex]
    val context = LocalContext.current
    val imageName = cameraGetResourceName(currentImageResourceId, context)

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
fun cameraGetResourceName(resourceId: Int, context: Context): String {
    return when (resourceId) {
        R.drawable.camera_guide_000 -> "카메라"
        R.drawable.camera_guide_001 -> "기본 화면"
        R.drawable.camera_guide_002 -> "사진 촬영"
        R.drawable.camera_guide_003 -> "촬영한 사진 확인"
        R.drawable.camera_guide_004 -> "촬영한 사진 확인"
        R.drawable.camera_guide_005 -> "전면 카메라 전환"
        R.drawable.camera_guide_006 -> "플래시 설정"
        R.drawable.camera_guide_007 -> "플래시 설정"
        R.drawable.camera_guide_008 -> "타이머 설정"
        R.drawable.camera_guide_009 -> "타이머 설정"
        R.drawable.camera_guide_010 -> "카메라 비율 설정"
        R.drawable.camera_guide_011 -> "카메라 비율 설정"
        R.drawable.camera_guide_012 -> "동영상 촬영"
        R.drawable.camera_guide_013 -> "동영상 촬영"
        else -> "Unknown"
    }
}

/*가이드 텍스트*/
@Composable
fun cameraGuideText(currentImageIndex: Int) {
    val textList = listOf(
        Triple("사진을 옆으로 넘기거나", "화살표를 눌러 확인해주세요.", "사진을 누르면 확대됩니다."),
        Triple("기본 홈 화면입니다.", "아래 카메라 버튼을", "눌러주세요."),
        Triple("사진 촬영하는 방법입니다.", "아래의 \"하얀 원\"버튼을 눌러주세요.", "사진이 촬영됩니다."),
        Triple("하단 왼쪽에 있는 사진 버튼을 누르면", "방금 촬영한 사진을 볼 수 있습니다.", ""),
        Triple("좋아하는 사진으로 등록하거나", "이름을 수정하고", "공유와 삭제 등이 가능합니다."),
        Triple("하단 오른쪽에 있는 전환 버튼을 누르면", "전면 카메라로 전환됩니다.", "자신의 얼굴을 촬영할 수 있습니다."),
        Triple("상단의 \"번개\"버튼을 눌러주세요.", "플래시를 켜거나 끌 수 있습니다.", ""),
        Triple("왼쪽에 있는 버튼은 플래시가 꺼진 상태입니다.", "가운데는 자동으로 설정합니다.", "오른쪽은 플래시를 켠 상태입니다."),
        Triple("상단의 \"시계\"버튼을 누르면", "타이머를 설정할 수 있습니다.", ""),
        Triple("첫 번째는 타이머를 설정하지 않습니다.", "두 번째부터 각각 2초, 5초, 10초로", "타이머를 설정합니다."),
        Triple("상단의 \"3:4\"버튼은 누르면", "사진 비율을 설정할 수 있습니다.", ""),
        Triple("각각 3:4, 9:16, 1:1, Full로", "설정할 수 있습니다.", "Full은 화면 꽉차게 촬영할 수 있습니다."),
        Triple("하단에서 \"동영상\"을 눌러주세요.", "동영상 촬영으로 바뀝니다.", ""),
        Triple("동영상 촬영은 사진 촬영과 똑같습니다.", "하단 가운데 \"하얀 원에 빨간 원\"버튼을 눌러주세요.", "동영상 촬영이 시작됩니다."),
    )

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        val (text1, text2, text3) = textList[currentImageIndex]
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text1, wordsToColor = mapOf(
                "사진" to Color.Blue, "전환" to Color.Red, "번개" to Color.Red,
                "시계" to Color.Red, "카메라" to Color.Red, "\"3:4\"" to Color.Red, "\"동영상\"" to Color.Red,
            )
        )
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text2, wordsToColor = mapOf(
                "카메라" to Color.Blue, "하얀 원" to Color.Red, "전면 카메라" to Color.Blue,
                "플래시" to Color.Blue, "타이머" to Color.Blue, "사진 비율" to Color.Blue, "하얀 원에 빨간 원" to Color.Red
            )
        )
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text3, wordsToColor = mapOf(
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun cameraGuideScreenPreview() {
    val navController = rememberNavController()
    PhoneCameraGuide(navController)
}
