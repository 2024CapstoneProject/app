package com.example.capstoneapp.cafe.ui.Components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.data.Repository.MenuItem
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth

@Composable
fun OrderList(
    orderItems: List<Pair<MenuItem, Int>>,
    onItemStatus: (Pair<MenuItem, String>) -> Unit,
    showBorder: Boolean = false
) {
    Box(
        modifier = Modifier
            .width(230.dp)
            .fillMaxHeight()
            .then(if (showBorder) Modifier.border(BorderWidth, BorderColor, BorderShape) else Modifier) // 조건에 따라 보더 적용
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            //선택한 메뉴 갯수에 따라 메뉴리스트 조회가능
            //메뉴 선택 시 status 변경 -> delete, add, minus
            items(orderItems.size) { index ->
                val item = orderItems[index]
                SelectedMenuSpec(
                    selectedMenuItem = item.first,
                    selectedMenuCount = item.second,
                ) {
                    onItemStatus(Pair(item.first, it))
                }
            }
        }
    }
}

@Composable
fun SelectedMenuSpec(
    selectedMenuItem: MenuItem, selectedMenuCount: Int, onItemClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .width(230.dp), contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(modifier = Modifier.size(28.dp), onClick = { onItemClick("Delete") }

            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "clear",
                    modifier = Modifier
                        .size(28.dp)
                        .padding(4.dp)
                )
            }
            Text(
                text = selectedMenuItem.name,
                modifier = Modifier.width(120.dp),
                textAlign = TextAlign.Center
            )
            IconButton(modifier = Modifier.size(28.dp), onClick = { onItemClick("Minus") }) {
                Icon(
                    painter = painterResource(id = R.mipmap.minus),
                    contentDescription = "Minus",
                    modifier = Modifier
                        .size(28.dp)
                        .padding(4.dp)
                )
            }
            Text(
                text = selectedMenuCount.toString(),
                modifier = Modifier,
                textAlign = TextAlign.Center
            )
            IconButton(modifier = Modifier.size(32.dp), onClick = { onItemClick("Add") }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp)
                )
            }
        }
    }
}