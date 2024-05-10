package com.example.capstoneapp.phone.ui.screens

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
import com.example.capstoneapp.cafe.ui.Screens.EnlargedImagePopup


@Composable
fun PhoneContactGuide(navController: NavController) {
    var currentImageIndex by remember { mutableStateOf(0) }
    var isImageClicked by remember { mutableStateOf(false) }
    var clickedImageResource by remember { mutableStateOf(0) }

    val imageResources = listOf(
        R.drawable.contact_guide_000,
        R.drawable.contact_guide_001,
        R.drawable.contact_guide_002,
        R.drawable.contact_guide_003,
        R.drawable.contact_guide_004,
        R.drawable.contact_guide_005,
        R.drawable.contact_guide_006,
        R.drawable.contact_guide_007,
        R.drawable.contact_guide_008,
        R.drawable.contact_guide_009,
        R.drawable.contact_guide_010
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f),
    ) {
        contactguideImage(
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
        contactGuideText(currentImageIndex)
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
fun contactguideImage(
    currentImageIndex: Int,
    imageResources: List<Int>,
    onImageIndexChanged: (Int) -> Unit,
    onImageClicked: () -> Unit
) {
    val currentImageResourceId = imageResources[currentImageIndex]
    val context = LocalContext.current
    val imageName = contactgetResourceName(currentImageResourceId, context)

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
                        com.example.capstoneapp.cafe.ui.Screens.showPreviousImage(
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
                        com.example.capstoneapp.cafe.ui.Screens.showNextImage(
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
fun contactgetResourceName(resourceId: Int, context: Context): String {
    return when (resourceId) {
        R.drawable.contact_guide_000 -> "연락처"
        R.drawable.contact_guide_001 -> "기본 화면"
        R.drawable.contact_guide_002 -> "연락처 추가"
        R.drawable.contact_guide_003 -> "연락처 추가"
        R.drawable.contact_guide_004 -> "연락처 입력&저장"
        R.drawable.contact_guide_005 -> "연락처 저장 완료"
        R.drawable.contact_guide_006 -> "연락처 편집"
        R.drawable.contact_guide_007 -> "연락처 편집"
        R.drawable.contact_guide_008 -> "연락처 삭제"
        R.drawable.contact_guide_009 -> "연락처 삭제"
        R.drawable.contact_guide_010 -> "연락처 삭제"
        else -> "Unknown"
    }
}

/*가이드 텍스트*/
@Composable
fun contactGuideText(currentImageIndex: Int) {
    val textList = listOf(
        Triple("사진을 옆으로 넘기거나", "화살표를 눌러 확인해주세요.", "사진을 누르면 확대됩니다."),
        Triple("기본 홈 화면입니다.", "아래 전화 버튼을", "눌러주세요."),
        Triple("연락처를 추가하는 방법입니다.", "아래의 \"연락처\"를 눌러주세요.", ""),
        Triple("상단의 +를 눌러주세요", "연락처를 추가할 수 있습니다.", ""),
        Triple("이름과 전화번호 등을 입력해주세요.", "입력이 끝나면", "하단의 \"저장\"을 눌러주세요."),
        Triple("", "연락처가 저장되었습니다!", ""),
        Triple("연락처를 변경하는 방법입니다.", "하단의 \"편집을\" 눌러주세요.", ""),
        Triple("이름과 전화번호 등을 입력해주세요.", "입력이 끝나면", "하단의 \"저장\"을 눌러주세요."),
        Triple("연락처를 삭제하는 법입니다.", "하단의 \"더보기\"를 눌러주세요.", ""),
        Triple("", "\"삭제\" 버튼을 눌러주세요.", ""),
        Triple("\"휴지통으로 이동\"을 눌러주세요.", "연락처가 삭제됩니다.", ""),
    )

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        val (text1, text2, text3) = textList[currentImageIndex]
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text1, wordsToColor = mapOf(
                "사진" to Color.Blue, "추가" to Color.Blue, "+" to Color.Red,
                "변경" to Color.Blue, "삭제" to Color.Blue, "휴지통으로 이동" to Color.Red
            )
        )
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text2, wordsToColor = mapOf(
                "전화" to Color.Green, "연락처" to Color.Red, "편집" to Color.Red,
                "더보기" to Color.Red, "삭제" to Color.Red
            )
        )
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text3, wordsToColor = mapOf(
                "저장" to Color.Red
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun contactGuideScreenPreview() {
    val navController = rememberNavController()
    var currentImageIndex by remember { mutableStateOf(0) }
    PhoneContactGuide(navController)
}
