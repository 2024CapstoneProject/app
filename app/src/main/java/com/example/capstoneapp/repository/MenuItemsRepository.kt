package com.example.capstoneapp.repository

import com.example.capstoneapp.R

object MenuItemsRepository {
    private var hamburgerMenuItems = listOf(
        MenuItem(1, "불고기 버거", R.drawable.baseline_adb_24, 7000),
        MenuItem(2, "불고기 버거 세트", R.drawable.baseline_adb_24, 10000),
        MenuItem(3, "새우버거", R.drawable.baseline_adb_24, 7000),
        MenuItem(4, "새우버거 세트", R.drawable.baseline_adb_24, 10000),
        MenuItem(5, "치즈버거", R.drawable.baseline_adb_24, 7000),
        MenuItem(6, "치즈버거 세트", R.drawable.baseline_adb_24, 10000),
        MenuItem(7, "치킨버거", R.drawable.baseline_adb_24, 7000),
        MenuItem(8, "치킨버거 세트", R.drawable.baseline_adb_24, 10000)
    )

    private var dessertMenuItems = listOf(
        MenuItem(1, "감자튀김", R.drawable.baseline_adb_24, 0),
        MenuItem(2, "양념감자", R.drawable.baseline_adb_24, 500),
        MenuItem(3, "치킨텐더", R.drawable.baseline_adb_24, 800),
        MenuItem(4, "치즈스틱", R.drawable.baseline_adb_24, 800),
    )

    private var drinkMenuItems = listOf(
        MenuItem(1, "콜라", R.drawable.baseline_adb_24, 0),
        MenuItem(2, "사이다", R.drawable.baseline_adb_24, 0),
        MenuItem(3, "제로콜라", R.drawable.baseline_adb_24, 0),
        MenuItem(4, "오랜지 주스", R.drawable.baseline_adb_24, 300),
    )

    fun getItemsForMenu(selectedMenu: String): List<MenuItem> {
        return when (selectedMenu) {
            "햄버거" -> hamburgerMenuItems
            "세트 디저트" -> dessertMenuItems
            "세트 드링크" -> drinkMenuItems
            // 다른 메뉴 카테고리에 대한 처리...
            else -> listOf()
        }
    }

    fun getMenuItemById(id: Int): MenuItem? {

        val menuItem = hamburgerMenuItems.find { it.id == id }
        if(menuItem != null) {
            return menuItem
        }
        return null;
    }
}

data class MenuItem(
    val id: Int,
    var name: String,
    val iconResourceId: Int,
    val price: Int
)