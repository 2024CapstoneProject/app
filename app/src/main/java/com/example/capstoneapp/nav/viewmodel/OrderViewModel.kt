package com.example.capstoneapp.nav.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneapp.nav.repository.MenuItem
import com.example.capstoneapp.nav.repository.OrderItem

class OrderViewModel : ViewModel() {
    private val _orderItems = MutableLiveData<List<OrderItem>>(emptyList())
    val orderItems: LiveData<List<OrderItem>> = _orderItems

    private val _totalOrderAmount = MutableLiveData(0)
    val totalOrderAmount: LiveData<Int> = _totalOrderAmount

    fun addMenuItem(menuItem: MenuItem, quantity: Int) {
        val currentItems = _orderItems.value ?: emptyList()
        val updatedItems = currentItems.toMutableList().apply {
            add(OrderItem(menuItem, quantity))
        }
        _orderItems.value = updatedItems
        calculateTotalAmount(updatedItems)
    }

    private fun calculateTotalAmount(orderItems: List<OrderItem>) {
        val total = orderItems.sumOf { it.menuItem.price * it.quantity }
        _totalOrderAmount.value = total
    }
}
