package com.example.capstoneapp.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.capstoneapp.Frame.NotificationScreen
import com.example.capstoneapp.component.CafeKioskMainFormat


@Composable
fun CafeKioskScreen(){
    NotificationScreen {
        CafeKioskMainFormat()
    }

}

@Preview
@Composable
fun cafeKioskScreenPreview(){
    CafeKioskScreen()
}