package com.example.capstoneapp.chatbot.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.capstoneapp.chatbot.api.ChatService
import com.example.capstoneapp.chatbot.api.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
fun ChatUI(chatService: ChatService) {
    var question by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("AI의 응답이 여기에 표시됩니다.") }
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("질문 입력") },
            modifier = Modifier.padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                coroutineScope.launch {
                    try {
                        val chatResponse = RetrofitInstance.api.askChatbotTest(question)
                        if (chatResponse.isSuccessful) {
                            response = chatResponse.body()?.question ?: "응답을 받지 못했습니다."
                            errorMessage = "" // 성공 시 에러 메시지 클리어
                        } else {
                            errorMessage = "Error: ${chatResponse.errorBody()?.string()}"
                        }
                    } catch (e: Exception) {
                        errorMessage = e.localizedMessage ?: "알 수 없는 에러가 발생했습니다."
                    }
                }
            })
        )

        Button(onClick = {
            coroutineScope.launch {
                try {
                    val chatResponse = RetrofitInstance.api.askChatbotTest(question)
                    if (chatResponse.isSuccessful) {
                        response = chatResponse.body()?.question ?: "응답을 받지 못했습니다."
                        errorMessage = "" // 성공 시 에러 메시지 클리어
                    } else {
                        errorMessage = "Error: ${chatResponse.errorBody()?.string()}"
                    }
                } catch (e: Exception) {
                    errorMessage = e.localizedMessage ?: "알 수 없는 에러가 발생했습니다."
                    Log.e("ChatUI", "에러 발생", e)
                }
            }
        }) {
            Text("질문 전송")
        }

        if (errorMessage.isNotEmpty()) {
            Text("Error: $errorMessage", color = Color.Red)
        } else if (response.isNotEmpty()) {
            Text("Response: $response")
        }
    }


        Button(onClick = {
            coroutineScope.launch {
                try {
                    val result: Response<String> = chatService.test()
                    if (result.isSuccessful) {
                        response = result.body() ?: "응답이 비어있습니다."
                        errorMessage = "" // 성공 시 에러 메시지 클리어
                    } else {
                        errorMessage = "Error: ${result.errorBody()?.string()}"
                    }
                } catch (e: Exception) {
                    errorMessage = e.localizedMessage ?: "알 수 없는 에러가 발생했습니다."
                    Log.e("ChatUI", "에러 발생", e)
                }
            }
        }) {
            Text("테스트")
        }

        // 응답과 에러 메시지를 화면에 표시
        Text(text = response, modifier = Modifier.padding(top = 8.dp))
        if (errorMessage.isNotEmpty()) {
            Text(text = "Error: $errorMessage", color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }





        if (errorMessage.isNotEmpty()) {
            Text(text = "Error: $errorMessage", color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        } else {
            Text(text = response, modifier = Modifier.padding(top = 8.dp))
        }
    }


@Preview
@Composable
fun ChatUIPreview() {
    ChatUI(RetrofitInstance.api)
}