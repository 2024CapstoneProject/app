package com.example.capstoneapp.component

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.example.capstoneapp.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.capstoneapp.repository.MenuItem
import com.example.capstoneapp.repository.MenuItemsRepository

@Composable
fun ItemList(selectedMenu: String, onItemClicked: (MenuItem) -> Unit) {
    val items = MenuItemsRepository.getItemsForMenu(selectedMenu).filter { it.id % 2 != 0 }

    Column(
        modifier = Modifier.padding(16.dp),) {
        // 선택된 메뉴에 따라 다른 상품 목록을 보여주는 로직


        val rows = items.chunked(2) // 리스트를 2개씩 묶어서 새로운 리스트 생성
        rows.forEach { rowItems ->
            Row {
                rowItems.forEach { item ->
                    ItemCard(item = item, onClick = { onItemClicked(item) })
                }
                // 항목이 하나만 있는 경우 Spacer를 추가하여 정렬합니다.
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f)) // 남은 공간을 채우기 위해 weight 사용
                }
            }
        }
    }
}