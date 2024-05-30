package com.example.capstoneapp.fastfood.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import com.example.capstoneapp.fastfood.ui.components.OptionCard
import com.example.capstoneapp.fastfood.ui.frame.DividerFormat
import com.example.capstoneapp.fastfood.ui.frame.KioskButtonFormat
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.kakatalk.ui.Components.RepeatDialog
import com.example.capstoneapp.nav.repository.Problem

@SuppressLint("RememberReturnType")
@Composable
fun OrderScreen(
    navController: NavController,
    viewModel: OrderViewModel,
    showBorder: Boolean,
    problem: Problem
) {
    val orderItems by viewModel.orderItems.observeAsState(initial = listOf())
    val totalAmount = orderItems.sumOf { it.menuItem.price * it.quantity }
    val showDialog = remember { mutableStateOf(false) }

    var repeatAnswer by remember { mutableStateOf(false) }

    // 각 옵션의 선택 상태를 관리하는 상태 변수
    val selectedPackingOption = remember { mutableStateOf<OptionType?>(null) }
    val selectedDiscountOption = remember { mutableStateOf<OptionType?>(null) }
    val selectedPaymentOption = remember { mutableStateOf<OptionType?>(null) }

    // 모든 옵션이 선택되었는지 확인하는 상태 변수
    val allOptionsSelected = selectedPackingOption.value != null &&
            selectedDiscountOption.value != null &&
            selectedPaymentOption.value != null

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.padding(8.dp))
        Row {
            Column(modifier = Modifier
                .weight(1.1f)
                .padding(start = 8.dp)
                .padding(top = 16.dp)
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
                Spacer(modifier = Modifier.padding(4.dp))
                orderItems.forEach { orderItem ->
                    ItemRow(orderItem.menuItem.name, orderItem.quantity.toString(), "${orderItem.menuItem.price * orderItem.quantity}")
                }

                Spacer(modifier = Modifier.padding(16.dp))
                DividerFormat()
                Spacer(modifier = Modifier.padding(4.dp))

                Column {
                    SummaryRow(label = "주문금액", amount = totalAmount.toString())
                    SummaryRow(label = "할인금액", amount = "0")
                    Spacer(modifier = Modifier.padding(4.dp))
                    SummaryRow(label = "결제할금액", amount = totalAmount.toString(), isTotal = true)
                }

                PaymentPopup(
                    showDialog = showDialog.value,
                    onDismiss = { showDialog.value = false },
                    onConfirm = {
                        showDialog.value = false
                        navController.navigate("HamburgerHomeScreen")
                    },
                    navController, // 팝업을 닫을 때 showDialog 상태를 false로 설정
                    viewModel = viewModel
                )
            }

            // Right Section for selectable image buttons
            Column(modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
            ) {
                OrderText("포장을 선택하세요.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionCard(
                        onClick = {
                            if(problem.place!="포장 하기"){
                                repeatAnswer=true
                            }else{
                                selectedPackingOption.value = OptionType.Packing
                            }},
                        text = "포장",
                        icon = painterResource(id = R.drawable.bag),
                        isSelected = selectedPackingOption.value == OptionType.Packing,
                        showBorder = showBorder&&(problem.place=="포장 하기"),
                    )
                    OptionCard(
                        onClick = {
                            if(problem.place!="매장에서 먹기"){
                                repeatAnswer=true
                            }else{
                                selectedPackingOption.value = OptionType.Store
                            }},
                        text = "매장",
                        icon = painterResource(id = R.drawable.shop),
                        isSelected = selectedPackingOption.value == OptionType.Store,
                        showBorder = showBorder&&(problem.place=="매장에서 먹기"),
                    )
                }

                OrderText("할인/적립을 선택하세요.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionCard(
                        onClick = {
                            if(problem.point!="O"){
                                repeatAnswer=true
                            }else{
                                selectedDiscountOption.value = OptionType.Point
                            }},
                        text = "포인트",
                        icon = painterResource(id = R.drawable.discount),
                        isSelected = selectedDiscountOption.value == OptionType.Point,
                        showBorder = showBorder&&(problem.point=="O"),
                    )
                    OptionCard(
                        onClick = {
                            if(problem.point!="X"){
                                repeatAnswer=true
                            }else{
                                selectedDiscountOption.value = OptionType.None
                            }},
                        text = "선택\n없음",
                        icon = painterResource(id = R.drawable.x),
                        isSelected = selectedDiscountOption.value == OptionType.None,
                        showBorder = showBorder&&(problem.point=="X"),
                    )
                }

                OrderText("결제를 선택하세요.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionCard(
                        onClick = {
                            if(problem.pay!="카드 결제"){
                                repeatAnswer=true
                            }else{
                                selectedPaymentOption.value = OptionType.CreditCard
                            }},
                        text = "신용\n/체크카드",
                        icon = painterResource(id = R.drawable.cardicon),
                        isSelected = selectedPaymentOption.value == OptionType.CreditCard,
                        showBorder = showBorder&&(problem.pay=="카드 결제"),
                    )
                    OptionCard(
                        onClick = {
                            if(problem.pay!="모바일 페이"){
                                repeatAnswer=true
                            }else{
                                selectedPaymentOption.value = OptionType.MobilePay
                            }},
                        text = "모바일\n/페이",
                        icon = painterResource(id = R.drawable.pay),
                        isSelected = selectedPaymentOption.value == OptionType.MobilePay,
                        showBorder = showBorder&&(problem.pay.split(" ").contains("모바일")||problem.pay.split(" ").contains("페이")),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))

        // 결제 버튼
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp), // Apply horizontal padding
            horizontalArrangement = Arrangement.SpaceBetween // Arrange buttons with space in between
        ) {
            KioskButtonFormat(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp),
                onClick = {
                    if (allOptionsSelected) {
                        showDialog.value = true
                    }
                },
                buttonText = "결제하기",
                backgroundColor = if (allOptionsSelected) Color.Red else Color.Gray,
                contentColor = Color.Black,
                enabled = allOptionsSelected
            )
        }
    }
    if(repeatAnswer){
        RepeatDialog(onDismiss = {
            repeatAnswer = false })
    }
}

enum class OptionType {
    Packing, Store, Point, None, CreditCard, MobilePay
}

@Composable
fun ItemRow(name: String, quantity: String, price: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
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
            .padding(vertical = 4.dp),
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

@Composable
fun OptionCard(
    onClick: () -> Unit,
    text: String,
    icon: Painter,
    isSelected: Boolean,
    showBorder: Boolean
) {
    val backgroundColor = if (isSelected) Color.LightGray else Color.Transparent
    Column(
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
                .then(if (showBorder) Modifier.border(BorderWidth, BorderColor, BorderShape) else Modifier),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = icon, contentDescription = null, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = text, textAlign = TextAlign.Center)
            }
        }
    }
}

//@Preview(showBackground = true, name = "Order Screen Preview")
//@Composable
//fun OrderScreenPreview() {
//    // NavController의 모의 인스턴스를 생성합니다.
//    val navController = rememberNavController()
//
//    // 미리보기를 위한 OrderViewModel의 인스턴스를 생성합니다.
//    // 실제로는 PreviewOrderViewModel이나 적절한 모의 객체를 제공해야 합니다.
//    val viewModel = OrderViewModel()
//
//    // OrderScreen 컴포저블을 호출하고 미리보기에 필요한 인자를 전달합니다.
//    // 실제 앱에서는 이 인자들이 상위에서 제공될 것입니다.
//    OrderScreen(
//        navController = navController,
//        viewModel = viewModel,
//        showBorder = true // 또는 미리보기에 맞는 값으로 설정합니다.
//    )
//}