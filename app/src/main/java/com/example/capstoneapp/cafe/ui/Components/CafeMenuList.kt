package com.example.capstoneapp.cafe.ui.Components

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
import com.example.capstoneapp.cafe.data.Repository.MenuItem
import com.example.capstoneapp.cafe.data.Repository.MenuItemsRepository

@Composable
fun CafeMenuList(selectedMenu : String,onItemClicked:(MenuItem) ->Unit, showBorder:Boolean) {
    val items = MenuItemsRepository.getItemsForMenu(selectedMenu)
    val coffeeIceMenuItems = MenuItemsRepository.getItemsForMenu("커피(ICE)")

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(16.dp)
    ) {

        val rows = items.chunked(2)

        rows.forEachIndexed() { rowIndex,rowItems ->
            Row {
                rowItems.forEachIndexed() { itemIndex,item ->

                    ItemCard(item = item, onClick =  {
                        onItemClicked(item)
                    }, showBorder = showBorder && rowIndex == 0 && itemIndex == 0 &&
                            item in coffeeIceMenuItems)
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
    CafeMenuList("커피(HOT)", onItemClicked = {},true)
}