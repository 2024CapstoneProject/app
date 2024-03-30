package com.example.capstoneapp.fastfood.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.fastfood.data.model.OrderViewModel
import com.example.capstoneapp.fastfood.data.model.PreviewOrderViewModel
import com.example.capstoneapp.fastfood.ui.components.OptionCard
import com.example.capstoneapp.fastfood.ui.frame.DividerFormat
import com.example.capstoneapp.fastfood.ui.frame.KioskButtonFormat

@SuppressLint("RememberReturnType")
@Composable
fun OrderScreen(
    navController: NavController,
    viewModel: OrderViewModel,
    showBorder: Boolean
) {
    val orderItems by viewModel.orderItems.observeAsState(initial = listOf())
    val totalAmount by viewModel.totalOrderAmount.observeAsState(0)
    val showDialog = remember { mutableStateOf(false) }

    Column(modifier=Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(20.dp))

        Row {
            // Left Section (Placeholder for other content)
            Column(modifier = Modifier
                .weight(1.1f)
                .padding(start = 16.dp)
                .padding(top = 32.dp)
            ) {
                // Header
                Row {
                    Text("제품", modifier = Modifier.weight(1.7f))
                    Text("수량", modifier = Modifier.weight(0.8f)
                        .align(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                    Text("금액", modifier = Modifier.weight(1f)
                        .align(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.End)
                    )
                }
                // Rows
                Spacer(modifier = Modifier.padding(8.dp))
                orderItems.forEach { orderItem ->
                    ItemRow(orderItem.menuItem.name,orderItem.quantity.toString(),"${orderItem.menuItem.price * orderItem.quantity}")
                }
               // ItemRow(orderItem.menuItem.name, "1", "6,300")
               //  ItemRow("콜라", "1", "0")
               // ItemRow("포테이토", "1", "0")

                Spacer(modifier = Modifier.padding(32.dp))
                DividerFormat()
                Spacer(modifier = Modifier.padding(8.dp))

                Column {
                    // Individual Row for each item in the table
                    SummaryRow(label = "주문금액", amount = totalAmount.toString())
                    SummaryRow(label = "할인금액", amount = "0")
                    Spacer(modifier = Modifier.padding(8.dp))
                    SummaryRow(label = "결제할금액", amount = totalAmount.toString(), isTotal = true)
                }

                PaymentPopup(
                    showDialog = showDialog.value,
                    onDismiss = { showDialog.value = false } // 팝업을 닫을 때 showDialog 상태를 false로 설정
                )
            }

            // Right Section for selectable image buttons
            Column(modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp)
            ) {
                OrderText("포장을 선택하세요.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    OptionCard(
                        onClick = {},
                        text = "포장",
                        icon = painterResource(id = R.drawable.bag),
                        showBorder = false
                    )
                    OptionCard(
                        onClick = {},
                        text = "매장",
                        icon = painterResource(id = R.drawable.shop),
                        showBorder = showBorder
                    )
                }

                OrderText("할인/적립을 선택하세요.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionCard(
                        onClick = {},
                        text = "포인트",
                        icon = painterResource(id = R.drawable.discount),
                        showBorder = false
                    )
                    OptionCard(
                        onClick = {},
                        text = "선택\n없음",
                        icon = painterResource(id = R.drawable.x),
                        showBorder = showBorder
                    )
                }

                OrderText("결제를 선택하세요.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionCard(
                        onClick = {},
                        text = "신용\n/체크카드",
                        icon = painterResource(id = R.drawable.cardicon),
                        showBorder = showBorder
                    )
                    OptionCard(
                        onClick = {},
                        text = "모바일\n/페이",
                        icon = painterResource(id = R.drawable.pay),
                        showBorder = false
                    )
                }
            }
        }

        // 결제 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp), // Apply horizontal padding
            horizontalArrangement = Arrangement.SpaceBetween // Arrange buttons with space in between
        ) {
            KioskButtonFormat(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp),
                onClick = { /* Handle click */ },
                buttonText = "취소하기",
                backgroundColor = Color.DarkGray,
                contentColor = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))

            KioskButtonFormat(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp),
                onClick = { showDialog.value = true },
                buttonText = "결제하기",
                backgroundColor = Color.Red,
                contentColor = Color.Black
            )
        }
    }
}

@Composable
fun ItemRow(name: String, quantity: String, price: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name, modifier = Modifier.weight(1.7f))
        Text(quantity, modifier = Modifier.weight(0.8f)
            .align(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
        )
        Text(price, modifier = Modifier.weight(1f)
            .align(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.End)
        )
    }
}

@Composable
fun SummaryRow(label: String, amount: String, isTotal: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            modifier = Modifier,
            color = Color.Black,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = amount,
            modifier = Modifier,
            color = if (isTotal) Color.Red else Color.Black,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun OrderText(optionText: String) {
    Text(
        text = optionText,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
@Preview(showBackground = true)
@Composable
fun PreviewOrderScreen() {
    // Mock data to be displayed in the preview, since we cannot use live data here.
    val mockViewModel = PreviewOrderViewModel()
    // No actual NavController functionality required for preview

    val navController = rememberNavController()
//    OrderScreen(navController = navController, mockViewModel)
}

// Ensure to use the correct package for R.drawable if you're referencing drawables
