package com.example.capstoneapp.fastfood.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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

@Composable
fun FastfoodGuideScreenPreview(navController: NavController) {
    var currentImageIndex by remember { mutableStateOf(0) }
    var isImageClicked by remember { mutableStateOf(false) }
    var clickedImageResource by remember { mutableStateOf(0) }

    val imageResources = listOf(
        R.drawable.hamburger,
        R.drawable.guide_000,
        R.drawable.fastfood_guide_001,
        R.drawable.fastfood_guide_002,
        R.drawable.fastfood_guide_003,
        R.drawable.fastfood_guide_004,
        R.drawable.fastfood_guide_005,
        R.drawable.fastfood_guide_006,
        R.drawable.fastfood_guide_007,
        R.drawable.fastfood_guide_008
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
fun getResourceName(resourceId: Int, context: Context): String {
    return when (resourceId) {
        R.drawable.hamburger -> "패스트푸드"
        R.drawable.guide_000 -> "터치 화면"
        R.drawable.fastfood_guide_001 -> "결제 수단 선택"
        R.drawable.fastfood_guide_002 -> context.getString(R.string.burger_text)
        R.drawable.fastfood_guide_003 -> context.getString(R.string.set_text)
        R.drawable.fastfood_guide_004 -> context.getString(R.string.dessert_text)
        R.drawable.fastfood_guide_005 -> context.getString(R.string.drink_text)
        R.drawable.fastfood_guide_006 -> "주문 메뉴 확인"
        R.drawable.fastfood_guide_007 -> "포장 여부&적립&결제"
        R.drawable.fastfood_guide_008 -> "카드 결제 진행&종료"
        else -> "Unknown"
    }
}

@Composable
fun guideText(currentImageIndex: Int) {
    val textList = listOf(
        Triple("사진을 옆으로 넘기거나", "화살표를 눌러 확인해주세요.", "사진을 누르면 확대됩니다."),
        Triple("화면을 눌러주세요!", "보통은 광고 화면이 나옵니다.", ""),
        Triple("카드, 디지털 교환권, 현금 중", "원하시는 결제 방법을", "선택해주세요."),
        Triple("원하시는 햄버거 메뉴를 골라주세요.", "선택된 메뉴는", "하단 목록에 표시됩니다."),
        Triple("버거 단품 또는", "음료와 디저트가 있는 세트 메뉴를", "선택하실 수 있습니다."),
        Triple("세트 구성에 포함된", "세트 디저트를 선택해주세요.", "일부 메뉴는 추가 금액이 있습니다."),
        Triple("세트 구성에 포함된", "세트 드링크를 선택해주세요.", "일부 메뉴는 추가 금액이 있습니다."),
        Triple("선택한 메뉴와 금액을 다시 확인한 후", "\"결제하기\"를 눌러주세요.", ""),
        Triple("좌측엔 선택하신 메뉴 목록이,", "우측에는 포장/매장, 할인과 적립,", "결제 방법을 선택할 수 있습니다."),
        Triple("해당 그림이 나타나면", "키오스크 하단에 있는 카드 투입구에", "카드를 넣어 주세요.")
    )

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        val (text1, text2, text3) = textList[currentImageIndex]
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text1,
            wordsToColor = mapOf(
                "사진" to Color.Blue, "화면" to Color.Red,
                "햄버거" to Color.Blue,
                "버거 단품" to Color.Red
            )
        )
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text2,
            wordsToColor = mapOf(
                "광고" to Color.Blue,
                "세트 메뉴" to Color.Red,
                "세트 디저트" to Color.Blue, "세트 드링크" to Color.Blue,
                "결제하기" to Color.Red,
                "화면 오른쪽 아래" to Color.Red
            )
        )
        com.example.capstoneapp.cafe.ui.Screens.TextWithColoredWords(
            text = text3,
            wordsToColor = mapOf(
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FastfoodGuideScreenPreviews() {
    val navController = rememberNavController()
    var currentImageIndex by remember { mutableStateOf(0) }
    FastfoodGuideScreenPreview(navController)
}

