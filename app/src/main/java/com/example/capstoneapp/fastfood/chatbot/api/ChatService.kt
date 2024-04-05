package com.example.capstoneapp.fastfood.chatbot.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


public interface ChatService {
    @GET("/api/chat/ask")
    fun askChatbot(@Query("question") question: String): Call<ChatResponse>

    @GET("/api/chat/test")
    fun test(): Call<String>


}


object RetrofitInstance {
    val api: ChatService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") // 백엔드 서버의 기본 URL을 설정하세요.
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환을 위해 GsonConverterFactory 사용
            .build()
            .create(ChatService::class.java)
    }
}

data class ChatResponse(
    val responseText: String
)

