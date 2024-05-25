package com.example.capstoneapp.auth

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.capstoneapp.R

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KakaoTalkLoginButton {
            val intent = Intent(context, AuthCodeHandlerActivity::class.java).apply {
                putExtra("use_kakao_account", false)
            }
            context.startActivity(intent)
        }

        Spacer(modifier = Modifier.height(16.dp))

        KakaoAccountLoginButton {
            val intent = Intent(context, AuthCodeHandlerActivity::class.java).apply {
                putExtra("use_kakao_account", true)
            }
            context.startActivity(intent)
        }
    }
}

@Composable
fun KakaoTalkLoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color(0xFFFEE500)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(16.dp)
            .height(50.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakao_login_large_wide),
            contentDescription = "KakaoTalk Login Button"
        )
    }
}

@Composable
fun KakaoAccountLoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.Gray),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(16.dp)
            .height(50.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Login with Kakao Account",
            color = Color.White
        )
    }
}
