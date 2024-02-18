package com.example.capstoneapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CafeMenuList(){
    Spacer(Modifier.width(10.dp))

    Box(
        modifier = Modifier
            .width(130.dp)
            .height(180.dp)
            .background(Color.White)
            .border(1.dp, Color.Black),
    ){

    }
    Spacer(Modifier.width(30.dp))

    Box(
        modifier = Modifier
            .width(130.dp)
            .height(180.dp)
            .background(Color.White)
            .border(1.dp, Color.Black),
    ){

    }
}