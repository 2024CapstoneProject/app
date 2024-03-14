package com.example.capstoneapp.component

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.ui.theme.CapstoneAppTheme
import com.example.capstoneapp.Frame.TopAppBar
import com.example.capstoneapp.R

@Composable
fun KioskCafePractice0(navController:NavController) {
        Column {
            TopAppBar()
            TextScreen(navController)
        }

}

@Composable
fun TextScreen(navController:NavController) {
    val menu ="아이스아메리카노"
    val place = "매장에서 먹기"
    val point = "O"
    val pay = "카드 결제"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 60.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp), // 수직 방향으로 요소를 동일한 간격으로 배치합니다.
    ) {
        Text(
            text = "메뉴 : $menu",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "장소 : $place",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "포인트 적립 여부 : $point",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "결제 방식 : $pay",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Image(
            painter = painterResource(R.drawable.cafe_icon),
            contentDescription = "cafe image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 50.dp),
            alignment = Alignment.Center
        )
        Text(
            text = "화면에서 정답인 버튼을",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "눌러주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        StartButton(onClick =  {
            navController.navigate("CafeKioskScreen")
        })
    }

}

@Preview(showBackground = true)
@Composable
fun TextScreenPreview() {
    val navController = rememberNavController()
    CapstoneAppTheme {
        Column {
            TopAppBar()
            TextScreen(navController)
        }
    }
}

@Composable
fun StartButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.size(260.dp, 100.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "시작하기",
                fontSize = 24.sp,
                color = Color.Black
                )
        }
    }
}
