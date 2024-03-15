package com.example.capstoneapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.Frame.NotificationScreen

@Composable
fun KioskCafePractice5(navController: NavController) {
    NotificationScreen {
        Column {
            CafeMenuBarFormat {
                MenuText5()
            }
            Screen5(navController)
        }
    }
}

@Composable
fun Screen5(navController: NavController) {
    val navController = rememberNavController()
    val price = 2500
    val menusAndNums = ArrayList<Pair<String, Int>>()
    menusAndNums.add(Pair("ICE 아메리카노", 2))
    menusAndNums.add(Pair("카페라떼", 1))
    menusAndNums.add(Pair("카페모카", 1))

    Column {
        for (menuAndNum in menusAndNums) {
            val (menu, num) = menuAndNum
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = menu,
                    modifier = Modifier
                        .padding(start = 30.dp, top = 10.dp)
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Text(
                    text = num.toString(),
                    modifier = Modifier
                        .padding(end = 30.dp, top = 10.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("KioskCafePractice6")
                },
                modifier = Modifier.size(150.dp, 80.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFCA0D)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "먹고가기",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold

                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {},
                modifier = Modifier.size(150.dp, 80.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFB2929)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "포장하기",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }


    }
}

@Composable
fun MenuText5() {
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
fun Kiosk5PreView() {
    val navController = rememberNavController()
    KioskCafePractice5(navController)
}