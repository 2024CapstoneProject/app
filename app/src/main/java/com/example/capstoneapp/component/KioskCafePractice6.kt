package com.example.capstoneapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.Frame.NotificationScreen


@Composable
fun KioskCafePractice6(){
    NotificationScreen {
        CafeKioskMainFormat({MenuText()}, {PayScreen()})
    }

}
@Composable
fun PayScreen(){
    val price = 2500
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
        //카드결제
        Box(
            modifier = Modifier
                .padding(60.dp)
                .size(width = 200.dp, height = 150.dp)
                .background(Color.Gray)
                .align(Alignment.CenterHorizontally)
        ){
            Text(
                text = "카드 결제",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        //쿠폰사용
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(width = 200.dp, height = 150.dp)
                .background(Color.Gray) //D9D9D9
                .align(Alignment.CenterHorizontally)
        ){
            Text(
                text = "쿠폰 사용",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
                )
        }
        Row {
            Text(
                text = "금액",
                modifier = Modifier
                    .absolutePadding(top = 70.dp, left = 30.dp),
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Text(
                text = "$price",
                modifier = Modifier
                    .absolutePadding(top = 72.dp, left = 160.dp),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            Text(
                text = "원",
                modifier = Modifier
                    .absolutePadding(top = 70.dp),
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

    }
}
@Composable
fun MenuText(){
    Text(
        text = "결제수단 선택",
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(left = 30.dp),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
    )
}
@Preview
@Composable
fun KioskPreView(){
    KioskCafePractice6()
}
