package com.example.capstoneapp.data.model

import com.example.capstoneapp.R
import com.example.capstoneapp.data.repository.MenuItem
import com.example.capstoneapp.data.repository.OrderItem

class PreviewOrderViewModel : OrderViewModel() {
    init {
        // Initialize with mock data
        val mockOrderItems = listOf(
            MenuItem(1, "불고기 버거", R.drawable.baseline_adb_24, 7000),
                    MenuItem(2, "불고기 버거 세트", R.drawable.baseline_adb_24, 10000),
           MenuItem(3, "새우버거", R.drawable.baseline_adb_24, 7000)
        )
        // Assuming you have a method in your ViewModel to set items directly
        this.addMenuItem(mockOrderItems.get(0))
        this.addMenuItem(mockOrderItems.get(0))
        this.addMenuItem(mockOrderItems.get(2))
    }
}