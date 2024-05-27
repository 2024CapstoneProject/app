package com.example.capstoneapp.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneapp.MainActivity
import com.example.capstoneapp.R
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 로그인 공통 콜백 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) { // Login Fail
                Log.e(TAG, "Kakao Login Failed :", error)
            } else if (token != null) { // Login Success
                Log.i(TAG, "Kakao Login Succeeded : ${token.accessToken}")
                startMainActivity()
            }
        }

        findViewById<ImageView>(R.id.kakao_login_btn).setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {

                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "KakaoTalk Login Failed :", error)
                        loginWithKakaoAccount(callback)
                    } else if (token != null) {
                        Log.i(TAG, "KakaoTalk Login Succeeded : ${token.accessToken}")
                        startMainActivity()
                    }
                }
            // 항상 카카오 계정으로 로그인
            }else{

                loginWithKakaoAccount(callback)
            }

        }
    }

    private fun loginWithKakaoAccount(callback: (OAuthToken?, Throwable?) -> Unit) {
        Log.d("AuthCodeHandlerActivity", "카카오 계정으로 로그인 시도")
        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish() // LoginActivity 종료
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}
