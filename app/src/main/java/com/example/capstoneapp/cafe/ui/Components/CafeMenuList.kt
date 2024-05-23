package com.example.capstoneapp.cafe.ui.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.capstoneapp.cafe.data.Repository.MenuItem
import com.example.capstoneapp.cafe.data.Repository.MenuItemsRepository
import com.example.capstoneapp.kakatalk.ui.Components.RepeatDialog
import com.example.capstoneapp.nav.repository.Problem

@Composable
fun CafeMenuList(selectedMenu : String,onItemClicked:(MenuItem) ->Unit, showBorder:Boolean,problem: Problem) {
    val items = MenuItemsRepository.getItemsForMenu(selectedMenu)
    var repeatAnswer by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(16.dp)
    ) {

        val rows = items.chunked(2)

        rows.forEach { rowItems ->
            Row {
                rowItems.forEach { item ->
                    ItemCard(
                        item = item,
                        onClick = {
                            onItemClicked(item)
                            if (problem.c_menu != item.name) {
                                repeatAnswer = true
                            }
                        },
                        showBorder = showBorder && (problem.c_menu == item.name)
                    )
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }

    }
    if(repeatAnswer){
        RepeatDialog(onDismiss = {
            repeatAnswer = false })
    }
}

@Preview
@Composable
fun CafeMenuListPreview(){
    //CafeMenuList("커피(HOT)", onItemClicked = {},true,problem)
}