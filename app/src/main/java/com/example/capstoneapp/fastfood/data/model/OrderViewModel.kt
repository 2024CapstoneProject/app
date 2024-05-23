package com.example.capstoneapp.fastfood.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneapp.nav.repository.MenuItem
import com.example.capstoneapp.nav.repository.OrderItem

open class OrderViewModel : ViewModel() {
    private val _orderItems = MutableLiveData<List<OrderItem>>()
    val orderItems: LiveData<List<OrderItem>> get() = _orderItems

    private val _totalOrderAmount = MutableLiveData<Int>()
    val totalOrderAmount: LiveData<Int> get() = _totalOrderAmount

    init {
        _orderItems.value = listOf()
        _totalOrderAmount.value = 0
    }

    fun addMenuItem(menuItem: MenuItem, quantity: Int) {
        val currentItems = _orderItems.value.orEmpty().toMutableList()

        // 이미 같은 아이템이 추가된 경우 추가하지 않음
        val existingItem = currentItems.find { it.menuItem.id == menuItem.id }
        if (existingItem != null) {
            return
        }

        // 선택한 타입의 메뉴가 여러 개일 경우 마지막으로 선택된 메뉴만 남기고 제거합니다.
        currentItems.removeAll { it.menuItem.type == menuItem.type }
        currentItems.add(OrderItem(menuItem, quantity))

        _orderItems.value = currentItems
        calculateTotalAmount()
    }

    private fun calculateTotalAmount() {
        _totalOrderAmount.value = _orderItems.value.orEmpty().sumOf { it.menuItem.price * it.quantity }
    }
}

