package com.example.capstoneapp.fastfood.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneapp.fastfood.data.repository.MenuItem
import com.example.capstoneapp.fastfood.data.repository.OrderItem

open class OrderViewModel : ViewModel() {
    private val _orderItems = MutableLiveData<List<OrderItem>>()
    val orderItems: LiveData<List<OrderItem>> = _orderItems

    private val _totalOrderAmount = MutableLiveData<Int>()
    val totalOrderAmount: LiveData<Int> = _totalOrderAmount

    init {
        // _orderItems가 업데이트될 때마다 전체 금액 계산
        _orderItems.observeForever { updateTotalOrderAmount() }
    }

    private fun updateTotalOrderAmount() {
        _totalOrderAmount.value = _orderItems.value?.sumOf { it.menuItem.price * it.quantity } ?: 0
    }

    fun addMenuItem(menuItem: MenuItem, quantity: Int = 1) {
        val updatedList = _orderItems.value?.toMutableList() ?: mutableListOf()
        val existingOrderItem = updatedList.find { it.menuItem.id == menuItem.id }
        if (existingOrderItem != null) {
            // 이미 주문 목록에 있는 경우, 수량을 업데이트
            existingOrderItem.quantity += quantity
        } else {
            // 새로운 항목을 주문 목록에 추가
            updatedList.add(OrderItem(menuItem, quantity))
        }
        // 주문 목록 업데이트
        _orderItems.value = updatedList
        // 전체 금액 업데이트
        updateTotalOrderAmount()
    }


    // 주문 목록에서 항목 제거
    fun removeMenuItem(menuItemId: Int) {
        _orderItems.value = _orderItems.value?.filter { it.menuItem.id != menuItemId }
    }

    // 전체 주문 금액 계산

}