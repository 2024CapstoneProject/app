package com.example.capstoneapp.chatbot.ui.components

import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.capstoneapp.chatbot.api.AudioUploader
import com.example.capstoneapp.chatbot.api.ChatRoom

import com.example.capstoneapp.chatbot.api.ChatService
import com.example.capstoneapp.chatbot.api.RetrofitInstance
import com.example.capstoneapp.mainPage.VoicePopup
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
fun ChatUI(navController: NavController, chatService: ChatService) {
    val fontSize = 18.sp
    var question by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf("") }
    var showVoiceRecogPopup by remember { mutableStateOf(false) }
    val audioUploader = remember { AudioUploader(chatService) }


    // 초기 AI 메시지 설정
    val initialAiResponses = listOf(
        "안녕하세요! 키오스크 주문에 어려움을 겪고 계신가요?",
        "패스트푸드점 또는 카페에서의 주문을 도와드릴게요.",
        "주문을 원하시면 \"주문할래요\"를 입력해주세요."
    )

    var userMessages by remember { mutableStateOf(listOf<String>()) }
    var aiResponses by remember { mutableStateOf(initialAiResponses) }
    var sessionId by remember { mutableStateOf("") }


    val context = LocalContext.current
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    val speechIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR") // 한국어 설정
        }
    }
    val speechLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == ComponentActivity.RESULT_OK) {
            result.data?.let {
                val matches = it.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                matches?.let { resultList ->
                    if (resultList.isNotEmpty()) {
                        question = resultList[0]
                    }
                }
            }
        }
    }


    @Composable
    fun MessageBox(message: String, isUser: Boolean) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .background(
                    color = if (isUser) Color(0xFFD1E7FF) else Color(0xFFE6E6E6),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .widthIn(max = (LocalConfiguration.current.screenWidthDp.dp * 2 / 3))
        ) {
            Text(
                text = message,
                color = if (isUser) Color.Black else Color.Blue,
                fontSize = 18.sp
            )
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = chatService.getChatListTest()
                if (response.isSuccessful) {
                    response.body()?.let { chatRooms ->
                        val filteredChatRooms = if (chatRooms.isNotEmpty() && chatRooms[0].message == "대화 새 시작") {
                            chatRooms.drop(1)  // 첫 번째 메시지가 "대화 새 시작"이면 제외
                        } else {
                            chatRooms
                        }
                        userMessages = filteredChatRooms.map { it.message }
                        aiResponses = filteredChatRooms.map { it.response }
                        if (filteredChatRooms.isNotEmpty()) {
                            sessionId = filteredChatRooms[0].sessionId
                        }
                    }
                    errorMessage = ""
                } else {
                    errorMessage = "Error: ${response.errorBody()?.string()}"
                }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "알 수 없는 에러가 발생했습니다."
                Log.e("ChatUI", "Error occurred", e)
            }
        }
    }



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
            // 사용자 메시지와 AI 응답 추가
            items(userMessages.size) { index ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // MessageBox 컴포저블 사용
                        MessageBox(message = userMessages[index], isUser = true)
                    }
                    if (aiResponses.size > index) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            // MessageBox 컴포저블 사용
                            MessageBox(message = aiResponses[index], isUser = false)
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
                if (question.isNotBlank()) {
                    coroutineScope.launch {
                        try {
                            val userMessage = question
                            userMessages = userMessages + userMessage
                            question = "" // Clear the input field immediately
                            val reset = userMessages.isEmpty() && aiResponses.isEmpty() // 기존 대화가 없는 경우 reset = true
                            Log.e("reset", reset.toString())
                            val chatResponse = chatService.askChatbotReset(userMessage, reset)
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
                }
            })
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { speechLauncher.launch(speechIntent) }, // 음성 검색 버튼 클릭 시 음성 인식 시작
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text("음성 검색", fontSize = fontSize)
            }

            Spacer(Modifier.width(32.dp))

            Button(
                onClick = {
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
                },
                enabled = question.isNotBlank(),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text("질문 전송", fontSize = fontSize)
            }
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val resetResponse = chatService.resetChatbot(sessionId)
                        if (resetResponse.isSuccessful) {
                            userMessages = listOf()
                            aiResponses = listOf()
                            val chatResponse = chatService.askChatbotReset("대화 새 시작", true);
                        } else {
                            errorMessage = "Error: ${resetResponse.errorBody()?.string()}"
                            Log.e("ChatUI", "Response error body: ${resetResponse.errorBody()?.string()}")
                        }
                    } catch (e: Exception) {
                        errorMessage = e.localizedMessage ?: "알 수 없는 에러가 발생했습니다."
                        Log.e("ChatUI", "Error occurred", e)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("새 대화 하기", fontSize = fontSize)
        }



        if (errorMessage.isNotEmpty()) {
            Text(
                text = "Error: $errorMessage",
                fontSize = fontSize,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (showVoiceRecogPopup) {
            VoicePopup(
                showDialog = showVoiceRecogPopup,
                onDismiss = {
                    println("AI 도우미 팝업 닫힘")
                    showVoiceRecogPopup = false
                },
                audioUploader = audioUploader
            )
        }
    }


}


