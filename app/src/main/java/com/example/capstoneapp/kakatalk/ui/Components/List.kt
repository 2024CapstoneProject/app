package com.example.capstoneapp.kakatalk.ui.Components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController

@Composable
fun List(
    navController: NavController,
    chatData: List<ChatItemData>,
    onItemClick: (ChatItemData) -> Unit
) {
    Column {
        chatData.forEachIndexed { index, chatItem ->
            ChatItem(
                chatItem = chatItem,
                onItemClick = {
                    if (index == 1) {
                        //navController.navigate("KakaoGuide0")
                    }
                }
            )
        }
    }
}

@Composable
fun ChatItem(
    chatItem: ChatItemData,
    onItemClick: (ChatItemData) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick(chatItem) }, // 클릭 이벤트 추가
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 이미지
        Image(
            painter = chatItem.image,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp)
                .padding(end = 16.dp),
        )

        // 이름 및 대화 내용
        Column {
            Text(text = chatItem.name)
            Text(text = chatItem.message, textAlign = TextAlign.Start)
        }

        // 날짜
        Text(
            text = chatItem.date,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
    }
}

data class ChatItemData(
    val image: Painter,
    val name: String,
    val message: String,
    val date: String
)
