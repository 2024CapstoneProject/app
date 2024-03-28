package com.example.capstoneapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.data.model.OrderViewModel

import com.example.capstoneapp.data.repository.MenuItem
import com.example.capstoneapp.data.repository.MenuItemsRepository
import com.example.capstoneapp.ui.screens.itemMenu
import androidx.compose.foundation.lazy.items
@Composable
fun ItemList(selectedMenu: String, onItemClicked: (MenuItem) -> Unit) {
    // 선택된 메뉴에 따라 상품 목록을 가져옵니다.
    val items = MenuItemsRepository.getItemsForMenu(selectedMenu).filter { it.id % 2 != 0 }
    Box(
        modifier = Modifier
            // Column 내 나머지 공간을 채우도록 설정
            .fillMaxWidth()
            .height(200.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(start = 50.dp, end = 50.dp)) {
            // 항목들을 2개씩 묶어서 새로운 리스트를 생성합니다.
            val rows = items.chunked(2)
            items(rows) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween // 항목 사이에 공간을 균등하게 배분
                ) {
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
}

@Preview(showBackground = true)
@Composable
fun PreviewItemMenuss() {
    val navController = rememberNavController()
    val viewModel = OrderViewModel()
//    itemMenu(navController = navController, viewModel)
}