// 파일: ChatUI.kt
package com.example.capstoneapp.chatbot.ui.components


import AnswerDialog
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.chatbot.api.AudioUploader
import com.example.capstoneapp.chatbot.api.ChatResponse
import com.example.capstoneapp.chatbot.api.ChatRoom
import com.example.capstoneapp.chatbot.api.ChatService
import com.example.capstoneapp.chatbot.api.WhisperTranscriptionResponse
import com.example.capstoneapp.chatbot.utils.TtsPlaybackHandler
import com.example.capstoneapp.fastfood.ui.frame.ButtonFormat
import com.example.capstoneapp.fastfood.ui.theme.LightYellow
import com.example.capstoneapp.fastfood.ui.theme.OutraGeousOrange
import com.example.capstoneapp.fastfood.ui.theme.Yellow
import com.example.capstoneapp.mainPage.VoicePopup
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.texttospeech.v1.AudioConfig
import com.google.cloud.texttospeech.v1.AudioEncoding
import com.google.cloud.texttospeech.v1.SsmlVoiceGender
import com.google.cloud.texttospeech.v1.SynthesisInput
import com.google.cloud.texttospeech.v1.TextToSpeechClient
import com.google.cloud.texttospeech.v1.TextToSpeechSettings
import com.google.cloud.texttospeech.v1.VoiceSelectionParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import java.io.InputStream

@Composable
fun ChatUI(navController: NavController, chatService: ChatService) {
    val fontSize = remember { mutableStateOf(21.sp) }
    var question by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf("") }
    var showVoiceRecogPopup by remember { mutableStateOf(false) }
    val audioUploader = remember { AudioUploader(chatService) }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var currentResponse by remember { mutableStateOf("") }
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val uid = sharedPreferences.getString("user_uid", null)

    // 초기 AI 메시지 설정
    val initialAiResponses = listOf(
        "첫번째, 식사장소가 매장인지 포장인지를 선택하세요\n" +
                "두번째, 화면 왼쪽열에서 버거를 선택하세요. 화면 오른쪽에 있는 흰색막대기를 위아래로 움직이면서 불고기 버거 이미지를 찾으면 됩니다.\n" +
                "세번째, 세트를 선택하세요. 기본크기의 사이드와 음료를 먹고 싶으면 세트를, 더 많이 먹고 싶으면 라지를 선택해주세요.",
        "패스트푸드점 또는 카페에서의 주문을 도와드릴게요.",
        "주문을 원하시면 \"주문할래요\"를 입력해주세요."
    )

    var userMessages by remember { mutableStateOf(listOf<String>()) }
    var aiResponses by remember { mutableStateOf(initialAiResponses) }
    var sessionId by remember { mutableStateOf("") }

    // Only create the SpeechRecognizer if not in preview mode
    // Initialize the speech recognizer only if not in edit mode
 /*   val speechRecognizer = if (!isInEditMode()) SpeechRecognizer.createSpeechRecognizer(context) else null
    val speechIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR") // 한국어 설정
        }
    }
   /* val speechLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (!isInEditMode() && result.resultCode == ComponentActivity.RESULT_OK) {
                result.data?.let {
                    val matches = it.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    matches?.let { resultList ->
                        if (resultList.isNotEmpty()) {
                            question = resultList[0]
                        }
                    }
                }
            }
        }*/
*/
    val ttsClient = remember {
        val credentialsStream: InputStream =
            context.resources.openRawResource(R.raw.service_account_key) // 서비스 계정 키 파일
        val credentials = GoogleCredentials.fromStream(credentialsStream)
        val settings = TextToSpeechSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .build()
        TextToSpeechClient.create(settings)
    }

    val ttsPlaybackHandler = remember {
        TtsPlaybackHandler(context, ttsClient, coroutineScope)
    }



    @Composable
    fun MessageBox(
        message: String,
        isUser: Boolean,
        fontSize: TextUnit,
        onClick: () -> Unit = {}
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .background(
                    color = if (isUser) Color(0xFFD1E7FF) else LightYellow,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .widthIn(max = (LocalConfiguration.current.screenWidthDp.dp * 2 / 3))
                .clickable { if (!isUser) onClick() }
        ) {
            Text(
                text = message,
                color = if (isUser) Color.Blue else Color.Black,
                fontSize = fontSize
            )
        }
    }



    LaunchedEffect(Unit) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("user_uid", null)
        Log.i("ChatUI", "UID: $uid")
        coroutineScope.launch {
            try {

                val response = chatService.getChatList(uid ?: "test")
                if (response.isSuccessful) {
                    response.body()?.let { chatRooms ->
                        val filteredChatRooms =
                            if (chatRooms.isNotEmpty() && chatRooms[0].message == "대화 새 시작") {
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
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        // Navigation Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp), // Adjust padding as needed
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
            Text(
                text = "AI 도우미",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    /*    Text(
            text = "폰트 크기 조절:",
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Slider(
            value = fontSize.value.value,
            onValueChange = { fontSize.value = it.sp },
            valueRange = 12f..30f,
            modifier = Modifier.padding(horizontal = 16.dp)
        )*/
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            items(userMessages.size) { index ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        MessageBox(
                            message = userMessages[index],
                            isUser = true,
                            fontSize = fontSize.value
                        )
                    }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            AnswerDialog(
                                responseText = aiResponses[index],
                               // onDismiss = { showDialog = true },

                                onReplay = { textToRead ->
                                    coroutineScope.launch {
                                        if (textToRead.isNotEmpty()) {
                                            ttsPlaybackHandler.playText(textToRead)
                                        }
                                    }
                                }
                            )
                        }

                }
            }
        }

    /*    if (showDialog) {
            AnswerDialog(
                responseText = currentResponse,
             //   onDismiss = { showDialog = false },
                onReplay = { textToRead ->
                    coroutineScope.launch {
                        if (textToRead.isNotEmpty()) {
                            ttsPlaybackHandler.playText(textToRead)
                        }
                    }
                }
            )
        }*/

        MessageInputField(
            question = question,
            onQuestionChange = { question = it },
            onSendClick = {
                coroutineScope.launch {
                    try {
                        val userMessage = question
                        userMessages = userMessages + userMessage
                        aiResponses = aiResponses + "답변 중..."
                        question = "" // Clear the input field immediately
                        val reset = userMessages.isEmpty() && aiResponses.isEmpty() // 기존 대화가 없는 경우 reset = true
                        Log.e("reset", reset.toString())

                        val chatResponse = chatService.askChatbotReset(userMessage, reset, uid ?: "test")

                        if (chatResponse.isSuccessful) {
                            val newResponse = chatResponse.body()?.question ?: "응답을 받지 못했습니다."
                            aiResponses = aiResponses.dropLast(1) + newResponse
                            if (newResponse.split("\n").size > 3) {
                                currentResponse = newResponse
                                showDialog = true
                            }
                            errorMessage = ""
                        } else {
                            aiResponses = aiResponses.dropLast(1) + "Error: ${chatResponse.errorBody()?.string()}"
                            errorMessage = "Error: ${chatResponse.errorBody()?.string()}"
                        }
                    } catch (e: Exception) {
                        aiResponses = aiResponses.dropLast(1) + "알 수 없는 에러가 발생했습니다."
                        errorMessage = e.localizedMessage ?: "알 수 없는 에러가 발생했습니다."
                        Log.e("ChatUI", "Error occurred", e)
                    }
                }
            },
        )

        Spacer(modifier = Modifier.height(16.dp)) // Add spacer to create gap

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(Modifier.height(16.dp))

            ButtonFormat(
                onClick = {
                    coroutineScope.launch {
                        try {
                            val resetResponse = chatService.resetChatbot(uid?:"test")
                            if (resetResponse.isSuccessful) {
                                userMessages = listOf()
                                aiResponses = listOf()
                                val chatResponse = chatService.askChatbotReset("대화 새 시작", true,uid?:"test")
                            } else {
                                errorMessage = "Error: ${resetResponse.errorBody()?.string()}"
                                Log.e(
                                    "ChatUI",
                                    "Response error body: ${resetResponse.errorBody()?.string()}"
                                )
                            }
                        } catch (e: Exception) {
                            errorMessage = e.localizedMessage ?: "알 수 없는 에러가 발생했습니다."
                            Log.e("ChatUI", "Error occurred", e)
                        }
                    }
                },
                buttonText = "새 대화하기",
                backgroundColor = Yellow,
                contentColor = Color.Black,
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(Modifier.width(24.dp))

            ButtonFormat(
                //  onClick = { speechLauncher.launch(speechIntent) }, // 음성 검색 버튼 클릭 시 음성 인식 시작
                onClick = {},
                buttonText = "말하기",
                backgroundColor = LightYellow,
                contentColor = Color.Black,
                modifier = Modifier
                    .weight(1f)

            )


        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = "Error: $errorMessage",
                fontSize = 16.sp,
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

        DisposableEffect(Unit) {
            onDispose {
                ttsClient.shutdown()
            }
        }

    }
}// Add your existing utility function
fun isInEditMode(): Boolean {
    return "com.android.tools.idea".equals(System.getProperty("java.vendor"))
}



@Preview(showBackground = true)
@Composable
fun ChatUIPreview() {
    // 더미 ChatService 생성
    val dummyChatService = object : ChatService {
        override suspend fun getChatList(uid: String): retrofit2.Response<List<ChatRoom>> {
            // Dummy chat room data for preview
            val dummyChatRooms = listOf(
                ChatRoom(
                    message = "사용자: 매장 내 주문은 어떻게 하나요?",
                    response = "첫번째, 식사장소가 매장인지 포장인지를 선택하세요\n두번째, 화면 왼쪽열에서 버거를 선택하세요. 화면 오른쪽에 있는 흰색막대기를 위아래로 움직이면서 불고기 버거 이미지를 찾으면 됩니다.\n세번째, 세트를 선택하세요. 기본크기의 사이드와 음료를 먹고 싶으면 세트를, 더 많이 먹고 싶으면 라지를 선택해주세요.",
                    createdAt = "2024-08-08T10:00:00", // Mock date/time
                    sessionId = "session_12345" // Mock session ID
                ),
                ChatRoom(
                    message = "사용자: 매장 내 주문은 어떻게 하나요?",
                    response = "첫번째, 식사장소가 매장인지 포장인지를 선택하세요\n두번째, 화면 왼쪽열에서 버거를 선택하세요. 화면 오른쪽에 있는 흰색막대기를 위아래로 움직이면서 불고기 버거 이미지를 찾으면 됩니다.\n세번째, 세트를 선택하세요. 기본크기의 사이드와 음료를 먹고 싶으면 세트를, 더 많이 먹고 싶으면 라지를 선택해주세요.",
                    createdAt = "2024-08-08T10:00:00", // Mock date/time
                    sessionId = "session_12345" // Mock session ID
                )
            )
            return retrofit2.Response.success(dummyChatRooms)
        }

        override suspend fun askChatbotReset(
            question: String,
            reset: Boolean,
            uid: String
        ): retrofit2.Response<ChatResponse> {
            return retrofit2.Response.success(ChatResponse("Dummy response"))
        }

        override suspend fun getChatListTest(uid: String): retrofit2.Response<List<ChatRoom>> {
            return retrofit2.Response.success(emptyList())
        }

        override suspend fun resetChatbot(uid: String): retrofit2.Response<Void> {
            return retrofit2.Response.success(null)
        }

        override fun uploadAudioFile(file: MultipartBody.Part): Call<WhisperTranscriptionResponse> {
            return object : Call<WhisperTranscriptionResponse> {
                override fun execute(): retrofit2.Response<WhisperTranscriptionResponse> {
                    return retrofit2.Response.success(WhisperTranscriptionResponse("Dummy transcription"))
                }

                override fun enqueue(callback: Callback<WhisperTranscriptionResponse>) {
                    callback.onResponse(
                        this,
                        retrofit2.Response.success(WhisperTranscriptionResponse("Dummy transcription"))
                    )
                }

                override fun isExecuted(): Boolean = false
                override fun cancel() {}
                override fun isCanceled(): Boolean = false

                override fun request(): Request {
                    return Request.Builder().url("http://dummy.url").build()
                }

                override fun timeout(): Timeout = Timeout()

                override fun clone(): Call<WhisperTranscriptionResponse> = this
            }
        }
    }

    // 초기 AI 메시지 설정
    val initialAiResponses = listOf(
        "안녕하세요! 키오스크 주문에 어려움을 겪고 계신가요?",
        "패스트푸드점 또는 카페에서의 주문을 도와드릴게요.",
        "주문을 원하시면 \"주문할래요\"를 입력해주세요."
    )

    // NavController 생성
    val navController = rememberNavController()

    // ChatUI 호출 - 초기 AI 응답 메시지 및 더미 채팅방 데이터 전달
    ChatUI(
        navController = navController,
        chatService = dummyChatService
    )
}





