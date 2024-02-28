package com.example.capstoneapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
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
import com.example.capstoneapp.R
import com.example.capstoneapp.data.repository.MenuItem
import com.example.capstoneapp.ui.components.CustomizedNavigationBar
import com.example.capstoneapp.ui.components.DividerFormat
import com.example.capstoneapp.ui.components.ItemList
import com.example.capstoneapp.ui.components.KioskButtonFormat
import com.example.capstoneapp.ui.components.OrderList

@Composable
fun itemMenu() {
    // 주문한 목록
    val orderItems = remember { mutableStateListOf<MenuItem>() }
    var showDialog by remember { mutableStateOf(false) }
    var currentItemForDialog by remember { mutableStateOf<MenuItem?>(null) }

    //테스트용 메뉴 선택 더미 데이터
    val dummyOrderItems = listOf(
        MenuItem(1,"불고기 버거", R.drawable.baseline_adb_24, 7000)
    )

    //네비게이션 카테고리 선택
    var selectedMenu by remember { mutableStateOf("햄버거") } // 초기값 설정
    val myMenuItems = listOf("추천메뉴", "햄버거", "디저트/치킨", "음료/커피")

    Column(
        modifier = Modifier.padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //네비게이션 bar (추천 메뉴, 햄버거 ...)
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
                    showDialog = false
                }
            )
        }

        DividerFormat()

        // 주문 목록 표시
        OrderList(orderItems = orderItems )

        DividerFormat()

        Spacer(Modifier.weight(1f)) // This will push the buttons up to be just above the bottom bar

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
                    .padding(bottom=16.dp),
                onClick = { /* Handle click */ },
                buttonText = "취소하기",
                backgroundColor = Color.DarkGray,
                contentColor = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp)) // Space between buttons

            KioskButtonFormat(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom=16.dp)
                ,
                onClick = { /* Handle click */ },
                buttonText = "결제하기",
                backgroundColor = Color.Red,
                contentColor = Color.Black
            )
            //Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
fun DefaultMenuPreview() {
    // 주문 내역에 "불고기 버거" 추가를 시뮬레이션
    val dummyOrderItems = listOf(
        MenuItem(1,"불고기 버거", R.drawable.baseline_adb_24, 7000)
    )

    // AppTheme와 같은 상위 테마 컴포저블이 있으면 여기서 감싸줍니다.
    var selectedMenu by remember { mutableStateOf("햄버거") } // 초기값 설정
    val myMenuItems = listOf("추천메뉴", "햄버거", "디저트/치킨", "음료/커피")

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {// 선택된 메뉴를 기반으로 ItemList 렌더링
        //네비게이션 bar (추천 메뉴, 햄버거 ...)
        Box {
            CustomizedNavigationBar(
                menuItems = myMenuItems,
                selectedMenuItem = selectedMenu,
                onMenuItemClick = { menuItem ->
                    selectedMenu = menuItem // 메뉴 항목 클릭 시 선택된 메뉴 업데이트
                }
            )
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
        ItemList(selectedMenu = selectedMenu) {
            // 미리 정의된 주문 내역을 사용하므로 여기서는 아무 작업도 하지 않음
        }
        // 구분선
        Divider(color = Color.Gray, thickness = 2.dp)
        // 더미 주문 내역을 렌더링
        OrderList(orderItems = dummyOrderItems)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemMenu() {
    itemMenu()
}

