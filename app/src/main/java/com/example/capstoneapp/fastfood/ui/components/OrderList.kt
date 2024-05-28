package com.example.capstoneapp.fastfood.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.fastfood.data.model.OrderViewModel
import com.example.capstoneapp.nav.repository.MenuItem
import com.example.capstoneapp.nav.repository.OrderItem

@Composable
fun OrderList(orderItems: List<OrderItem>) {
    Column(modifier = Modifier.padding(16.dp)) {
        // 총 주문 내역, 총 개수, 총 가격을 한 줄에 표시
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "총 주문 내역", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))

            // 총 주문 개수와 총 가격 계산
            val totalItems = orderItems.sumOf { it.quantity }
            val totalPrice = orderItems.sumOf { it.menuItem.price * it.quantity }

            // 총 개수와 총 가격을 함께 표시
            Text(
                text = "$totalItems 개 $totalPrice 원",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Default,
                fontSize = 20.sp,
            )
        }

        // 구분선
        Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))

        // 개별 주문 내역을 스크롤 가능하게 표시
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(orderItems) { orderItem ->
                    OrderListItem(orderItem)
                }
            }
        }
    }
}

@Composable
fun OrderListItem(orderItem: OrderItem) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 각 주문 항목을 표시하는 UI를 여기에 작성합니다.
        // 예: Text(orderItem.menuItem.name)
        Text(
            text = "${orderItem.menuItem.name} ${orderItem.menuItem.price}원 x ${orderItem.quantity}",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = FontFamily.Default,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemMenu() {
    val navController = rememberNavController()
    val viewModel = OrderViewModel()
//  //  itemMenu(navController = navController, viewModel)
}