package com.example.capstoneapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capstoneapp.auth.LoginActivity
import com.example.capstoneapp.auth.UserService
import com.example.capstoneapp.fastfood.ui.theme.CapstoneAppTheme
import com.example.capstoneapp.nav.AppNavigation
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

            setContent {
                CapstoneAppTheme {
                    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
                    val problemViewModel: ProblemViewModel =
                        viewModel(factory = problemViewModelFactory)
                    AppNavigation(problemViewModel, this@MainActivity)




                    Button(onClick = { logout() }) {
                        Text(text = "Logout")
                    }
                }


            }
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("access_token")
        editor.apply()
        startActivity(Intent(this, LoginActivity::class.java))
        finish() // MainActivity 종료
    }


}
