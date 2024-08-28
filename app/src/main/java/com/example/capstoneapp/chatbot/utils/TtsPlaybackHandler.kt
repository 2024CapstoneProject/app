package com.example.capstoneapp.chatbot.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.util.Log
import android.widget.Toast
import com.google.cloud.texttospeech.v1.AudioConfig
import com.google.cloud.texttospeech.v1.AudioEncoding
import com.google.cloud.texttospeech.v1.SsmlVoiceGender
import com.google.cloud.texttospeech.v1.SynthesisInput
import com.google.cloud.texttospeech.v1.TextToSpeechClient
import com.google.cloud.texttospeech.v1.VoiceSelectionParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
class TtsPlaybackHandler(
    private val context: Context,
    private val ttsClient: TextToSpeechClient,
    private val coroutineScope: CoroutineScope
) {

    private var isShutdown = false
    private var isPlaying = false

    fun playText(text: String) {
        if (isShutdown) {
            Log.e("TtsPlaybackHandler", "TTS client is already shutdown")
            return
        }

        coroutineScope.launch {
            try {
                isPlaying = true
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
                Log.d("TtsPlaybackHandler", "AudioTrack state: $state")
                if (state == AudioTrack.STATE_UNINITIALIZED) {
                    Log.e("TtsPlaybackHandler", "AudioTrack is not initialized properly")
                } else if (state == AudioTrack.STATE_INITIALIZED) {
                    Log.d("TtsPlaybackHandler", "AudioTrack is initialized properly")
                }

                //Toast.makeText(context, "TTS 재생 중: $text", Toast.LENGTH_SHORT).show()

                // 재생 완료 후 상태 업데이트
                audioTrack.setNotificationMarkerPosition(audioContent.size / 2)
                audioTrack.setPlaybackPositionUpdateListener(object : AudioTrack.OnPlaybackPositionUpdateListener {
                    override fun onMarkerReached(track: AudioTrack?) {
                        isPlaying = false
                        Log.d("TtsPlaybackHandler", "TTS 재생 완료")
                    }

                    override fun onPeriodicNotification(track: AudioTrack?) {
                        // Do nothing
                    }
                })
            } catch (e: Exception) {
                Log.e("TtsPlaybackHandler", "Error occurred", e)
                isPlaying = false
            }
        }
    }

    fun isTtsPlaying(): Boolean {
        return isPlaying
    }

    fun shutdown() {
        try {
            ttsClient.shutdown()
            ttsClient.awaitTermination(1, TimeUnit.MINUTES)
            isShutdown = true
        } catch (e: Exception) {
            Log.e("TtsPlaybackHandler", "Error during shutdown", e)
        }
    }
}
