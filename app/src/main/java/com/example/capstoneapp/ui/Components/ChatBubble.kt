package com.example.capstoneapp.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.R
import com.example.capstoneapp.data.Repository.ChatMessage
import com.example.capstoneapp.data.Repository.ChatMessageRepository
import com.example.capstoneapp.ui.theme.LightYellow

@Composable
fun ChatDetail(chatMessages: List<ChatMessage>, listState: LazyListState) {
    LazyColumn(
        state = listState
    ) {
        itemsIndexed(chatMessages) { index, message ->
            ChatMessageItem(message)
        }
    }


}

@Composable
fun ChatMessageItem(chatMessage: ChatMessage) {

    val backgroundColor: Color
    val alignment: Alignment
    val shape: RoundedCornerShape
    val sender: String
    val padding: Dp

    if (chatMessage.who.equals("m")) {
        backgroundColor = LightYellow
        alignment = Alignment.CenterEnd
        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
        sender = ""
        padding = 0.dp
    } else {
        backgroundColor = Color.White
        alignment = Alignment.CenterStart
        shape = RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
        sender = chatMessage.name
        padding = 8.dp
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding),
        contentAlignment = alignment
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Row(modifier = Modifier.wrapContentSize()) {

                if (chatMessage.who == "m") {
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(36.dp)
                    ) {}
                } else {
                    Image(
                        painter = painterResource(R.drawable.kakaotalk_icon),
                        contentDescription = "profile",
                        modifier = Modifier
                            .width(36.dp)
                            .height(36.dp)
                            .padding(top = 8.dp),
                        alignment = Alignment.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                ) {
                    if (chatMessage.who != "m") {
                        Text(
                            text = sender, style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                color = backgroundColor, shape = shape
                            )
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    ) {
                        Text(
                            text = chatMessage.content,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier

                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ChatDetailPreview() {

    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    LaunchedEffect(Unit) {
        chatMessages.addAll(ChatMessageRepository.getSimpleChat())
    }
    val listState = rememberLazyListState()
    ChatDetail(chatMessages = chatMessages, listState)
}