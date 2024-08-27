package com.example.capstoneapp.taxi.ui.screens.practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.capstoneapp.R
import com.example.capstoneapp.fastfood.ui.frame.ButtonFormat
import com.example.capstoneapp.fastfood.ui.theme.Brown
import com.example.capstoneapp.fastfood.ui.theme.White

@Composable
fun TaxiConfirm(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight(1f)
            .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        TaxiConfirmScreen(navController)
    }
}


@Composable
fun TaxiConfirmScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Image(
            painter = painterResource(id = R.drawable.taxi_map),
            contentDescription = "temp_way_map",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(40.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "경기대학교",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = ">",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "수원역",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            TaxiOptionDetail(navController)
        }
    }
}

@Composable
fun TaxiOptionDetail(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.taxi_car),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "빠른 택시",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "주변에 가까운 택시 호출",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "결제수단",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .clickable {
                    navController.navigate("TaxiPay")
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "쿠폰", style = TextStyle(fontSize = 14.sp))
        Text(text = "포인트 OP", style = TextStyle(fontSize = 14.sp))
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navController.navigate(" INSERT NAV HERE ") }, // TODO: Nav 연결
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "본인탑승 >",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
        Text(
            text = "예상 7,300원",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
//    Button(
//        onClick = {
//            navController.navigate("TaxiRequest")
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 16.dp, end = 16.dp)
//            .padding(top = 16.dp)
//            .height(50.dp)
//    ) {
//        Text("호출하기")
//    }
    ButtonFormat(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        onClick = {navController.navigate("TaxiRequest") },
        buttonText = "호출하기",
        backgroundColor = White,
        contentColor = Brown,
        showShadow = true
    )


    Spacer(modifier = Modifier.height(16.dp))
}