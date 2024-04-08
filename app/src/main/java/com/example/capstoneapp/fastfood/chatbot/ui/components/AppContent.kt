package com.example.capstoneapp.fastfood.chatbot.ui.components
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.capstoneapp.fastfood.chatbot.api.ChatService
import com.example.capstoneapp.fastfood.chatbot.api.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
@Composable
fun AppContent() {
    val chatService = RetrofitInstance.api // Assuming RetrofitInstance is accessible here
    var response by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        TestButton(chatService, response, errorMessage, coroutineScope) { newResponse, newErrorMessage ->
            response = newResponse
            errorMessage = newErrorMessage
        }

        if (errorMessage.isNotEmpty()) {
            Text("Error: $errorMessage", modifier = Modifier.padding(top = 8.dp))
        } else if (response.isNotEmpty()) {
            Text("Response: $response", modifier = Modifier.padding(top = 8.dp))
        }
    }
}
@Composable
fun TestButton(
    chatService: ChatService,
    response: String,
    errorMessage: String,
    coroutineScope: CoroutineScope,
    updateResponse: (String, String) -> Unit
) {
    Button(onClick = {
        coroutineScope.launch {
            try {
                val result: Response<String> = chatService.test() // `.execute()` 제거
                if (result.isSuccessful) {
                    updateResponse(result.body() ?: "응답이 비어있습니다.", "")
                } else {
                    updateResponse("", "Error: ${result.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                updateResponse("", e.localizedMessage ?: "알 수 없는 에러가 발생했습니다.")
                Log.e("ChatUI", "에러 발생", e)
            }
        }
    }) {
        Text("테스트")
    }
}

@Preview
@Composable
fun AppContentPreview() {
    AppContent()
}
