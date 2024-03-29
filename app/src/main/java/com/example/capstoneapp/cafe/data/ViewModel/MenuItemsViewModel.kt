package com.example.capstoneapp.cafe.data.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneapp.cafe.data.Repository.MenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class MenuItemsViewModel @Inject constructor() : ViewModel() {

    private val _orderItems = MutableLiveData<List<Pair<MenuItem, Int>>>()
    val orderItems: LiveData<List<Pair<MenuItem, Int>>> = _orderItems

    private val _totalOrderAmount = MutableLiveData<Int>()
    val totalOrderAmount: LiveData<Int> = _totalOrderAmount

    private val _totalOrderCount = MutableLiveData<Int>()
    val totalOrderCount: LiveData<Int> = _totalOrderCount

    init {
        // _orderItems가 업데이트될 때마다 전체 금액, 전체 개수 계산
        _orderItems.observeForever { updateTotalOrderAmount() }
        _orderItems.observeForever { updateTotalOrderCount() }
    }

    private fun updateTotalOrderAmount() { //각 메뉴 가격 * 개수 = 전체금액
        _totalOrderAmount.value = _orderItems.value?.sumOf { it.first.price * it.second } ?: 0
    }

    private fun updateTotalOrderCount() { //전체 메뉴 개수
        _totalOrderCount.value = _orderItems.value?.sumOf { it.second } ?: 0
    }

    //주문 목록 메뉴 추가
    fun addMenuItem(targetPair: Pair<MenuItem, Int>, targetPairIndex: Int) {
        val updatedList = _orderItems.value?.toMutableList() ?: mutableListOf()

        //있으면 개수 증가, 없으면 항목 추가
        if (targetPairIndex == -1) {
            updatedList.add(targetPair)
        } else {
            updatedList[targetPairIndex] = Pair(targetPair.first, targetPair.second + 1)
        }
        // 주문 목록 업데이트
        _orderItems.value = updatedList
        // 전체 금액 업데이트
        updateTotalOrderAmount()
    }

    //주문 메뉴 개수 감소 & 주문 목록 메뉴 제거
    fun minusMenuItem(targetPair: Pair<MenuItem, Int>, targetPairIndex: Int) {
        val removeList = _orderItems.value?.toMutableList() ?: mutableListOf()
        if (targetPair.second - 1 == 0) {
            removeList.remove(targetPair)
        } else {
            removeList[targetPairIndex] = Pair(targetPair.first, targetPair.second - 1)
        }
        _orderItems.value = removeList
        updateTotalOrderAmount()
    }

    //주문 메뉴 삭제
    fun removeMenuItem(targetPair: Pair<MenuItem, Int>) {
        val removeList = _orderItems.value?.toMutableList() ?: mutableListOf()
        removeList.remove(targetPair)
        _orderItems.value = removeList
        updateTotalOrderAmount()
    }

    //주문 목록 초기화
    fun clearMenuItem() {
        val clearList = _orderItems.value?.toMutableList() ?: mutableListOf()
        if(clearList.isNotEmpty()){
            clearList.clear()
        }
        _orderItems.value = clearList
        updateTotalOrderAmount()
    }
}