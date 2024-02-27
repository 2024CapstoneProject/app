package com.example.capstoneapp.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectedMenuSpec(selectedMenuName:String){

    Box(
        modifier = Modifier
            .height(32.dp)
            .width(230.dp),
        contentAlignment = Alignment.Center
    ){

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ){
            IconButton(
                modifier = Modifier.size(28.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "clear",
                    modifier = Modifier
                        .size(28.dp)
                        .padding(4.dp)
                )
            }
            Text(
                text=selectedMenuName,
                modifier = Modifier.width(120.dp),
                textAlign = TextAlign.Center
            )
            IconButton(
                modifier = Modifier.size(28.dp),
                onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .size(28.dp)
                        .padding(4.dp)
                )
            }
            Text(text="10",
                modifier = Modifier,
                textAlign = TextAlign.Center)
            IconButton(
                modifier = Modifier.size(32.dp),
                onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SelectedMenuSpecPreview(){
    SelectedMenuSpec("아이스 아메리카노")
}

data class MenuSpec(
    val name:String,
    val count:Int
)
