package com.example.capstoneapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capstoneapp.auth.LoginActivity
import com.example.capstoneapp.fastfood.ui.theme.CapstoneAppTheme
import com.example.capstoneapp.nav.AppNavigation
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory
import com.kakao.sdk.common.KakaoSdk

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
        editor.remove("user_uid")
        editor.remove("access_token")

        editor.apply()
        startActivity(Intent(this, LoginActivity::class.java))
        finish() // MainActivity 종료
    }


}
