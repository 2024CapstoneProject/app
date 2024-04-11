package com.example.capstoneapp.fastfood.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.fastfood.data.model.OrderViewModel
import com.example.capstoneapp.nav.repository.MenuItem
import com.example.capstoneapp.fastfood.ui.components.CustomizedNavigationBar
import com.example.capstoneapp.fastfood.ui.frame.DividerFormat
import com.example.capstoneapp.fastfood.ui.components.ItemList
import com.example.capstoneapp.fastfood.ui.frame.KioskButtonFormat
import com.example.capstoneapp.fastfood.ui.components.OrderList
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth

@Composable
fun ItemMenu(
    navController: NavController,
    viewModel: OrderViewModel,
    showBorder: Boolean
) {
    // 주문한 목록
    val orderItems = remember { mutableStateListOf<MenuItem>() }
    var showDialog by remember { mutableStateOf(false) }
    var currentItemForDialog by remember { mutableStateOf<MenuItem?>(null) }
    var showDessertScreen by remember { mutableStateOf(false) }

    val onButtonClick = {
        if(showDessertScreen)
            showDessertScreen = false
        else {
            navController.navigate("finalOrder")
        }
    }

    //네비게이션 카테고리 선택
    var selectedMenu by remember { mutableStateOf("햄버거") } // 초기값 설정
    val myMenuItems = listOf("추천메뉴", "햄버거", "디저트/치킨", "음료/커피")
    val buttonText = if (showDessertScreen) "선택완료" else "결제하기"
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        // 메뉴 목록 표
        if (!showDessertScreen) {
            Box(
                modifier = Modifier
                    .weight(1.8f) // 화면의 절반을 차지
                    .fillMaxWidth()
            ) {
                Column {
                    CustomizedNavigationBar(
                        menuItems = myMenuItems,
                        selectedMenuItem = selectedMenu,
                        onMenuItemClick = { menuItem ->
                            if (menuItem == "햄버거") { // "햄버거" 메뉴만 선택 가능
                                selectedMenu = menuItem
                            }
                        }
                    )
                    ItemList(selectedMenu = selectedMenu) { selectedItem ->
                        if (selectedMenu == "햄버거") { // "햄버거" 메뉴가 선택되었을 때만 클릭 이벤트 활성화
                            currentItemForDialog = selectedItem
                            showDialog = true // 아이템 클릭 시 팝업 표시
                        }
                    }
                }
            }


            if (showDialog) {
                SetOrSingleChoicePopup(
                    showDialog = showDialog,
                    currentItem = currentItemForDialog,
                    onDismiss = { showDialog = false },
                    onAddToOrder = { item ->
                        orderItems.add(item)
                        viewModel.addMenuItem(item, 1)
                        showDialog = false
                        showDessertScreen = item.id % 2 == 0
                    },
                )
            }
        } else {
            SelectSetDessertScreen(
                onItemSelected = { selectedItem ->
                    orderItems.add(selectedItem)
                    viewModel.addMenuItem(selectedItem, 1)
                }
            )
        }

        Box(
            modifier = Modifier
                .weight(1f) // 화면의 절반을 차지
                .fillMaxSize()
        ) {
            DividerFormat()
            OrderList(orderItems = orderItems)
        }
        // 주문 목록 표시

        // 결제 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Apply horizontal padding
            horizontalArrangement = Arrangement.SpaceBetween // Arrange buttons with space in between
        ) { KioskButtonFormat(
                modifier = Modifier.weight(1f),
                onClick = { /* Handle click */ },
                buttonText = "취소하기",
                backgroundColor = Color.DarkGray,
                contentColor = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp)) // Space between buttons

            KioskButtonFormat(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (showBorder) Modifier.border(BorderWidth, BorderColor)
                        else Modifier
                    ),
                onClick = onButtonClick,
                buttonText = buttonText,
                backgroundColor = Color.Red,
                contentColor = Color.Black
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ItemMenuPreview() {
    val navController = rememberNavController()
    val viewModel = OrderViewModel()

    ItemMenu(
        navController = navController,
        viewModel = viewModel,
        showBorder = true // 또는 미리보기에 맞는 값으로 설정합니다.
    )
}
