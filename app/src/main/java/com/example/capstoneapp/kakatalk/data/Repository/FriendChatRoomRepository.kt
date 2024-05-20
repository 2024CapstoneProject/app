package com.example.capstoneapp.kakatalk.data.Repository

import com.example.capstoneapp.R
import com.example.capstoneapp.nav.repository.KakaotalkProblem

object FriendChatRoomRepository {

    private val chatData = listOf(
        ChatItemData(
            image = R.drawable.profile_husband,
            name = "남편",
            message = "어디야?",
            date = "2024-04-01"
        ),
        ChatItemData(
            image = R.drawable.profile_son,
            name = "아들",
            message = "그래",
            date = "2024-03-31"
        ),
        ChatItemData(
            image = R.drawable.profile_daughter,
            name = "딸",
            message = "어디 아픈 데 없으시죠?",
            date = "2024-03-30"
        ),
        ChatItemData(
            image = R.drawable.profile_granddaughter,
            name = "손녀",
            message = "사랑해요!",
            date = "2024-03-30"
        ),
        ChatItemData(
            image = R.drawable.profile_me,
            name = "김희연",
            message = "",
            date = "2024-03-30"
        ),
    )

    private val friendList = listOf(
        Pair(R.drawable.profile_husband, "남편"),
        Pair(R.drawable.profile_granddaughter, "손녀"),
        Pair(R.drawable.profile_son, "아들"),
        Pair(R.drawable.profile_daughter, "딸")
    )

    fun getchatData(problem: KakaotalkProblem): List<ChatItemData> {

        val chatList: List<ChatMessage>

        if (problem.type == "simple") {
            chatList = ChatMessageRepository.getSimpleChat(
                person = problem.person,
                problemNumber = problem.index
            )

        } else {
            chatList = ChatMessageRepository.getPhotoSend(
                person = problem.person,
                problemNumber = problem.index
            )
        }
        problemChatData(
            person = problem.person,
            message = chatList[chatList.size - 1].content
        )


        return chatData
    }

    fun getfriendList(): List<Pair<Int, String>> {
        return friendList
    }

    fun problemChatData(
        person: String,
        message: String
    ) {
        for (i in 0..chatData.size - 1) {
            if (chatData[i].name == person) {
                chatData[i].message = message
            }
        }
    }
}

data class ChatItemData(
    val image: Int,
    val name: String,
    var message: String,
    val date: String
)