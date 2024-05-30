package com.example.capstoneapp.auth


import com.google.gson.GsonBuilder
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query


interface UserService {

    @POST("/api/auth/login")
    suspend fun loginUser(
        @Query("uid") uid: String,
        @Query("email") email: String
    ): Response<Void>


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

        val api: UserService by lazy {
            retrofit.create(UserService::class.java)
        }
    }
}