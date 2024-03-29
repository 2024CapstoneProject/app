package com.example.capstoneapp.cafe.data.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CafeMenuBarFormat(bar:@Composable () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth(
            ) // Fill the width of the parent
            .padding() // Padding from the left and right
            // Height to wrap content
            .fillMaxHeight(0.11f) // Fill 50% of the height of the parent
            .background(
                color = Color(0xffff602e), // Change this color to your desired background color
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                ) // Rounded corners
            )
            .border(0.dp, Color.Transparent, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) ,// Border
        contentAlignment = Alignment.Center
    ){
        bar()
    }
}