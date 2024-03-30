package com.example.capstoneapp.kakatalk.ui.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.capstoneapp.R

@Composable
fun PhotoBox(boxSize: Dp,onClick:()->Unit) {
    Box(
        modifier = Modifier
            .height(boxSize)
            .width(400.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .height(76.dp)
                        .width(76.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(

                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .background(
                                color = Color("#87D459".toColorInt()),
                                shape = RoundedCornerShape(40.dp)
                            )
                    ) {
                        IconButton(onClick = onClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.gallery_icon),
                                contentDescription = "Favorite",
                                modifier = Modifier
                                    .width(48.dp)
                                    .height(48.dp),
                                tint = Color.White
                            )
                        }
                    }
                    Text(text = "앨범")
                }

            }
        }
    }
}

@Composable
fun photoBlock(boxSize: Dp,photoList : List<Int>){
    Box(modifier = Modifier
        .height(boxSize)
    ){
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = LazyListState()
        ){
            items(photoList){photo ->
                photoCard(photo)
            }

        }
    }
}

@Composable
fun photoCard(photoId: Int) {
    var buttonClick by rememberSaveable { mutableStateOf(false) }
    val ImageBoxcolor: Color
    val buttonColor: Color
    val buttonContainerColor: Color
    val border: Dp
    val icon: ImageVector
    var alpha = 1f

    if (buttonClick) {
        ImageBoxcolor = Color.Yellow
        buttonColor = Color.Yellow
        buttonContainerColor = Color.Yellow
        border = 4.dp
        icon = Icons.Filled.Favorite
        alpha = 0.5f
    } else {
        ImageBoxcolor = Color.Black
        buttonColor = Color.Gray
        buttonContainerColor = Color.White
        border = 1.dp
        icon = Icons.Filled.FavoriteBorder
        alpha = 1f
    }

    Box(
        modifier = Modifier
            .width(150.dp)
            .width(150.dp)
            .border(border, ImageBoxcolor)
    ) {

        Image(
            painter = painterResource(id = photoId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.alpha(alpha)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.TopEnd){
            OutlinedButton(
                onClick = {
                    buttonClick=true},
                border = BorderStroke(2.dp, buttonColor),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = buttonContainerColor),
            ) {

            }
        }
    }
}