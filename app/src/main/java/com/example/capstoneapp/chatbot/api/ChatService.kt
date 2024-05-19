package com.example.capstoneapp.chatbot.api

import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import java.time.LocalDateTime


public interface ChatService {
    @GET("/api/chat/test/ask")
    suspend fun askChatbotTest(@Query("question") question: String): Response<ChatResponse>

    @GET("/api/chat/test")
    suspend fun test(): Response<String>

    @GET("/api/chat/ask")
    suspend fun askChatbot(@Query("question") question: String): Response<ChatResponse>

    @Multipart
    @GET("/api/chat/transcription/ask")
    fun uploadAudioFile(@Part file: MultipartBody.Part): Call<WhisperTranscriptionResponse>

    @GET("/api/chat/ask")
    suspend fun askChatbotReset(
        @Query("question") question: String,
        @Query("reset") reset: Boolean
    ): Response<ChatResponse>

    @GET("/api/chat/list/test")
    suspend fun getChatListTest(): Response<List<ChatRoom>>
    @POST("/api/chat/reset")
    suspend fun resetChatbot(@Query("sessionId") sessionId: String): Response<Void>



}



object RetrofitInstance {
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://118.67.135.211:9000") // 백엔드 서버의 기본 URL을 설정하세요.
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }

    val api: ChatService by lazy {
        retrofit.create(ChatService::class.java)
    }


}


data class ChatResponse(
    val question: String
)

data class WhisperTranscriptionResponse(
    val text: String
)



data class ChatRoom(
    val message: String,
    val response: String,
    val createdAt: String, // String으로 변경
    val sessionId: String
)

