package com.example.capstoneapp.chatbot.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.chatbot.api.ChatService
import com.example.capstoneapp.chatbot.api.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
fun ChatUI(chatService: ChatService) {
    val fontSize = 18.sp
    var question by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf("") }

    // 초기 AI 메시지 설정
    val initialAiResponses = listOf(
        "안녕하세요! 키오스크 주문에 어려움을 겪고 계신가요?",
        "패스트푸드점 또는 카페에서의 주문을 도와드릴게요.",
        "주문을 원하시면 \"주문할래요\"를 입력해주세요."
    )

    var userMessages by remember { mutableStateOf(listOf<String>()) }
    var aiResponses by remember { mutableStateOf(initialAiResponses) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            // AI 초기 응답 추가
            items(initialAiResponses.size) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "AI\n${initialAiResponses[index]}",
                        fontSize = fontSize,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .widthIn(max = (LocalConfiguration.current.screenWidthDp.dp * 2 / 3)),
                        color = Color.Blue
                    )
                }
            }

            // 사용자 메시지와 AI 응답 추가
            items(userMessages.size) { index ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "User\n${userMessages[index]}",
                            fontSize = fontSize,
                            modifier = Modifier
                                .padding(bottom = 4.dp)
                                .widthIn(max = (LocalConfiguration.current.screenWidthDp.dp * 2 / 3)),
                            color = Color.Black
                        )
                    }
                    if (aiResponses.size > index + initialAiResponses.size) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "AI\n${aiResponses[index + initialAiResponses.size]}",
                                fontSize = fontSize,
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .widthIn(max = (LocalConfiguration.current.screenWidthDp.dp * 2 / 3)),
                                color = Color.Blue
                            )
                        }
                    }
                }
            }
        }

        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("질문 입력", fontSize = fontSize) },
            textStyle = LocalTextStyle.current.copy(fontSize = fontSize),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                coroutineScope.launch {
                    try {
                        val userMessage = question
                        userMessages = userMessages + userMessage
                        question = "" // Clear the input field immediately
                        val chatResponse = chatService.askChatbot(userMessage)
                        if (chatResponse.isSuccessful) {
                            aiResponses = aiResponses + (chatResponse.body()?.question ?: "응답을 받지 못했습니다.")
                            errorMessage = ""
                        } else {
                            errorMessage = "Error: ${chatResponse.errorBody()?.string()}"
                        }
                    } catch (e: Exception) {
                        errorMessage = e.localizedMessage ?: "알 수 없는 에러가 발생했습니다."
                        Log.e("ChatUI", "Error occurred", e)
                    }
                }
            })
        )

        Button(onClick = {
            coroutineScope.launch {
                try {
                    val userMessage = question
                    userMessages = userMessages + userMessage
                    question = "" // Clear the input field immediately
                    val chatResponse = chatService.askChatbot(userMessage)
                    if (chatResponse.isSuccessful) {
                        aiResponses = aiResponses + (chatResponse.body()?.question ?: "응답을 받지 못했습니다.")
                        errorMessage = ""
                    } else {
                        errorMessage = "Error: ${chatResponse.errorBody()?.string()}"
                    }
                } catch (e: Exception) {
                    errorMessage = e.localizedMessage ?: "알 수 없는 에러가 발생했습니다."
                    Log.e("ChatUI", "Error occurred", e)
                }
            }
        }) {
            Text("질문 전송", fontSize = fontSize)
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = "Error: $errorMessage",
                fontSize = fontSize,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatUIPreview() {
    ChatUI(RetrofitInstance.api)
}