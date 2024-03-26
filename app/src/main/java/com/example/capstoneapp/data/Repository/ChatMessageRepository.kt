package com.example.capstoneapp.data.Repository

import kotlin.random.Random

object ChatMessageRepository {

    private val simpleChatMission = listOf(
        ChatMessage("m","나","아들 언제와?","4:00 PM"),
        ChatMessage("o","아들","곧이요.","4:01 PM"),
        ChatMessage("o","아들","오늘 메뉴가 뭐에요?","4:01 PM"),
        ChatMessage("m","나","경닭발","4:02 PM"),
    )

    private val photoSendMission = listOf(
        ChatMessage("m","나","옷가게 왔는데 이거 사줄까?","4:00 PM"),
        ChatMessage("o","아들","사진 보내주세요","4:01 PM"),
    )

    fun getSimpleChat() : List<ChatMessage>{
        return simpleChatMission
    }

    fun getPhotoSend() : List<ChatMessage>{
        return photoSendMission
    }
}

data class ChatMessage(
    val who: String,
    val name: String,
    val content: String,
    val timestamp: String,
)