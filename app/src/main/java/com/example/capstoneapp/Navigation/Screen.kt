package com.example.capstoneapp.Navigation

sealed class Screen(val route: String) {
    object menu : Screen("menu_screen")
    object pay : Screen("pay_screen")
    //object Once : Screen("once_screen")
}