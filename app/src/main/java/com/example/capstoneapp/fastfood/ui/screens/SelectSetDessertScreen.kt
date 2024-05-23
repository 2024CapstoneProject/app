package com.example.capstoneapp.fastfood.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.R
import com.example.capstoneapp.fastfood.ui.components.CustomizedNavigationBar
import com.example.capstoneapp.fastfood.ui.components.ItemList
import com.example.capstoneapp.fastfood.ui.theme.fontFamily
import com.example.capstoneapp.nav.repository.MenuItem

@Composable
fun SelectSetDessertScreen(onItemSelected: (MenuItem) -> Unit) {
    // 주문한 목록
    val orderItems = remember { mutableStateListOf<MenuItem>() }
    var currentItemForDialog by remember { mutableStateOf<MenuItem?>(null) }
    var selectDessert by remember { mutableStateOf(false) }
    var selectDrink by remember { mutableStateOf(false) }
    //테스트용 메뉴 선택 더미 데이터
    val dummyOrderItems = listOf(
        MenuItem(1, "감자튀김", R.drawable.baseline_adb_24, 0)
    )

    //네비게이션 카테고리 선택
    var selectedMenu by remember { mutableStateOf("세트 디저트") } // 초기값 설정
    val myMenuItems = listOf("세트 디저트", "세트 드링크")

    Column(
        modifier = Modifier.padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //네비게이션 bar
        Box {
            CustomizedNavigationBar(
                menuItems = myMenuItems,
                selectedMenuItem = selectedMenu,
                onMenuItemClick = { menuItem ->
                    selectedMenu = menuItem // 메뉴 항목 클릭 시 선택된 메뉴 업데이트
                }
            )
        }

        Spacer(Modifier.padding(top = 16.dp))
        Text(
            text = "세트 디저트 1개를 선택해주세요.",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily
            )
        )

        // 메뉴 목록 표시
        ItemList(selectedMenu = selectedMenu) { selectedItem ->
            onItemSelected(selectedItem) // This will invoke the callback passed from itemMenu
            selectedMenu="세트 드링크"
        }
    }

//        DividerFormat()
//
//        // 주문 목록 표시
//        OrderList(orderItems = dummyOrderItems)
//
//        DividerFormat()
//
//        Spacer(Modifier.weight(1f)) // This will push the buttons up to be just above the bottom bar
//
//        // 결제 버튼
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 32.dp), // Apply horizontal padding
//            horizontalArrangement = Arrangement.SpaceBetween // Arrange buttons with space in between
//        ) {
//            KioskButtonFormat(
//                modifier = Modifier.weight(1f),
//                onClick = { /* Handle click */ },
//                buttonText = "취소하기",
//                backgroundColor = Color.DarkGray,
//                contentColor = Color.Black
//            )
//            Spacer(modifier = Modifier.width(16.dp)) // Space between buttons
//
//            KioskButtonFormat(
//                modifier = Modifier.weight(1f),
//                onClick = { /* Handle click */ },
//                buttonText = "선택완료",
//                backgroundColor = Color.Red,
//                contentColor = Color.Black
//            )
//            Spacer(modifier = Modifier.height(48.dp))
//        }
//    }
}//

@Preview(showBackground = true)
@Composable
fun PreviewISelectSetDessertScreen() {
    val dummyOrderItems = listOf(
        MenuItem(1, "감자튀김", R.drawable.baseline_adb_24, 0)
    )
    SelectSetDessertScreen(onItemSelected = {dummyOrderItems})
}