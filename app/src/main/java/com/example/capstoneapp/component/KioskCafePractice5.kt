package com.example.capstoneapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.Frame.NotificationScreen

@Composable
fun KioskCafePractice5(){
    NotificationScreen {
        Column {
            CafeMenuBarFormat {
                MenuText5()
            }
            Screen5()
        }
    }
}
@Composable
fun Screen5(){
    val price = 2500
    val num = 1
    val menu = "ICE 아메리카노"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 0.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "$menu",
                modifier = Modifier
                    .padding(start = 30.dp, top = 50.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Text(
                text = "$num",
                modifier = Modifier
                    .padding(end = 30.dp, top = 52.dp)
                    .align(Alignment.CenterVertically),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(250.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "금액",
                modifier = Modifier
                    .padding(start = 30.dp, top = 50.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Text(
                text = "$price",
                modifier = Modifier
                    .padding(end = 5.dp, top = 50.dp)
                    .align(Alignment.CenterVertically),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            Text(
                text = "원",
                modifier = Modifier
                    .padding(end = 30.dp, top = 50.dp)
                    .align(Alignment.CenterVertically),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Row {

        }
    }
}
@Composable
fun MenuText5(){
    Text(
        text = "주문 세부내역 확인",
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
fun Kiosk5PreView(){
    KioskCafePractice5()
}