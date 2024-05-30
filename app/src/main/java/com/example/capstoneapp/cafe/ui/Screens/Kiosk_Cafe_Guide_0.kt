package com.example.capstoneapp.cafe.ui.Screens

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
fun KioskCafeGuide0(navController: NavController) {
    var currentImageIndex by remember { mutableStateOf(0) }
    var isImageClicked by remember { mutableStateOf(false) }
    var clickedImageResource by remember { mutableStateOf(0) }

    // 이미지 리소스 리스트를 함수 외부에서 정의
    val imageResources = listOf(
        R.drawable.cafe_icon,
        R.drawable.guide_000,
        R.drawable.cafe_guide_001,
        R.drawable.cafe_guide_002,
        R.drawable.cafe_guide_003,
        R.drawable.cafe_guide_004,
        R.drawable.cafe_guide_005,
        R.drawable.cafe_guide_006,
        R.drawable.cafe_guide_007
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f),
    ) {
        guideImage(
            currentImageIndex = currentImageIndex,
            imageResources = imageResources, // 이미지 리소스를 함수로 전달
            onImageIndexChanged =  { newIndex ->
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
        R.drawable.cafe_icon -> "카페"
        R.drawable.guide_000 -> "터치 화면"
        R.drawable.cafe_guide_001 -> "음료 선택"
        R.drawable.cafe_guide_002 -> "주문 메뉴 확인"
        R.drawable.cafe_guide_003 -> "포장 여부 선택"
        R.drawable.cafe_guide_004 -> "결제 수단 선택"
        R.drawable.cafe_guide_005 -> "카드 결제"
        R.drawable.cafe_guide_006 -> "쿠폰 사용"
        R.drawable.cafe_guide_007 -> "종료"
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
        Triple("화면을 눌러주세요!", "보통은 광고 화면이 나옵니다.", ""),
        Triple("드시고 싶은 음료를 선택해주세요.", "상단에 커피(HOT), 커피(ICE) 등 탭을 눌러", "원하는 음료를 찾아주세요."),
        Triple("음료를 선택했다면", "하단에 선택한 메뉴와 갯수를", "확인 후 \"결제\"를 눌러주세요."),
        Triple("선택한 메뉴와 금액을 다시 확인한 후", "포장 여부를 선택해주세요!", ""),
        Triple("결제 수단을 선택합니다.", "\"카드결제\"나 \"쿠폰 사용\"을 눌러주세요.", "\"현금 결제\"라면 카운터에서 주문해주세요."),
        Triple("카드결제를 선택했다면", "키오스크 아래의 카드 투입구에", "카드를 넣어주세요."),
        Triple("쿠폰 사용을 선택했다면", "키오스크 아래의 바코드 인식기에", "바코드를 읽혀주세요."),
        Triple("결제가 끝났습니다!", "영수증을 챙겨 번호를 확인해주세요!", ""),
        )

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        val (text1, text2, text3) = textList[currentImageIndex]
        TextWithColoredWords(
            text = text1, wordsToColor = mapOf(
                "사진" to Color.Blue, "화면" to Color.Red, "음료" to Color.Red,
                "메뉴" to Color.Blue, "금액" to Color.Blue, "카드결제" to Color.Red,
                "쿠폰 사용" to Color.Red
            )
        )
        TextWithColoredWords(
            text = text2, wordsToColor = mapOf(
                "광고" to Color.Blue, "메뉴" to Color.Blue, "갯수" to Color.Blue,
                "포장 여부" to Color.Red, "카드결제" to Color.Red, "쿠폰 사용" to Color.Red,
                "카드 투입구" to Color.Blue, "바코드 인식기" to Color.Blue
            )
        )
        TextWithColoredWords(
            text = text3, wordsToColor = mapOf(
                "결제" to Color.Red, "현금 결제" to Color.Blue, "카운터" to Color.Green
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
fun cafeGuideScreenPreview() {
    val navController = rememberNavController()
    var currentImageIndex by remember { mutableStateOf(0) }
    KioskCafeGuide0(navController)
}
