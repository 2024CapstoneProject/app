package com.example.capstoneapp.kakatalk.ui.Screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R


@Composable
fun KakaoGuide0(navController: NavController) {
    var currentImageIndex by remember { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f),
    ) {
        guideImage(currentImageIndex) { newIndex ->
            currentImageIndex = newIndex
        }
        guideText(currentImageIndex)
    }
}

/*
* 가이드 이미지, 화살표 버튼
* */
@Composable
fun guideImage(currentImageIndex: Int, onImageIndexChanged: (Int) -> Unit) {
    val imageResources = listOf(
        R.drawable.kakao_guide_000,
        R.drawable.kakao_guide_001,
        R.drawable.kakao_guide_002,
        R.drawable.kakao_guide_004,
        R.drawable.kakao_guide_003,
        R.drawable.kakao_guide_005,
        R.drawable.kakao_guide_006,
    )
    val currentImageResourceId = imageResources[currentImageIndex]
    val context = LocalContext.current
    val imageName = getResourceName(currentImageResourceId, context)

    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
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

                val isImageClicked = remember { mutableStateOf(false) }
                val modifier = if (isImageClicked.value) {
                    Modifier.clickable { /* Do nothing */ }
                } else {
                    Modifier.clickable {
                        isImageClicked.value = true
                    }
                }

                val imageModifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .then(modifier)

                Image(
                    painter = painterResource(id = imageResources[currentImageIndex]),
                    contentDescription = null,
                    modifier = Modifier.size(width = 250.dp, height = 500.dp)
                )

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

                // 이미지가 클릭되었을 때만 팝업을 표시
                if (isImageClicked.value) {
                    EnlargedImagePopup(currentImageResourceId) {
                        isImageClicked.value = false // 팝업 닫히면 클릭 상태를 초기화
                    }
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

/*다음 이미지로 변경*/
fun showNextImage(size: Int, currentIndex: Int): Int {
    return (currentIndex + 1) % size
}

/*이전 이미지로 변경*/
fun showPreviousImage(size: Int, currentIndex: Int): Int {
    return if (currentIndex == 0) size - 1 else currentIndex - 1
}

@Composable
fun EnlargedImagePopup(imageResource: Int, onClose: () -> Unit) {
    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { /* Do nothing on click */ },
                contentScale = ContentScale.Fit // 이미지가 화면에 맞게 최대로 확대됨
            )
        }
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
        TextWithColoredWords(
            text = text1, wordsToColor = mapOf(
                "프로필" to Color.Green, "앨범" to Color.Blue, "사진" to Color.Blue
            )
        )
        TextWithColoredWords(
            text = text2, wordsToColor = mapOf(
                "친구목록" to Color.Blue, "메세지" to Color.Blue, "화면 오른쪽 아래" to Color.Red,"최대 30장" to Color.Red
            )
        )
        TextWithColoredWords(
            text = text3, wordsToColor = mapOf(
                "이름" to Color.Red, "채팅" to Color.Red,"화살표" to Color.Green
            )
        )
    }
}

/*텍스트 디자인*/
@Composable
fun TextWithColoredWords(text: String, wordsToColor: Map<String, Color>) {
    val spannableString = buildAnnotatedString {
        append(text)
        wordsToColor.forEach { (word, color) ->
            val startIndex = text.indexOf(word)
            if (startIndex >= 0) {
                val endIndex = startIndex + word.length
                addStyle(SpanStyle(color = color), startIndex, endIndex)
            }
        }
    }

    Text(
        text = spannableString,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Center,
    )
}

@Preview(showBackground = true)
@Composable
fun kakaoGuideScreenPreview() {
    val navController = rememberNavController()
    var currentImageIndex by remember { mutableStateOf(0) }
    KakaoGuide0(navController)

}
