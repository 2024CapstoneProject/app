package com.example.capstoneapp.chatbot.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface ChatService {
    @GET("/api/chat/ask")
    fun askChatbot(@Query("question") question: String): Call<ChatResponse>
}


object RetrofitInstance {
    val api: ChatService by lazy {
        Retrofit.Builder()
            .baseUrl("http://localhost:8080") // 백엔드 서버의 기본 URL을 설정하세요.
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환을 위해 GsonConverterFactory 사용
            .build()
            .create(ChatService::class.java)
    }
}

data class ChatResponse(
    val responseText: String
)