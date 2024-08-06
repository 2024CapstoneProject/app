package com.example.capstoneapp.mainPage


import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.capstoneapp.R
import com.example.capstoneapp.chatbot.api.AudioRecorder
import com.example.capstoneapp.chatbot.api.AudioUploader
import com.example.capstoneapp.fastfood.ui.theme.fontFamily


@Composable
fun CancelButton(onDismiss: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, // 배경색을 투명으로 설정
            contentColor = Color.Black
        ),
        border = BorderStroke(1.dp, Color.LightGray),
        onClick = onDismiss
    ) {
        Text(
            text = "취소",
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
        )
    }
}

@Composable
fun VoicePopup(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    audioUploader: AudioUploader
) {
    val context = LocalContext.current // 현재 Compose 환경의 Context를 가져옵니다.
    val recorder = remember { AudioRecorder(context) } // AudioRecorder 인스턴스 생성
    var recordingState by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // Card의 전체 공간을 채웁니다.
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally, // 컬럼 내부의 항목들을 수평 중앙 정렬

                ) {
                    Text(
                        text = "원하시는 기능을 말씀해주세요.",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily
                        )
                    )
                    Spacer(modifier = Modifier.height(64.dp))

                    Image(
                        painter = painterResource(id = R.drawable.mic),
                        contentDescription = null, // 접근성을 위한 설명 추가, 필요하지 않다면 null 처리
                        modifier = Modifier
                            .size(100.dp)
                    )

                    Spacer(modifier = Modifier.height(64.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // UI elements like Text, Image

                        Button(onClick = {
                            val activity = context as? Activity
                            if (!recordingState) {
                                if (ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.RECORD_AUDIO
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    // 권한이 없으면 권한 요청
                                    activity?.let {
                                        ActivityCompat.requestPermissions(
                                            it,
                                            arrayOf(Manifest.permission.RECORD_AUDIO),
                                            1  // RECORD_AUDIO_REQUEST_CODE 대신에 직접 1 사용
                                        )
                                    } ?: Log.e("AudioRecorder", "Activity reference is null")
                                } else {
                                    // 권한이 이미 있으면 녹음 시작
                                    recorder.startRecording()
                                    Log.d("AudioRecorder", "Recording started")
                                    recordingState = true
                                }
                            } else {
                                // 녹음 중지 및 파일 처리
                                val recordedFile = recorder.stopRecording()
                                Log.d("AudioRecorder", "Recording stopped")
                                recordingState = false
                                recordedFile?.let { file ->
                                    audioUploader.uploadAudioFile(file)
                                    Log.d("AudioRecorder", "File uploaded")
                                }
                                onDismiss()
                            }
                        }) {
                            Text(if (recordingState) "녹음 중지" else "녹음 시작")
                        }

                    }
                    CancelButton(onDismiss)
                }
            }
        }
    }



}
