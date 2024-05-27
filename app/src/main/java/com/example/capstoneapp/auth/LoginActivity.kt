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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("access_token", null)

        if (token != null) {
            // 토큰이 있으면 MainActivity로 이동
            Log.d(TAG, "Stored token found: $token")
            startMainActivity()
            return
        }

        // 로그인 공통 콜백 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) { // Login Fail
                Log.e(TAG, "Kakao Login Failed :", error)
            } else if (token != null) { // Login Success
                Log.i(TAG, "Kakao Login Succeeded : ${token.accessToken}")
                saveToken(token.accessToken)
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
                        saveToken(token.accessToken)
                        startMainActivity()
                    }
                }
            } else {
                loginWithKakaoAccount(callback)
            }
        }
    }

    private fun loginWithKakaoAccount(callback: (OAuthToken?, Throwable?) -> Unit) {
        Log.d(TAG, "카카오 계정으로 로그인 시도")
        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)

    }

    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("access_token", token)
        editor.apply()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish() // LoginActivity 종료
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("access_token")
        editor.apply()
        Log.d(TAG, "User logged out. Token removed.")
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}
