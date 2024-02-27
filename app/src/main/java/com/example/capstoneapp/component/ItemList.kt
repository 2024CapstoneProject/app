package com.example.capstoneapp.component

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.example.capstoneapp.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
@Composable
fun ItemList(selectedMenu: String, onItemClicked: (MenuItem) -> Unit) {
    Column {
        // 선택된 메뉴에 따라 다른 상품 목록을 보여주는 로직
        val items = when (selectedMenu) {
            "햄버거" -> listOf(
                MenuItem("불고기 버거", R.drawable.baseline_adb_24, 7000),//R.drawable.ic_bulgogi_burger),
                MenuItem("새우버거", R.drawable.baseline_adb_24, 7000),//R.drawable.ic_shrimp_burger),
                MenuItem("치즈버거",  R.drawable.baseline_adb_24, 7000),//R.drawable.ic_cheese_burger),
                MenuItem("치킨버거",  R.drawable.baseline_adb_24, 7000),//R.drawable.ic_chicken_burger)
            )
            "세트 디저트" -> listOf(
                MenuItem("감자튀김", R.drawable.baseline_adb_24, 0),//R.drawable.ic_bulgogi_burger),
                MenuItem("양념 감자튀김", R.drawable.baseline_adb_24, 500),//R.drawable.ic_shrimp_burger),
                MenuItem("디저트1",  R.drawable.baseline_adb_24, 800),//R.drawable.ic_cheese_burger),
                MenuItem("디저트2",  R.drawable.baseline_adb_24, 800),//R.drawable.ic_chicken_burger)
            )
            "세트 드링크" -> listOf(
                MenuItem("콜라", R.drawable.baseline_adb_24, 0),//R.drawable.ic_bulgogi_burger),
                MenuItem("사이다", R.drawable.baseline_adb_24, 0),//R.drawable.ic_shrimp_burger),
                MenuItem("제로콜라",  R.drawable.baseline_adb_24, 0),//R.drawable.ic_cheese_burger),
                MenuItem("오랜지 주스",  R.drawable.baseline_adb_24, 500),//R.drawable.ic_chicken_burger)
            )
            // 다른 메뉴에 대한 아이템 리스트 정의
            else -> listOf()
        }

        val rows = items.chunked(2) // 리스트를 2개씩 묶어서 새로운 리스트 생성
        rows.forEach { rowItems ->
            Row {
                rowItems.forEach { item ->
                    ItemCard(item = item) {
                        onItemClicked(item)
                    }
                }
                if (rowItems.size < 2) { // 항목이 하나만 있는 경우 Spacer 추가로 정렬
                    Spacer(modifier = Modifier.width(92.dp + 16.dp)) // ItemCard의 너비와 패딩 고려
                }
            }
        }
    }
}

@Composable
fun ItemCard(item: MenuItem, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(18.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(id = item.iconResourceId),

            contentDescription = "",
            modifier = Modifier
                .width(92.dp) // 아이콘의 너비를 48dp로 설정
                .height(90.dp)
        )
        Text(
            text = item.name,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = item.price.toString() + "원",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

data class MenuItem(
    val name: String,
    val iconResourceId: Int,
    val price:Int
)
