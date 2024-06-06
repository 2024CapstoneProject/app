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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.capstoneapp.R
import com.example.capstoneapp.chatbot.api.AudioUploader
import com.example.capstoneapp.chatbot.api.ChatService
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
        "안녕하세요! 키오스크 주문에 어려움을 겪고 계신가요?",
        "패스트푸드점 또는 카페에서의 주문을 도와드릴게요.",
        "주문을 원하시면 \"주문할래요\"를 입력해주세요."
    )

    var userMessages by remember { mutableStateOf(listOf<String>()) }
    var aiResponses by remember { mutableStateOf(initialAiResponses) }
    var sessionId by remember { mutableStateOf("") }

    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    val speechIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR") // 한국어 설정
        }
    }
    val speechLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

    val ttsClient = remember {
        val credentialsStream: InputStream =
            context.resources.openRawResource(R.raw.service_account_key) // 서비스 계정 키 파일
        val credentials = GoogleCredentials.fromStream(credentialsStream)
        val settings = TextToSpeechSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .build()
        TextToSpeechClient.create(settings)
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

    fun HandleTtsPlayback(text: String) {
        coroutineScope.launch {
            try {
                val input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build()
                val voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("ko-KR")
                    .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                    .setName("ko-KR-Standard-B")
                    .build()
                val audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.LINEAR16)
                    .setSampleRateHertz(16000) // 샘플 레이트 설정 추가
                    .build()
                val response = withContext(Dispatchers.IO) {
                    ttsClient.synthesizeSpeech(input, voice, audioConfig)
                }
                val audioContent = response.audioContent.toByteArray()

                // AudioTrack을 사용하여 오디오 재생
                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build()

                val audioFormat = AudioFormat.Builder()
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(16000) // 샘플 레이트 설정 추가
                    .build()

                val audioTrack = AudioTrack(
                    audioAttributes,
                    audioFormat,
                    audioContent.size,
                    AudioTrack.MODE_STATIC,
                    AudioManager.AUDIO_SESSION_ID_GENERATE
                )

                audioTrack.write(audioContent, 0, audioContent.size)
                audioTrack.play()

                // AudioTrack 상태 확인 로그 추가
                val state = audioTrack.state
                Log.d("ChatUI", "AudioTrack state: $state")
                if (state == AudioTrack.STATE_UNINITIALIZED) {
                    Log.e("ChatUI", "AudioTrack is not initialized properly")
                } else if (state == AudioTrack.STATE_INITIALIZED) {
                    Log.d("ChatUI", "AudioTrack is initialized properly")
                }

                Toast.makeText(context, "TTS 재생 중: $text", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("ChatUI", "Error occurred", e)
            }
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
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        // 고정된 바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
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
                buttonText = "새 대화 하기",
                backgroundColor = OutraGeousOrange,
                contentColor = Color(0xFFFFFFFF),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
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
                        horizontalArrangement = Arrangement.Start
                    ) {
                        MessageBox(message = userMessages[index], isUser = true, onClick = {},fontSize = fontSize.value)
                    }
                    if (aiResponses.size > index) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            MessageBox(message = aiResponses[index], isUser = false,fontSize = fontSize.value) {
                                currentResponse = aiResponses[index]
                                showDialog = true
                            }
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AnswerDialog(
                responseText = currentResponse,
                onDismiss = { showDialog = false },
                onReplay = {
                    coroutineScope.launch {
                        if (currentResponse.isNotEmpty()) {
                            HandleTtsPlayback(currentResponse)
                        }
                    }
                }
            )
        }
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
                onClick = { speechLauncher.launch(speechIntent) }, // 음성 검색 버튼 클릭 시 음성 인식 시작
                buttonText = "말하기",
                backgroundColor = LightYellow,
                contentColor = Color.Black,
                modifier = Modifier
                    .weight(1f)

            )

            Spacer(Modifier.width(24.dp))

            ButtonFormat(
                onClick = {
                    coroutineScope.launch {
                        if (aiResponses.isNotEmpty()) {
                            val lastResponse = aiResponses.last()
                            HandleTtsPlayback(lastResponse)
                        }
                    }
                },
                buttonText = "다시 듣기",
                backgroundColor = Yellow,
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
}




