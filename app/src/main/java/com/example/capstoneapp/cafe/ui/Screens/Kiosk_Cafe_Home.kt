package com.example.capstoneapp.cafe.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R

@Composable
fun CafeHomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //가이드모드
        PictureButton {
            navController.navigate("KioskCafeGuide0")
        }
        //연습모드
        PracticeButton {
            navController.navigate("KioskCafePractice0")
        }
        //이미지(onclick 기능 필요없음)
        ImageButton {
        }
    }
}

@Composable
fun PictureButton(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
            modifier = Modifier
                .width(260.dp) // 너비 설정
                .height(130.dp) // 높이 설정
        ) {
            Text(
                text = "사진으로 설명보기",
                color = Color.Black, // 텍스트 색상을 검은색으로 설정
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun PracticeButton(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
            modifier = Modifier
                .width(260.dp) // 너비 설정
                .height(130.dp) // 높이 설정
                .clip(RectangleShape)
        ) {
            Text(
                text = "직접 연습해보기",
                color = Color.Black, // 텍스트 색상을 검은색으로 설정
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                fontSize = 24.sp

            )
        }
    }
}

@Composable
fun ImageButton(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.cafe_coffee_img),
            contentDescription = null, // 이미지에 대한 접근성 설명은 필요하지 않습니다
            modifier = Modifier
                .size(194.dp) // 이미지 크기 조정
                .clickable { onClick() } // 이미지를 클릭하면 지정된 동작을 실행합니다
        )
    }
}

@Preview
@Composable
fun cafeHomeScreenPreview() {
    val navController = rememberNavController()
    CafeHomeScreen(navController = (navController))
}