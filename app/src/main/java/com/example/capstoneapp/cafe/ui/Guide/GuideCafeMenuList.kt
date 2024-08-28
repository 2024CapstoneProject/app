package com.example.capstoneapp.cafe.ui.Guide

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.capstoneapp.cafe.data.Repository.MenuItem
import com.example.capstoneapp.cafe.data.Repository.MenuItemsRepository
import com.example.capstoneapp.cafe.ui.Components.ItemCard
import com.example.capstoneapp.nav.repository.Problem

@Composable
fun GuideCafeMenuList(
    selectedMenu: String,
    onItemClicked: (MenuItem) -> Unit,
    showBorder: Boolean,
    problem: Problem,
    currentStep: Int
) {
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
                    ItemCard(
                        item = item,
                        onClick = { onItemClicked(item) },
                        showBorder = currentStep == 3 && showBorder && (problem.c_menu == item.name)
                    )
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}
