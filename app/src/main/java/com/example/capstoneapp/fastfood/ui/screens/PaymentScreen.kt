package com.example.capstoneapp.fastfood.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.fastfood.ui.theme.fontFamily

@Composable
fun PaymentScreen(navController: NavController, showBorder: Boolean) {
    Column(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "원하시는 결제방법을 선택해주세요",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
            ),
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable {
                    navController.navigate("itemMenu") // Navigate to PaymentScreen
                }
                .then(if (showBorder) Modifier.border(BorderWidth, BorderColor, BorderShape) else Modifier),
            horizontalArrangement = Arrangement.SpaceBetween, // Arrange buttons with space in between
            verticalAlignment = Alignment.CenterVertically


        ) {
            Icon(
                painter = painterResource(id = R.drawable.cardicon),
                contentDescription = null,

                modifier = Modifier
                    .width(120.dp) // 아이콘의 너비를 48dp로 설정
                    .height(120.dp)
                    .weight(1f)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "카드",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                    )
                )
                Text(
                    text = "신용/체크카드 \n 모바일 금액권 \n 간편결제(페이)",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(48.dp))

        Divider(
            color = Color.Gray, // 선의 색상 지정
            thickness = 2.dp, // 선의 두께 지정
            modifier = Modifier.padding(horizontal = 16.dp) // 좌우 패딩 적용
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Apply horizontal padding
            horizontalArrangement = Arrangement.SpaceBetween,// Arrange buttons with space in between
            verticalAlignment = Alignment.CenterVertically
        ) { Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f) //왼쪽에 padding을 주기 위해
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pay),
                    contentDescription = null,
                    modifier = Modifier
                        .width(80.dp) // 아이콘의 너비를 48dp로 설정
                        .height(80.dp)
                )
                Text(
                    text = "디지털쿠폰/교환권",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Box(
                modifier = Modifier
                    .width(2.dp) // 선의 너비
                    .height(180.dp) // 선의 높이
                    .background(color = Color.Gray) // 선의 색상
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f) //왼쪽에 padding을 주기 위해
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cash),
                    contentDescription = null,
                    modifier = Modifier
                        .width(80.dp) // 아이콘의 너비를 48dp로 설정
                        .height(80.dp)
                )

                Text(
                    text = "현금",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentPreview() {
    val navController = rememberNavController()
    PaymentScreen(navController = navController, showBorder = true)
}
