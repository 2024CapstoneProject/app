package com.example.capstoneapp.fastfood.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.capstoneapp.R
import com.example.capstoneapp.fastfood.data.model.OrderViewModel
import com.example.capstoneapp.nav.repository.MenuItem
import com.example.capstoneapp.nav.repository.OrderItem
import com.example.capstoneapp.fastfood.ui.components.CustomizedNavigationBar
import com.example.capstoneapp.fastfood.ui.frame.DividerFormat
import com.example.capstoneapp.fastfood.ui.components.ItemList
import com.example.capstoneapp.fastfood.ui.frame.KioskButtonFormat
import com.example.capstoneapp.fastfood.ui.components.OrderList
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth

@Composable
fun itemMenu(
    navController: NavController,
    viewModel: OrderViewModel,
    showBorder: Boolean
) {
    // 주문한 목록
    val orderItems = remember { mutableStateListOf<MenuItem>() }
    var showDialog by remember { mutableStateOf(false) }
    var currentItemForDialog by remember { mutableStateOf<MenuItem?>(null) }
    var showDessertScreen by remember { mutableStateOf(false) }
    //테스트용 메뉴 선택 더미 데이터
    val dummyOrderItems = listOf(
        MenuItem(1,"불고기 버거", R.drawable.baseline_adb_24, 7000)
    )
    val onButtonClick = {
        if(showDessertScreen)
            showDessertScreen = false
        else
        {
            navController.navigate("finalOrder")
            // Additional logic here if needed, such as navigating back or handling the action
            //nav -> finalOrderDetailScreen
        }

    }

    //네비게이션 카테고리 선택
    var selectedMenu by remember { mutableStateOf("햄버거") } // 초기값 설정
    val myMenuItems = listOf("추천메뉴", "햄버거", "디저트/치킨", "음료/커피")
    val buttonText = if (showDessertScreen) "선택완료" else "결제하기"
    Column(
        modifier = Modifier.padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //네비게이션 bar (추천 메뉴, 햄버거 ...)

        
        // 메뉴 목록 표
        if (!showDessertScreen) {
            Box {
                CustomizedNavigationBar(
                    menuItems = myMenuItems,
                    selectedMenuItem = selectedMenu,
                    onMenuItemClick = { menuItem ->
                        selectedMenu = menuItem // 메뉴 항목 클릭 시 선택된 메뉴 업데이트
                    }
                )
                DividerFormat(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }
            // Display the default content
            // 메뉴 목록 표시
            ItemList(selectedMenu = selectedMenu) { selectedItem ->
                currentItemForDialog = selectedItem
                showDialog = true // 아이템 클릭 시 팝업 표시
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
            // Display the SelectSetDessertScreen content
            SelectSetDessertScreen(
                onItemSelected = { selectedItem ->
                    orderItems.add(selectedItem)
                    viewModel.addMenuItem(selectedItem, 1)
                }
            )// You may need to adjust this part based on your actual content
        }

        DividerFormat()  // 밑으로 변경 x

        // 주문 목록 표시
        OrderList(orderItems = orderItems )
        Column( //빈공간
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
        }
         // This will push the buttons up to be just above the bottom bar

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
            Spacer(modifier = Modifier.width(16.dp)) // Space between buttons

            KioskButtonFormat(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp)
                    .then(if (showBorder) Modifier.border(BorderWidth, BorderColor) else Modifier)
                ,
                onClick = onButtonClick,
                buttonText = buttonText,
                backgroundColor = Color.Red,
                contentColor = Color.Black
            )
            //Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

