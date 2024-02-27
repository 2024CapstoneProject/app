package com.example.capstoneapp.Repository

import com.example.capstoneapp.R

object MenuItemsRepository {

    private var coffeeHotMenuItems = listOf(
        MenuItem(1,"아메리카노", R.drawable.cafe_icon, 2500),
        MenuItem(2,"카페라떼", R.drawable.cafe_icon, 3000),
    )

    private var coffeeIceMenuItems = listOf(
        MenuItem(1,"아메리카노", R.drawable.cafe_icon, 3000),
        MenuItem(2,"바닐라라떼", R.drawable.cafe_icon, 4000),
    )

    private var TeaMenuItems = listOf(
        MenuItem(1,"녹차", R.drawable.cafe_icon, 3000),
        MenuItem(2,"캐모마일", R.drawable.cafe_icon, 3500),
    )

    fun getItemsForMenu(selectedMenu : String):List<MenuItem>{
        return when (selectedMenu) {
            "커피(HOT)" -> coffeeHotMenuItems
            "커피(ICE)" -> coffeeIceMenuItems
            "티(TEA)" -> TeaMenuItems
            else -> listOf()
        }
    }

    fun getMenuItemById(id:Int) : MenuItem? {
        val menuItem = coffeeHotMenuItems.find{it.id==id}
        if(menuItem != null){
            return menuItem
        }
        return null
    }
}

data class MenuItem(
    val id:Int,
    var name:String,
    val iconResourceId:Int,
    val price:Int,
)