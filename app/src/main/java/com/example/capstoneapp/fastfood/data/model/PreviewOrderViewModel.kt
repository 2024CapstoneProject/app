package com.example.capstoneapp.fastfood.data.model

import com.example.capstoneapp.R
import com.example.capstoneapp.nav.repository.MenuItem

class PreviewOrderViewModel : OrderViewModel() {
    init {
        // Initialize with mock data
        val mockOrderItems = listOf(
            MenuItem(1, "불고기 버거", R.drawable.baseline_adb_24, 7000, "햄버거"),
            MenuItem(2, "불고기 버거 세트", R.drawable.baseline_adb_24, 10000, "햄버거"),
            MenuItem(3, "새우버거", R.drawable.baseline_adb_24, 7000, "햄버거")
        )
        // Assuming you have a method in your ViewModel to set items directly
        this.addMenuItem(mockOrderItems[0], 1)
        this.addMenuItem(mockOrderItems[1], 1)
        this.addMenuItem(mockOrderItems[2], 1)
    }
}
