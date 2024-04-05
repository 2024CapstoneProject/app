package com.example.capstoneapp.kakatalk.ui.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.capstoneapp.R
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessage
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessageRepository

@Composable
fun PhotoBox(boxSize: Dp, onClick: () -> Unit) {
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
fun photoBlock(boxSize: Dp, photoList: List<Int>, onNewPhotoMessage: (Int) -> Unit) {
    var isPhotoSelect by remember { mutableStateOf(false) }
    var selectedList = remember { mutableStateListOf<Int>() }

    Box(
        modifier = Modifier
            .height(boxSize)
            .border(
                0.dp, Color.Transparent, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f),
            state = LazyListState(),
        ) {
            items(photoList.size) { index ->
                photoCard(photoList[index], index) { photoId, buttonClick ->
                    if (selectedList.contains(photoId) && !buttonClick) {//선택했다 취소하는 경우
                        selectedList.remove(photoId)
                        if (selectedList.isEmpty()) isPhotoSelect = false
                    } else if (!selectedList.contains(photoId) && buttonClick) {//처음 선택하는 경우
                        selectedList.add(photoId)
                        isPhotoSelect = !isPhotoSelect
                        onNewPhotoMessage(selectedList.get(0))
                    } else if (selectedList.isEmpty()) { //아무것도 선택하지 않았으면 반드시 false
                        isPhotoSelect = false
                    }
                }
            }

        }
    }
}

@Composable
fun photoCard(photoId: Int, index: Int, onClick: (Int, Boolean) -> Unit) {
    var buttonClick by remember { mutableStateOf(false) }
    val ImageBoxcolor: Color
    val buttonColor: Color
    val buttonContainerColor: Color
    val border: Dp
    val icon: ImageVector
    var alpha = 1f

    if (buttonClick) {
        ImageBoxcolor = Color.Yellow
        buttonColor = Color.Gray
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

    val roundedShape =
        if (index == 0) RoundedCornerShape(bottomStart = 16.dp) else if (index == 2) RoundedCornerShape(
            bottomEnd = 16.dp
        ) else RoundedCornerShape(0.dp)

    Box(
        modifier = Modifier
            .width(132.dp)
            .fillMaxHeight(1f)
            .clip(roundedShape)
            .border(border, ImageBoxcolor)
    ) {

        Image(
            painter = painterResource(id = photoId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(border)
                .alpha(alpha)
                .clip(roundedShape),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), contentAlignment = Alignment.TopEnd
        ) {
            OutlinedButton(
                onClick = {
                    buttonClick = !buttonClick
                    onClick(photoId, buttonClick)
                },
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

@Preview
@Composable
fun photoBlockPreview() {
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    val photoList = remember { mutableStateListOf<Int>() }
    photoList.addAll(ChatMessageRepository.getPhotoList())

    photoBlock(132.dp, photoList) {

    }
}

@Preview
@Composable
fun PhotoBoxPreview() {
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    val photoList = remember { mutableStateListOf<Int>() }
    photoList.addAll(ChatMessageRepository.getPhotoList())

    PhotoBox(132.dp) {

    }
}