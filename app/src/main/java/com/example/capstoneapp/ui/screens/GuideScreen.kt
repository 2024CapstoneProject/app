package com.example.capstoneapp.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavController
import com.example.capstoneapp.R
import com.example.capstoneapp.ui.theme.CapstoneAppTheme

@Composable
fun GuideImage(currentImageIndex: Int, onImageIndexChanged: (Int) -> Unit) {
    val imageResources = listOf(
        R.drawable.touch,
        R.drawable.payment,
        R.drawable.burger,
        R.drawable.set,
        R.drawable.dessert,
        R.drawable.drink,
        R.drawable.receipt,
        R.drawable.card
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
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onImageIndexChanged(showPreviousImage(imageResources.size, currentImageIndex)) }
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.arrow_back),
                        contentDescription = "Previous"
                    )
                }
                Image(
                    painter = painterResource(id = imageResources[currentImageIndex]),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth()
                        .aspectRatio(0.6f)
                        .clip(RoundedCornerShape(20.dp))
                )
                IconButton(
                    onClick = { onImageIndexChanged(showNextImage(imageResources.size, currentImageIndex)) }
                ) {
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
        R.drawable.touch -> context.getString(R.string.touch_text)
        R.drawable.payment -> context.getString(R.string.payment_text)
        R.drawable.burger -> context.getString(R.string.burger_text)
        R.drawable.set -> context.getString(R.string.set_text)
        R.drawable.dessert -> context.getString(R.string.dessert_text)
        R.drawable.drink -> context.getString(R.string.drink_text)
        R.drawable.receipt -> context.getString(R.string.receipt_text)
        R.drawable.card -> context.getString(R.string.card_text)
        else -> "Unknown"
    }
}

fun showNextImage(size: Int, currentIndex: Int): Int {
    return (currentIndex + 1) % size
}

fun showPreviousImage(size: Int, currentIndex: Int): Int {
    return if (currentIndex == 0) size - 1 else currentIndex - 1
}

@Composable
fun guideText(currentImageIndex: Int) {
    val textList = listOf(
        Triple("사진을 옆으로 넘기거나", "화살표를 눌러 확인해주세요.", "사진을 누르면 확대됩니다."),
        Triple("카드, 디지털 교환권, 현금 중", "원하시는 결제 방법을", "선택해주세요."),
        Triple("원하시는 햄버거 메뉴를 골라주세요.", "선택된 메뉴는", "하단 목록에 표시됩니다."),
        Triple("버거 단품 또는", "음료와 디저트가 있는 세트 메뉴를", "선택하실 수 있습니다."),
        Triple("세트 구성에 포함된", "세트 디저트를 선택해주세요.", "일부 메뉴는 추가 금액이 있습니다."),
        Triple("세트 구성에 포함된", "세트 드링크를 선택해주세요.", "일부 메뉴는 추가 금액이 있습니다."),
        Triple("좌측엔 선택하신 메뉴 목록이,", "우측에는 포장/매장, 할인과 적립,", "결제 방법을 선택할 수 있습니다."),
        Triple("해당 그림이 나타나면", "키오스크 하단에 있는 카드 투입구에", "카드를 넣어 주세요.")
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ){
        val (text1, text2, text3) = textList[currentImageIndex]
        TextWithColoredWords(
            text = text1,
            wordsToColor = mapOf(
                "현금 결제" to Color.Green,
                "카드" to Color.Blue,
                "파란색" to Color.Blue
            )
        )
        TextWithColoredWords(
            text = text2,
            wordsToColor = mapOf(
                "쿠폰" to Color.Green,
                "초록색" to Color.Green,
                "화면 오른쪽 아래" to Color.Red
            )
        )
        TextWithColoredWords(
            text = text3,
            wordsToColor = mapOf(
                "카운터" to Color.Green
            )
        )
    }
}

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
            .padding(0.dp),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun CafeGuideScreenPreview(navController: NavController) {
    CapstoneAppTheme {
        var currentImageIndex by remember { mutableIntStateOf(0) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            GuideImage(currentImageIndex) { newIndex ->
                currentImageIndex = newIndex
            }
            Spacer(modifier = Modifier.height(16.dp))
            guideText(currentImageIndex)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CafeGuideScreenPreviews() {
    CapstoneAppTheme {
        var currentImageIndex by remember { mutableIntStateOf(0) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            GuideImage(currentImageIndex) { newIndex ->
                currentImageIndex = newIndex
            }
            Spacer(modifier = Modifier.height(16.dp))
            guideText(currentImageIndex)
        }
    }
}

