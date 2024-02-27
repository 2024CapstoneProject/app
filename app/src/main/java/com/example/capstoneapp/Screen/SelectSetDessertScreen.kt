package com.example.capstoneapp.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.Frame.DividerFormat
import com.example.capstoneapp.Frame.kioskButtonFormat
import com.example.capstoneapp.R
import com.example.capstoneapp.component.CustomizedNavigationBar
import com.example.capstoneapp.component.ItemList
import com.example.capstoneapp.component.orderList
import com.example.capstoneapp.repository.MenuItem

@Composable
fun SelectSetDessertScreen() {
    // 주문한 목록
    val orderItems = remember { mutableStateListOf<MenuItem>() }
    var currentItemForDialog by remember { mutableStateOf<MenuItem?>(null) }

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
        CustomizedNavigationBar(
            menuItems = myMenuItems,
            onMenuItemClick = { menuItem ->
                selectedMenu = menuItem // 메뉴 항목 클릭 시 선택된 메뉴 업데이트
            }
        )

        DividerFormat()
        Spacer(Modifier.padding(top = 16.dp))

        Text(
            text = "세트 디저트 1개를 선택해주세요.",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        )

        // 메뉴 목록 표시
        ItemList(selectedMenu = selectedMenu) { selectedItem ->
            currentItemForDialog = selectedItem
            // 아이템 클릭 시 주문 목록에 추가
            orderItems.add(selectedItem)
        }

        DividerFormat()

        // 주문 목록 표시
        orderList(orderItems = dummyOrderItems)

        DividerFormat()

        Spacer(Modifier.weight(1f)) // This will push the buttons up to be just above the bottom bar

        // 결제 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp), // Apply horizontal padding
            horizontalArrangement = Arrangement.SpaceBetween // Arrange buttons with space in between
        ) {
            kioskButtonFormat(
                modifier = Modifier.weight(1f),
                onClick = { /* Handle click */ },
                buttonText = "취소하기",
                backgroundColor = Color.DarkGray,
                contentColor = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp)) // Space between buttons

            kioskButtonFormat(
                modifier = Modifier.weight(1f),
                onClick = { /* Handle click */ },
                buttonText = "선택완료",
                backgroundColor = Color.Red,
                contentColor = Color.Black
            )
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewISelectSetDessertScreen() {
    SelectSetDessertScreen()
}