package com.example.capstoneapp.kakatalk.data.Repository

import com.example.capstoneapp.R

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

    private val photoList = listOf(
        R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3
    )

    fun getSimpleChat() : List<ChatMessage>{
        return simpleChatMission
    }

    fun getPhotoSend() : List<ChatMessage>{
        return photoSendMission
    }

    fun getPhotoList(): List<Int>{
        return photoList
    }
}

data class ChatMessage(
    val who: String,
    val name: String,
    val content: String,
    val timestamp: String,
)
