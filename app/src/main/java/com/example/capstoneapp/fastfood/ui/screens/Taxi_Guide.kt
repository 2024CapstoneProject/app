package com.example.capstoneapp.ui.Screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.ui.Screens.EnlargedImagePopup
import com.example.capstoneapp.fastfood.ui.theme.CapstoneAppTheme

@Composable
fun taxiGuideImage(currentImageIndex: Int, onImageIndexChanged: (Int) -> Unit) {
    val imageResources = listOf(
        R.drawable.taxi_guide_000,
        R.drawable.taxi_guide_001,
        R.drawable.taxi_guide_002,
        R.drawable.taxi_guide_003,
        R.drawable.taxi_guide_004,
        R.drawable.taxi_guide_005,
        R.drawable.taxi_guide_006,
        R.drawable.taxi_guide_007,
        R.drawable.taxi_guide_008,
        R.drawable.taxi_guide_009,
        R.drawable.taxi_guide_010,
        R.drawable.taxi_guide_011,
        R.drawable.taxi_guide_012,
        R.drawable.taxi_guide_013,
        R.drawable.taxi_guide_014,
        R.drawable.taxi_guide_015,
        R.drawable.taxi_guide_016,
        R.drawable.taxi_guide_017
    )
    val currentImageResourceId = imageResources[currentImageIndex]
    val context = LocalContext.current
    val imageName = taxiGetResourceName(currentImageResourceId, context)

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
                IconButton(
                    onClick = { onImageIndexChanged(taxiShowPreviousImage(imageResources.size, currentImageIndex)) }
                ) {
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

                IconButton(
                    onClick = { onImageIndexChanged(taxiShowNextImage(imageResources.size, currentImageIndex)) }
                ) {
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
fun taxiGetResourceName(resourceId: Int, context: Context): String {
    return when (resourceId) {
        R.drawable.taxi_guide_000 -> context.getString(R.string.taxi_guide_000_text)
        R.drawable.taxi_guide_001 -> context.getString(R.string.taxi_guide_001_text)
        R.drawable.taxi_guide_002 -> context.getString(R.string.taxi_guide_002_text)
        R.drawable.taxi_guide_003 -> context.getString(R.string.taxi_guide_003_text)
        R.drawable.taxi_guide_004 -> context.getString(R.string.taxi_guide_004_text)
        R.drawable.taxi_guide_005 -> context.getString(R.string.taxi_guide_005_text)
        R.drawable.taxi_guide_006 -> context.getString(R.string.taxi_guide_006_text)
        R.drawable.taxi_guide_007 -> context.getString(R.string.taxi_guide_007_text)
        R.drawable.taxi_guide_008 -> context.getString(R.string.taxi_guide_008_text)
        R.drawable.taxi_guide_009 -> context.getString(R.string.taxi_guide_009_text)
        R.drawable.taxi_guide_010 -> context.getString(R.string.taxi_guide_010_text)
        R.drawable.taxi_guide_011 -> context.getString(R.string.taxi_guide_011_text)
        R.drawable.taxi_guide_012 -> context.getString(R.string.taxi_guide_012_text)
        R.drawable.taxi_guide_013 -> context.getString(R.string.taxi_guide_013_text)
        R.drawable.taxi_guide_014 -> context.getString(R.string.taxi_guide_014_text)
        R.drawable.taxi_guide_015 -> context.getString(R.string.taxi_guide_015_text)
        R.drawable.taxi_guide_016 -> context.getString(R.string.taxi_guide_016_text)
        R.drawable.taxi_guide_017 -> context.getString(R.string.taxi_guide_017_text)
        else -> "Unknown"
    }
}

fun taxiShowNextImage(size: Int, currentIndex: Int): Int {
    return (currentIndex + 1) % size
}

fun taxiShowPreviousImage(size: Int, currentIndex: Int): Int {
    return if (currentIndex == 0) size - 1 else currentIndex - 1
}

@Composable
fun taxiGuideText(currentImageIndex: Int) {
    val textList = listOf(
        Triple("사진을 옆으로 넘기거나", "화살표를 눌러 확인해주세요.", "사진을 누르면 확대됩니다."),
        Triple("카카오택시 로그인화면 입니다.", "카카오계정으로 시작하기를", "눌러주세요."),
        Triple("위치 허용 화면 입니다.", "앱을 사용하는 동안 허용을", "눌러주세요."),
        Triple("이 창은 광고입니다.", "다시 보지 않기나", "닫기를 눌러주세요."),
        Triple("이 창은 가이드 화면입니다.", "화면 아무 곳이나", "눌러주세요."),
        Triple("이용할 것을 선택할 수 있습니다.", "택시 사진을", "눌러주세요."),
        Triple("이 창에서는 도착지를 정합니다.", "어디로 갈까요?를", "눌러주세요."),
        Triple("이 창은 도착지를 검색합니다.", "도착지 검색을", "눌러주세요."),
        Triple("이 창은 도착지를 입력합니다.", "주소나 건물 이름을", "입력하세요."),
        Triple("이 창은 도착지를 결정합니다.", "주소를 보고 원하는 도착지를", "눌러주세요."),
        Triple("이 창은 출발 위치를 정합니다.", "화면을 누르고 지도를 움직여", "출발 위치를 설정합니다."),
        Triple("이 창은 택시 종류를 결정합니다.", "원하는 택시 종류를", "눌러주세요."),
        Triple("이 창은 결제수단을 선택합니다.", "결제수단 선택을", "눌러주세요."),
        Triple("이 창은 직접결제로 결제를 합니다.", "직접결제를 선택하고", "적용하기를 눌러주세요."),
        Triple("이 창은 호출하기 전 확인하는 창입니다.", "직접결제로 변경된 후에", "호출하기를 눌러주세요."),
        Triple("이 창은 택시가 호출되는 중입니다.", "호출이 될 때까지 기다려주세요.", "택시가 잡히는 중입니다."),
        Triple("이 버튼은 호출을 취소하는 버튼입니다.", "호출을 취소하고 싶다면", "호출취소를 눌러주세요."),
        Triple("이 창은 취소하기 전 확인하는 창입니다.", "정말로 호출 취소를 원한다면", "호출취소를 눌러주세요.")
    )

    Column (
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ){
        val (text1, text2, text3) = textList[currentImageIndex]
        taxiTextWithColoredWords(
            text = text1,
            wordsToColor = mapOf(
                "로그인화면" to Color.Green,
                "위치 허용 화면" to Color.Green,
                "출발 위치" to Color.Green,
                "택시 종류" to Color.Green,
                "결제수단" to Color.Green,
                "직접결제" to Color.Green,
                "확인하는" to Color.Green,
                "호출되는" to Color.Green,
                "취소하는" to Color.Green
            )
        )
        taxiTextWithColoredWords(
            text = text2,
            wordsToColor = mapOf(
                "카카오계정으로 시작하기" to Color.Red,
                "앱을 사용하는 동안 허용" to Color.Red,
                "다시 보지 않기" to Color.Red,
                "화면 아무 곳" to Color.Red,
                "택시 사진" to Color.Red,
                "어디로 갈까요?" to Color.Red,
                "도착지 검색" to Color.Red,
                "주소나 건물 이름" to Color.Red,
                "원하는 도착지" to Color.Red,
                "화면을 누르고 지도를 움직여" to Color.Red,
                "원하는 택시 종류" to Color.Red,
                "결제수단 선택" to Color.Red,
                "직접결제" to Color.Red,
                "변경된 후" to Color.Red,
                "호출을 취소" to Color.Red
                )
        )
        taxiTextWithColoredWords(
            text = text3,
            wordsToColor = mapOf(
                "닫기" to Color.Red,
                "입력" to Color.Red,
                "적용하기" to Color.Red,
                "호출하기" to Color.Red,
                "호출취소" to Color.Red
                )
        )
    }
}

@Composable
fun taxiTextWithColoredWords(text: String, wordsToColor: Map<String, Color>) {
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
fun taxiGuideScreenPreview() {
    val navController = rememberNavController()
    Taxi_Guide(navController)
}
@Composable
fun Taxi_Guide(navController: NavController){
    CapstoneAppTheme {
        var currentImageIndex by remember { mutableStateOf(0) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            taxiGuideImage(currentImageIndex) { newIndex ->
                currentImageIndex = newIndex
            }
            Spacer(modifier = Modifier.height(16.dp))
            taxiGuideText(currentImageIndex)
        }
    }
}