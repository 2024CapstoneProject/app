package com.example.capstoneapp.repository

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.capstoneapp.R

object MenuItemsRepository {
    private val hamburgerMenuItems = listOf(
        MenuItem(1, "불고기 버거", R.drawable.baseline_adb_24, 7000),
        MenuItem(2, "불고기 버거 세트", R.drawable.baseline_adb_24, 10000),
        MenuItem(3, "새우버거", R.drawable.baseline_adb_24, 7000),
        MenuItem(4, "새우버거 세트", R.drawable.baseline_adb_24, 10000),
        MenuItem(5, "치즈버거", R.drawable.baseline_adb_24, 7000),
        MenuItem(6, "치즈버거 세트", R.drawable.baseline_adb_24, 10000),
        MenuItem(7, "치킨버거", R.drawable.baseline_adb_24, 7000),
        MenuItem(8, "치킨버거 세트", R.drawable.baseline_adb_24, 10000)
    )

    fun getItemsForMenu(selectedMenu: String): List<MenuItem> {
        return when (selectedMenu) {
            "햄버거" -> hamburgerMenuItems
            // 다른 메뉴 카테고리에 대한 처리...
            else -> listOf()
        }
    }

    fun getMenuItemById(id: Int): MenuItem? {
        return hamburgerMenuItems.find { it.id == id }
    }


}

data class MenuItem(
    val id: Int,
    val name: String,
    val iconResourceId: Int,
    val price: Int
)
