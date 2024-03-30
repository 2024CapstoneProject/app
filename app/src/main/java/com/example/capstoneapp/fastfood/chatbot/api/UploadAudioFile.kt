package com.example.capstoneapp.fastfood.chatbot.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun uploadAudioFile(filePath: String) {
    val retrofit = Retrofit.Builder()
        .baseUrl("YOUR_BACKEND_SERVER_URL") // 여기에 백엔드 서버 URL을 입력하세요.
        .addConverterFactory(GsonConverterFactory.create())
        .build()

   /* val service = retrofit.create(ApiService::class.java)

    // 파일을 MultipartBody.Part 객체로 변환
    val file = File(filePath)
    val requestFile = file.asRequestBody("audio/3gp".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

    // 비동기적으로 파일 업로드
    service.uploadAudioFile(body).enqueue(object :
        WindowInsetsAnimation.Callback<WhisperTranscriptionResponse> {
        override fun onResponse(call: Call<WhisperTranscriptionResponse>, response: Response<WhisperTranscriptionResponse>) {
            if (response.isSuccessful) {
                // 성공적으로 파일을 업로드하고 응답을 받았을 때의 처리
                val transcriptionResponse = response.body()
                // 처리 로직...
            }
        }

        override fun onFailure(call: Call<WhisperTranscriptionResponse>, t: Throwable) {
            // 요청 실패 시의 처리
        }
    })*/
}
