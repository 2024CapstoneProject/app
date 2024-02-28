package com.example.capstoneapp.ui.components;

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

// NavigationBar 컴포넌트에 선택된 메뉴 항목을 업데이트하는 로직 추가
@Composable
fun CustomizedNavigationBar(
    menuItems: List<String>,
    selectedMenuItem: String, // 현재 선택된 메뉴 아이템
    onMenuItemClick: (String) -> Unit
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
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)) // 상단 모서리를 둥글게
                    .then(
                        if (isSelected) Modifier.border(
                            width = 2.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                        ).background(Color.Transparent)
                        else Modifier.background(Color.Transparent)
                    )
                    .padding(horizontal =8.dp, vertical = 10.dp)
                    .clickable { onMenuItemClick(item) },
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = item,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.Black else Color.Black,
                        fontFamily = FontFamily.SansSerif
                    )
                )
            }
        }
        if (!selectedMenuItem.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp) // 전체 Row의 좌우 패딩과 일치시킵니다.
                    .wrapContentSize(Alignment.BottomStart) // 하단에 내용을 배치합니다.
                    .offset(y = 10.dp) // 텍스트의 하단에서부터 시작점을 옮깁니다.
                    .height(2.dp) // 박스의 높이를 2dp로 설정합니다.
                    .background(Color.LightGray) // 박스의 배경색을 LightGray로 설정합니다.
            )
        }
    }
}

@Composable
@Preview
fun NavBarDefaulPreview() {
    CustomizedNavigationBar(
        menuItems = listOf("추천메뉴", "햄버거", "디저트/치킨", "음료/커피"),
        selectedMenuItem = "햄버거",
        onMenuItemClick = { "햄버거" }
    )
}