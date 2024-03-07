package com.example.capstoneapp.Navigation

sealed class Screen(val route:String){
    object pay : Screen("PayTest")
}