package com.example.capstoneapp.ui.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.capstoneapp.data.Repository.MenuItem
import com.example.capstoneapp.data.Repository.MenuItemsRepository

@Composable
fun CafeMenuList(selectedMenu : String,onItemClicked:(MenuItem) ->Unit) {
    val items = MenuItemsRepository.getItemsForMenu(selectedMenu)

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(16.dp)
    ) {

        val rows = items.chunked(2)

        rows.forEach { rowItems ->
            Row {
                rowItems.forEach { item ->
                    ItemCard(item = item, onClick =  {
                        onItemClicked(item)
                    })
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun CafeMenuListPreview(){
    CafeMenuList("커피(HOT)", onItemClicked = {})
}