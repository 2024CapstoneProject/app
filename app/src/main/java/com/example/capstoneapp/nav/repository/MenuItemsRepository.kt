package com.example.capstoneapp.nav.repository

import com.example.capstoneapp.R
import com.example.capstoneapp.R.drawable

object MenuItemsRepository {
    private var recommendMenuItems = listOf(
        MenuItem(1, "불고기 버거", drawable.bulgogi, 7000, "햄버거"),
        MenuItem(3, "새우버거", R.drawable.shrimp, 7000, "햄버거"),
        MenuItem(5, "양념감자", drawable.y_potato, 2500, "디저트"),
        MenuItem(7, "치즈스틱", drawable.cheese_stick, 2500, "디저트")
    )

    private var hamburgerMenuItems = listOf(
        MenuItem(1, "불고기 버거", drawable.bulgogi, 7000, "햄버거"),
        MenuItem(2, "불고기 버거 세트", drawable.burger_set, 10000, "햄버거"),
        MenuItem(3, "새우버거", drawable.shrimp, 7000, "햄버거"),
        MenuItem(4, "새우버거 세트", drawable.burger_set, 10000, "햄버거"),
        MenuItem(5, "치즈버거", drawable.cheese, 7000, "햄버거"),
        MenuItem(6, "치즈버거 세트", drawable.burger_set, 10000, "햄버거"),
        MenuItem(7, "치킨버거", drawable.chicken, 7000, "햄버거"),
        MenuItem(8, "치킨버거 세트", drawable.burger_set, 10000, "햄버거")
    )

    private var dessertMenuItems = listOf(
        MenuItem(1, "감자튀김", drawable.potato, 2000, "디저트"),
        MenuItem(3, "양념감자", drawable.y_potato, 2500, "디저트"),
        MenuItem(5, "치킨텐더", drawable.tender, 3000, "디저트"),
        MenuItem(7, "치즈스틱", drawable.cheese_stick, 2500, "디저트")
    )

    private var drinkMenuItems = listOf(
        MenuItem(1, "콜라", drawable.cola, 2000, "드링크"),
        MenuItem(3, "사이다", drawable.sprite, 2000, "드링크"),
        MenuItem(5, "제로콜라", drawable.zero_cola, 2000, "드링크"),
        MenuItem(7, "오렌지 주스", drawable.orange, 2300, "드링크")
    )

    private var setDessertMenuItems = listOf(
        MenuItem(1, "감자튀김", drawable.potato, 0, "디저트"),
        MenuItem(3, "양념감자", drawable.y_potato, 500, "디저트"),
        MenuItem(5, "치킨텐더", drawable.tender, 800, "디저트"),
        MenuItem(7, "치즈스틱", drawable.cheese_stick, 800, "디저트")
    )

    private var setDrinkMenuItems = listOf(
        MenuItem(1, "콜라", drawable.cola, 0, "드링크"),
        MenuItem(3, "사이다", drawable.sprite, 0, "드링크"),
        MenuItem(5, "제로콜라", drawable.zero_cola, 0, "드링크"),
        MenuItem(7, "오렌지 주스", drawable.orange, 300, "드링크")
    )

    fun getItemsForMenu(selectedMenu: String): List<MenuItem> {
        return when (selectedMenu) {
            "추천메뉴" -> recommendMenuItems
            "햄버거" -> hamburgerMenuItems
            "디저트/치킨" -> dessertMenuItems
            "음료/커피" -> drinkMenuItems
            "세트 디저트" -> setDessertMenuItems
            "세트 드링크" -> setDrinkMenuItems
            else -> listOf()
        }
    }

    fun getMenuItemById(id: Int): MenuItem? {
        return (recommendMenuItems + hamburgerMenuItems + dessertMenuItems + drinkMenuItems + setDessertMenuItems + setDrinkMenuItems).find { it.id == id }
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
