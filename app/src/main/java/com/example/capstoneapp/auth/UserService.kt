package com.example.capstoneapp.auth

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Header

interface UserService {

    @POST("api/sendToken")
    fun sendToken(@Body token: String): Call<Void>

    @POST("api/validateToken")
    fun validateToken(@Header("Authorization") token: String): Call<Boolean>

    companion object {
        fun create(): UserService {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://118.67.135.211:9000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(UserService::class.java)
        }
    }
}