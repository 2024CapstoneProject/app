package com.example.capstoneapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

import com.example.capstoneapp.data.repository.MenuItem
import com.example.capstoneapp.data.repository.MenuItemsRepository

@Composable
fun ItemList(selectedMenu: String, onItemClicked: (MenuItem) -> Unit) {
    // 선택된 메뉴에 따라 상품 목록을 가져옵니다.
    val items = MenuItemsRepository.getItemsForMenu(selectedMenu).filter { it.id % 2 != 0 }

    Column(modifier = Modifier.padding(16.dp)) {
        // 항목들을 2개씩 묶어서 새로운 리스트를 생성합니다.
        val rows = items.chunked(2)
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