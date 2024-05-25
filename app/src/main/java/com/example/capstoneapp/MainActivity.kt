package com.example.capstoneapp

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capstoneapp.auth.AuthCodeHandlerActivity
import com.example.capstoneapp.auth.LoginScreen
import com.example.capstoneapp.fastfood.ui.theme.CapstoneAppTheme
import com.example.capstoneapp.nav.AppNavigation
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        setContent {
            CapstoneAppTheme {
                var isLogin by remember { mutableStateOf(false) }

                if (isLogin) {
                    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
                    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
                    AppNavigation(problemViewModel,this)
                } else {
                    LoginScreen(onLoginSuccess =  {
//                        val intent = Intent(this@MainActivity, AuthCodeHandlerActivity::class.java)
//                        val loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                            if (result.resultCode == Activity.RESULT_OK) {
//                                val token = result.data?.getStringExtra("token")
//                                if (token != null) {
//                                    Log.i("MainActivity", "Received token: $token")
//                                    isLogin = true
//                                } else {
//                                    Log.e("MainActivity", "No token received")
//                                }
//                            } else {
//                                Log.e("MainActivity", "Login canceled or failed")
//                            }
//                        }
//                        loginLauncher.launch(intent)
                        isLogin= it
                    },this)
                }
            }
        }
    }
}