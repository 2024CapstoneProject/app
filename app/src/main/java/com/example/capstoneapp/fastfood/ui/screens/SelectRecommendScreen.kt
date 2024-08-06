package com.example.capstoneapp.fastfood.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.capstoneapp.fastfood.data.model.OrderViewModel
import com.example.capstoneapp.fastfood.ui.components.CustomizedNavigationBar
import com.example.capstoneapp.fastfood.ui.components.ItemList
import com.example.capstoneapp.fastfood.ui.components.OrderList
import com.example.capstoneapp.fastfood.ui.frame.DividerFormat
import com.example.capstoneapp.fastfood.ui.frame.KioskButtonFormat
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.kakatalk.ui.Components.RepeatDialog
import com.example.capstoneapp.nav.repository.Problem

@Composable
fun RecommendScreen(
    navController: NavController,
    viewModel: OrderViewModel = viewModel(),
    showBorder: Boolean,
    problem: Problem
) {
    var repeatAnswer by remember { mutableStateOf(false) }
    val orderItems by viewModel.orderItems.observeAsState(emptyList())

    val onButtonClick = {
        navController.navigate("finalOrder")
    }

    // 뒤로가기 처리
    BackHandler {
        viewModel.clearOrderItems()
        navController.navigate("HamburgerHomeScreen") {
            popUpTo("HamburgerHomeScreen") { inclusive = true }
        }
    }

    var selectedMenu by remember { mutableStateOf("추천메뉴") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        // 메뉴 목록 표
        Box(
            modifier = Modifier
                .weight(1.8f) // 화면의 절반을 차지
                .fillMaxWidth()
        ) {
            Column {
                CustomizedNavigationBar(
                    menuItems = listOf("추천메뉴", "햄버거", "디저트/치킨", "음료/커피"),
                    selectedMenuItem = selectedMenu,
                    onMenuItemClick = { menuItem ->
                        if (menuItem == "추천메뉴" || menuItem == "햄버거" || menuItem == "디저트/치킨" || menuItem == "음료/커피") {
                            selectedMenu = menuItem
                            if (menuItem == "햄버거") {
                                navController.navigate("itemMenu")
                            }
                            if (menuItem == "디저트/치킨") {
                                navController.navigate("dessertChicken")
                            }
                            if (menuItem == "음료/커피") {
                                navController.navigate("drinkCoffee")
                            }
                        }
                    }
                )
                ItemList(
                    selectedMenu = selectedMenu,
                    selectedItem = null,
                    showBorder = showBorder,
                    problem = problem,
                    onItemClicked = { selectedItem ->
                        if (selectedMenu == "추천메뉴") { // "디저트/치킨" 메뉴가 선택되었을 때
                            if (!problem.menu.split(",").contains(selectedItem.name)) {
                                repeatAnswer = true
                            } else {
                                viewModel.addMenuItem(selectedItem, 1)
                            }
                        }
                    }
                )
            }
        }
        if (repeatAnswer) {
            RepeatDialog(onDismiss = {
                repeatAnswer = false })
        }

        Box(
            modifier = Modifier
                .weight(1f) // 화면의 절반을 차지
                .fillMaxSize()
        ) {
            DividerFormat()
            OrderList(orderItems = orderItems)
        }

        // 결제 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Apply horizontal padding
            horizontalArrangement = Arrangement.SpaceBetween // Arrange buttons with space in between
        ) {
            KioskButtonFormat(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (showBorder) Modifier.border(BorderWidth, BorderColor)
                        else Modifier
                    ),
                onClick = onButtonClick,
                buttonText = "결제하기",
                backgroundColor = Color.Red,
                contentColor = Color.Black,
                enabled = orderItems.isNotEmpty() // orderItems가 비어 있으면 버튼 비활성화
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
}