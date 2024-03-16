package com.example.capstoneapp.state

sealed interface UiState<out T>{
    object Empty: UiState<Nothing>
    object Success: UiState<Nothing>

}