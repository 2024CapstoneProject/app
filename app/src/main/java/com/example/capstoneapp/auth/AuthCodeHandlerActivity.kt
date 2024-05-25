package com.example.capstoneapp.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class AuthCodeHandlerActivity : AppCompatActivity() {

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("AuthCodeHandlerActivity", "로그인 실패: $error")
            setResult(Activity.RESULT_CANCELED)
            finish()
        } else if (token != null) {
            Log.i("AuthCodeHandlerActivity", "로그인 성공: ${token.accessToken}")
            val intent = Intent().apply {
                putExtra("token", token.accessToken)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            Log.e("AuthCodeHandlerActivity", "로그인 실패: 알 수 없는 오류")
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login()
    }

    private fun login() {
        val keyHash = Utility.getKeyHash(this)
        Log.d("AuthCodeHandlerActivity", "KeyHash: $keyHash")
        val useKakaoAccount = intent.getBooleanExtra("use_kakao_account", false)
        if (useKakaoAccount) {
            Log.d("AuthCodeHandlerActivity", "카카오 계정으로 로그인 시도")
            UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
        } else {
            Log.d("AuthCodeHandlerActivity", "카카오톡으로 로그인 시도")
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e("AuthCodeHandlerActivity", "카카오톡으로 로그인 실패: $error")
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            Log.e("AuthCodeHandlerActivity", "사용자에 의해 취소됨")
                            setResult(Activity.RESULT_CANCELED)
                            finish()
                        } else {
                            Log.d("AuthCodeHandlerActivity", "카카오 계정으로 로그인 시도")
                            UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                        }
                    } else if (token != null) {
                        Log.i("AuthCodeHandlerActivity", "카카오톡으로 로그인 성공: ${token.accessToken}")
                        val intent = Intent().apply {
                            putExtra("token", token.accessToken)
                        }
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    } else {
                        Log.e("AuthCodeHandlerActivity", "카카오톡 로그인 실패: 알 수 없는 오류")
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
                }
            } else {
                Log.e("AuthCodeHandlerActivity", "카카오톡이 설치되지 않음, 카카오 계정으로 로그인 시도")
                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
            }
        }
    }
}
