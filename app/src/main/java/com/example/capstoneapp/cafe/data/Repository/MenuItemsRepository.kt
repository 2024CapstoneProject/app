package com.example.capstoneapp.cafe.data.Repository

import com.example.capstoneapp.R

object MenuItemsRepository {

    private var coffeeHotMenuItems = listOf(
        MenuItem(
            1,
            "HOT아메리카노",
            R.drawable.americano_hot,
            2500
        ),
        MenuItem(
            2,
            "HOT카페라떼",
            R.drawable.latte_hot,
            3000
        ),
    )

    private var coffeeIceMenuItems = listOf(
        MenuItem(
            1,
            "ICE아메리카노",
            R.drawable.americano_ice,
            3000
        ),
        MenuItem(
            2,
            "ICE바닐라라떼",
            R.drawable.latte_ice,
            4000
        ),
    )

    private var TeaMenuItems = listOf(
        MenuItem(1, "녹차", R.drawable.green, 3000),
        MenuItem(
            2,
            "캐모마일",
            R.drawable.green,
            3500
        ),
    )

    fun getItemsForMenu(selectedMenu: String): List<MenuItem> {
        return when (selectedMenu) {
            "커피(HOT)" -> coffeeHotMenuItems
            "커피(ICE)" -> coffeeIceMenuItems
            "티(TEA)" -> TeaMenuItems
            else -> listOf()
        }
    }

    fun getMenuItemById(id: Int): MenuItem? {
        val menuItem = coffeeHotMenuItems.find { it.id == id }
        if (menuItem != null) {
            return menuItem
        }
        return null
    }
}

data class MenuItem(
    val id: Int,
    var name: String,
    val iconResourceId: Int,
    val price: Int,
)