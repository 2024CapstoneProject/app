package com.example.capstoneapp.component;

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

// NavigationBar 컴포넌트에 선택된 메뉴 항목을 업데이트하는 로직 추가
@Composable
fun CustomizedNavigationBar(
    menuItems: List<String>,
    onMenuItemClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        menuItems.forEach { item ->
            Text(
                text = item,
                style = TextStyle(
                    fontSize =18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                ),
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .clickable { onMenuItemClick(item) }
            )
        }
    }
}