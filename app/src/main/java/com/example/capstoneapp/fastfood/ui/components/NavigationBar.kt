package com.example.capstoneapp.fastfood.ui.components;

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.fastfood.ui.theme.fontFamily

// NavigationBar 컴포넌트에 선택된 메뉴 항목을 업데이트하는 로직 추가
@Composable
fun CustomizedNavigationBar(
    menuItems: List<String>,
    selectedMenuItem: String, // 현재 선택된 메뉴 아이템
    onMenuItemClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray,shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceEvenly, // 메뉴 항목들을 공간에 균등하게 배치
            verticalAlignment = Alignment.CenterVertically
        ) {
            menuItems.forEach { item ->
                val isSelected = item == selectedMenuItem

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 2.dp)
                        .padding(top = 4.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)) // 상단 모서리를 둥글게
                        // todo : 배경색 바꾸기
                        .background(if (isSelected) Color.White else Color.LightGray) // 선택되지 않은 메뉴의 배경색을 회색으로 설정
                        .then(
                            if (isSelected) Modifier.border(
                                width = 1.dp,
                                color = Color.Transparent,
                                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp) // 하단 테두리 제외
                            )
                            else Modifier.background(Color.Transparent)
                        )
                        .padding(horizontal = 8.dp, vertical = 10.dp)
                        .clickable { onMenuItemClick(item) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                            color = if (isSelected) Color.Black else Color.DarkGray,
                            fontFamily = fontFamily
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NavBarDefaultPreview() {
    CustomizedNavigationBar(
        menuItems = listOf("추천메뉴", "햄버거", "디저트/치킨", "음료/커피"),
        selectedMenuItem = "햄버거",
        onMenuItemClick = { "햄버거" }
    )
}