package com.example.capstoneapp.chatbot.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AudioUploader(private val apiService: ChatService) {
        fun uploadAudioFile(audioFile: File) {
            // Create RequestBody instance from file
            val requestBody = audioFile.asRequestBody("audio/3gp".toMediaTypeOrNull())

            // MultipartBody.Part is used to send also the actual file name
            val filePart = MultipartBody.Part.createFormData("file", audioFile.name, requestBody)

            // Enqueue the retrofit call
            apiService.uploadAudioFile(filePart).enqueue(object :
                Callback<WhisperTranscriptionResponse> {
                override fun onResponse(call: Call<WhisperTranscriptionResponse>, response: Response<WhisperTranscriptionResponse>) {
                    if (response.isSuccessful) {
                        println("File uploaded successfully: ${response.body()?.text}")
                    } else {
                        println("Failed to upload file: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<WhisperTranscriptionResponse>, t: Throwable) {
                    println("Error uploading file: ${t.message}")
                }
            })
        }
    }
// Setup Retrofit instance and service

