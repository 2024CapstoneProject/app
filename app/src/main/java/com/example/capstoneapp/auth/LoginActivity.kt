package com.example.capstoneapp.auth


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneapp.MainActivity
import com.example.capstoneapp.R
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.http.HttpException
import java.io.IOException

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
                sendUserInfoToBackend()
                startMainActivity()
            }
        }

        findViewById<ImageView>(R.id.kakao_login_btn).setOnClickListener {
            val keyHash = Utility.getKeyHash(this)
            Log.d("AuthCodeHandlerActivity", "KeyHash: $keyHash")

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

        findViewById<ImageView>(R.id.kakao_login_btn2).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish() // LoginActivity 종료
        }
    }

    private fun loginWihtKakaoTalk(callback: (OAuthToken?, Throwable?) -> Unit)
    {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "KakaoTalk Login Failed :", error)
                    loginWithKakaoAccount(callback)
                } else if (token != null) {
                    Log.i(TAG, "KakaoTalk Login Succeeded : ${token.accessToken}")
                    saveToken(token.accessToken)
                    sendUserInfoToBackend()
                    startMainActivity()
                }
            }
        } else {
            loginWithKakaoAccount(callback)
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

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                val uid = user.id.toString()
                editor.putString("user_uid", uid)
            }
            editor.apply()
        }


    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish() // LoginActivity 종료
    }

 /*   private fun logout() {

        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                editor.remove("access_token")
                editor.remove("user_uid")
                editor.apply()
            }
        }
    }*/

    private fun getRefreshToken(callback: (OAuthToken?, Throwable?) -> Unit) {
        AuthApiClient.instance.refreshToken(
            callback = { token, error ->
                if (error != null) {
                    Log.e(TAG, "토큰 갱신 실패", error)
                } else if (token != null) {
                    Log.i(TAG, "토큰 갱신 성공 ${token.accessToken}")
                    saveToken(token.accessToken)
                    sendUserInfoToBackend()
                    startMainActivity()
                }
            }
        )
    }


    private fun sendUserInfoToBackend() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                val uid = user.id.toString()
                val email = user.kakaoAccount.toString()
                Log.i(TAG, "User ID: $uid, Email: $email")
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = UserService.RetrofitInstance.api.loginUser(uid, email)
                        if (response.isSuccessful) {
                            Log.i(TAG, "User info sent to backend successfully")
                        } else {
                            Log.e(TAG, "Failed to send user info to backend: ${response.code()}")
                        }
                    } catch (e: HttpException) {
                        Log.e(TAG, "HttpException: ${e.message}")
                    } catch (e: IOException) {
                        Log.e(TAG, "IOException: ${e.message}")
                    }
                }
            }
        }
    }





    companion object {
        private const val TAG = "LoginActivity"
    }
}
