package com.example.capstoneapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CafeMenuBar(){
    Row(
        modifier= Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically

    ){
        //Spacer(Modifier.width(20.dp))
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(60.dp)
                .height(64.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "setting")
        }
        //Spacer(Modifier.width(20.dp))
        TextButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text="커피(HOT)", color= Color.Black,fontSize=15.sp)
        }
        //Spacer(Modifier.width(10.dp))
        TextButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text="커피(ICE)",color= Color.Black,fontSize=15.sp)
        }
        //Spacer(Modifier.width(10.dp))
        TextButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text="티(TEA)",color= Color.Black,fontSize=15.sp)
        }
        Spacer(Modifier.width(10.dp))
    }
}