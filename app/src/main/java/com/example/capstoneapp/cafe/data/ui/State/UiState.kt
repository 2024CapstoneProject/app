package com.example.capstoneapp.cafe.data.ui.State

sealed interface UiState<out T>{
    object Empty: UiState<Nothing>
    object Success: UiState<Nothing>

}