package com.example.capstoneapp.kakatalk.data.Repository

import com.example.capstoneapp.R
object FriendChatRoomRepository {

    private val chatData = listOf(
        ChatItemData(
            image = R.drawable.kakaotalk_icon,
            name = "이소똥",
            message = "뭐해? 자니....?",
            date = "2024-04-01"
        ),
        ChatItemData(
            image = R.drawable.kakaotalk_icon,
            name = "아들",
            message = "넵",
            date = "2024-03-31"
        ),
        ChatItemData(
            image = R.drawable.kakaotalk_icon,
            name = "규세경",
            message = "배고파",
            date = "2024-03-30"
        ),
        ChatItemData(
            image = R.drawable.kakaotalk_icon,
            name = "규세경",
            message = "배고파",
            date = "2024-03-30"
        ),
        ChatItemData(
            image = R.drawable.kakaotalk_icon,
            name = "규세경",
            message = "배고파",
            date = "2024-03-30"
        ),
    )

    private val friendList = listOf(
        Pair(R.drawable.sample_1,"아내"),
        Pair(R.drawable.sample_2,"남편"),
        Pair(R.drawable.burgerimg,"아들"),
        Pair(R.drawable.burgerimg,"딸")
    )

    fun getchatData() : List<ChatItemData>{
        return chatData
    }

    fun getfriendList() :  List<Pair<Int, String>>{
        return friendList
    }
}

data class ChatItemData(
    val image: Int,
    val name: String,
    val message: String,
    val date: String
)