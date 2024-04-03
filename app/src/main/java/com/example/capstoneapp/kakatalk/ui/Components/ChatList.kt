package com.example.capstoneapp.kakatalk.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R

@Composable
fun ChatList(){
    val navController = rememberNavController()

    // 예시 데이터
    val chatData = listOf(
        ChatItemData(
            image = painterResource(id = R.drawable.kakaotalk_icon),
            name = "이소똥",
            message = "뭐해? 자니....?",
            date = "2024-04-01"
        ),
        ChatItemData(
            image = painterResource(id = R.drawable.kakaotalk_icon),
            name = "아들",
            message = "넵",
            date = "2024-03-31"
        ),
        ChatItemData(
            image = painterResource(id = R.drawable.kakaotalk_icon),
            name = "규세경",
            message = "배고파",
            date = "2024-03-30"
        ),
        ChatItemData(
            image = painterResource(id = R.drawable.kakaotalk_icon),
            name = "규세경",
            message = "배고파",
            date = "2024-03-30"
        ),
        ChatItemData(
            image = painterResource(id = R.drawable.kakaotalk_icon),
            name = "규세경",
            message = "배고파",
            date = "2024-03-30"
        ),
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.top),
            contentDescription = "상단바",
            modifier = Modifier
                .size(width = 400.dp, height = 60.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        List(navController = navController, chatData = chatData) {}
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.bottom),
            contentDescription = "하단바",
            modifier = Modifier
                .size(width = 400.dp, height = 60.dp),
        )
    }
}
@Preview
@Composable
fun ChatListPreview(){
    ChatList()
}