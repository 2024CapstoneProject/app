package com.example.capstoneapp.cafe.ui.State

sealed interface UiState<out T>{
    object Empty: UiState<Nothing>
    object Success: UiState<Nothing>

}