package com.example.capstoneapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable


import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.font.FontWeight
import com.example.capstoneapp.data.repository.MenuItem
import com.example.capstoneapp.ui.theme.fontFamily

@Composable
fun OrderList(orderItems: List<MenuItem>) {
    Column(modifier = Modifier.padding(0.dp)) {
        // 총 주문 내역, 총 개수, 총 가격을 한 줄에 표시
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "총 주문 내역", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))

            // 총 주문 개수와 총 가격 계산
            val totalItems = orderItems.size
            val totalPrice = orderItems.sumOf { it.price }

            // 총 개수와 총 가격을 함께 표시
            Text(
                text = "$totalItems 개 $totalPrice 원",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = fontFamily,
                fontSize = 10.sp,
            )
        }

        // 구분선
        Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.padding(vertical = 0.dp))
        Spacer(modifier = Modifier.height(6.dp))
        // 개별 주문 내역을 아래에 표시
        orderItems.forEach { item ->
            Text(
                text = "${item.name} ${item.price}원",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = fontFamily,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}