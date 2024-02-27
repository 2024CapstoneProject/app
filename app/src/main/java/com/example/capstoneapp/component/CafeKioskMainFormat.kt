package com.example.capstoneapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CafeKioskMainFormat(bar:@Composable () -> Unit, content:@Composable () -> Unit){
        Column(
            //horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Fill the width of the parent
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
                    .border(0.dp, Color.Black, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) ,// Border
                contentAlignment = Alignment.Center
            ){
                bar()
            }
            //Spacer(modifier=Modifier.weight(1f))
            content()
        }
}