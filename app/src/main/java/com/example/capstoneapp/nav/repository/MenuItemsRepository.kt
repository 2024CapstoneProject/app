package com.example.capstoneapp.nav.repository

import com.example.capstoneapp.R

object MenuItemsRepository {
    private var hamburgerMenuItems = listOf(
        MenuItem(1, "불고기 버거", R.drawable.bulgogi, 7000, "햄버거"),
        MenuItem(2, "불고기 버거 세트", R.drawable.burger_set, 10000, "햄버거"),
        MenuItem(3, "새우버거", R.drawable.shrimp, 7000, "햄버거"),
        MenuItem(4, "새우버거 세트", R.drawable.burger_set, 10000, "햄버거"),
        MenuItem(5, "치즈버거", R.drawable.cheese, 7000, "햄버거"),
        MenuItem(6, "치즈버거 세트", R.drawable.burger_set, 10000, "햄버거"),
        MenuItem(7, "치킨버거", R.drawable.chicken, 7000, "햄버거"),
        MenuItem(8, "치킨버거 세트", R.drawable.burger_set, 10000, "햄버거")
    )

    private var dessertMenuItems = listOf(
        MenuItem(1, "감자튀김", R.drawable.potato, 0, "디저트"),
        MenuItem(3, "양념감자", R.drawable.y_potato, 500, "디저트"),
        MenuItem(5, "치킨텐더", R.drawable.tender, 800, "디저트"),
        MenuItem(7, "치즈스틱", R.drawable.cheese_stick, 800, "디저트")
    )

    private var drinkMenuItems = listOf(
        MenuItem(1, "콜라", R.drawable.cola, 0, "드링크"),
        MenuItem(3, "사이다", R.drawable.sprite, 0, "드링크"),
        MenuItem(5, "제로콜라", R.drawable.zero_cola, 0, "드링크"),
        MenuItem(7, "오렌지 주스", R.drawable.orange, 300, "드링크")
    )

    fun getItemsForMenu(selectedMenu: String): List<MenuItem> {
        return when (selectedMenu) {
            "햄버거" -> hamburgerMenuItems
            "세트 디저트" -> dessertMenuItems
            "세트 드링크" -> drinkMenuItems
            else -> listOf()
        }
    }

    fun getMenuItemById(id: Int): MenuItem? {
        return (hamburgerMenuItems + dessertMenuItems + drinkMenuItems).find { it.id == id }
    }
}

data class MenuItem(
    val id: Int,
    val name: String,
    val iconResourceId: Int,
    val price: Int,
    val type: String // 추가된 속성
)

data class OrderItem(
    val menuItem: MenuItem,
    var quantity: Int // 주문 수량
)
