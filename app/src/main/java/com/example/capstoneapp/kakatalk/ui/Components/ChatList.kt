package com.example.capstoneapp.kakatalk.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.kakatalk.data.Repository.ChatItemData
import com.example.capstoneapp.nav.repository.KakaotalkProblem

@Composable
fun ChatList(
    navController: NavController,
    chatData: List<ChatItemData>,
    listState: LazyListState,
    showBorder: Boolean,
    problem: KakaotalkProblem,
    checkAnswer: (String) -> Unit
) {
    LazyColumn(
        state = listState, modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(chatData) { index, item ->
            ChatItem(
                chatItem = item,
                onItemClick = {
                    checkAnswer(it.name)
                    //navController.navigate("ChattingScreen")
                },
                showBorder = showBorder && (problem.person == item.name)
            )
        }
    }
}


@Composable
fun ChatItem(
    chatItem: ChatItemData,
    onItemClick: (ChatItemData) -> Unit,
    showBorder: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick(chatItem) }
            .then(
                if (showBorder) Modifier.border(
                    BorderWidth,
                    BorderColor,
                    BorderShape
                ) else Modifier
            ), // 클릭 이벤트 추가
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 이미지
        Image(
            painter = painterResource(id = chatItem.image),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp)
                .padding(end = 16.dp),
        )

        // 이름 및 대화 내용
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //이름
                Text(
                    text = chatItem.name,
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                // 날짜
                Text(
                    text = chatItem.date,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }
            //메세지
            Text(
                text = chatItem.message,
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ChatListPreview() {
//    val listState = rememberLazyListState()
//    val chatData = remember { mutableStateListOf<ChatItemData>() }
//    chatData.addAll(FriendChatRoomRepository.getchatData())
//    val navController = rememberNavController()
//
//    ChatList(navController, chatData, listState, true)
}