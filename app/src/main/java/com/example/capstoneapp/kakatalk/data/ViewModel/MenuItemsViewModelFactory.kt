package com.example.capstoneapp.kakatalk.data.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MenuItemsViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuItemsViewModel::class.java)) {
            return MenuItemsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}