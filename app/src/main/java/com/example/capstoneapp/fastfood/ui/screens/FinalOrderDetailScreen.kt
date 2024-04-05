package com.example.capstoneapp.fastfood.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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

    // 각 옵션의 선택 상태를 관리하는 상태 변수
    val isPackingSelected = remember { mutableStateOf(false) }
    val isStoreSelected = remember { mutableStateOf(false) }
    val isPointSelected = remember { mutableStateOf(false) }
    val isNoneSelected = remember { mutableStateOf(false) }
    val isCreditCardSelected = remember { mutableStateOf(false) }
    val isMobilePaySelected = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.padding(16.dp))
        Row {
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

                Spacer(modifier = Modifier.padding(32.dp))
                DividerFormat()
                Spacer(modifier = Modifier.padding(8.dp))

                Column {
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
                        onClick = { isPackingSelected.value = true; isStoreSelected.value = false },
                        text = "포장",
                        icon = painterResource(id = R.drawable.bag),
                        showBorder = isPackingSelected.value
                    )
                    OptionCard(
                        onClick = { isStoreSelected.value = true; isPackingSelected.value = false },
                        text = "매장",
                        icon = painterResource(id = R.drawable.shop),
                        showBorder = isStoreSelected.value
                    )
                }

                OrderText("할인/적립을 선택하세요.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionCard(
                        onClick = { isPointSelected.value = true; isNoneSelected.value = false },
                        text = "포인트",
                        icon = painterResource(id = R.drawable.discount),
                        showBorder = isPointSelected.value
                    )
                    OptionCard(
                        onClick = { isPointSelected.value = false; isNoneSelected.value = true },
                        text = "선택\n없음",
                        icon = painterResource(id = R.drawable.x),
                        showBorder = isNoneSelected.value
                    )
                }

                OrderText("결제를 선택하세요.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionCard(
                        onClick = { isCreditCardSelected.value = true; isMobilePaySelected.value = false },
                        text = "신용\n/체크카드",
                        icon = painterResource(id = R.drawable.cardicon),
                        showBorder = isCreditCardSelected.value
                    )
                    OptionCard(
                        onClick = { isCreditCardSelected.value = false; isMobilePaySelected.value = true },
                        text = "모바일\n/페이",
                        icon = painterResource(id = R.drawable.pay),
                        showBorder = isMobilePaySelected.value
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(70.dp))

        // 결제 버튼
        Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Apply horizontal padding
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

@Preview(showBackground = true, name = "Order Screen Preview")
@Composable
fun OrderScreenPreview() {
    // NavController의 모의 인스턴스를 생성합니다.
    val navController = rememberNavController()

    // 미리보기를 위한 OrderViewModel의 인스턴스를 생성합니다.
    // 실제로는 PreviewOrderViewModel이나 적절한 모의 객체를 제공해야 합니다.
    val viewModel = OrderViewModel()

    // OrderScreen 컴포저블을 호출하고 미리보기에 필요한 인자를 전달합니다.
    // 실제 앱에서는 이 인자들이 상위에서 제공될 것입니다.
    OrderScreen(
        navController = navController,
        viewModel = viewModel,
        showBorder = true // 또는 미리보기에 맞는 값으로 설정합니다.
    )
}