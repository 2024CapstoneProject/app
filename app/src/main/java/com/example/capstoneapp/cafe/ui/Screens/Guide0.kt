package com.example.capstoneapp.cafe.ui.Screens


import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.auth.LoginActivity
import com.example.capstoneapp.cafe.ui.theme.CapstoneAppTheme
import com.example.capstoneapp.chatbot.api.AudioUploader
import com.example.capstoneapp.chatbot.api.ChatService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.material3.MaterialTheme
import com.example.capstoneapp.mainPage.ButtonWithRoundedBorder



@Composable
fun Guide0(navController: NavController) {
    val LocalContext = staticCompositionLocalOf<Context?> { null }
    CapstoneAppTheme {
        CompositionLocalProvider(LocalContext provides currentContext()) {
            GuideScreen(navController)
        }
    }
}

@Composable
fun GuideScreen(navController: NavController) {
    val context = LocalContext.current

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ButtonWithRoundedBorder(
                onClick = { logout(context) },
                modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
            )
        }
    }


// 로그아웃 함수 정

    Column(
        modifier = Modifier
            .padding(top = 80.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.Top, // Align content to the top
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { navController.navigate("chatUI") },
            modifier = Modifier
                .size(330.dp, 80.dp)
                .padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFfbf4b6)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "AI 도우미 서비스",
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("CafeHomeScreen")
                },
                modifier = Modifier
                    .size(180.dp, 180.dp)
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "카페",
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Button(
                onClick = {
                    navController.navigate("Kakao_Menu")
                },
                modifier = Modifier
                    .size(180.dp, 180.dp)
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "카카오톡",
                    fontSize = 27.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("HamburgerHomeScreen")
                },
                modifier = Modifier
                    .size(180.dp, 180.dp)
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "패스트",
                        fontSize = 30.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "푸드",
                        fontSize = 30.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Button(
                onClick = {
                    navController.navigate("Taxi_Guide")
                },
                modifier = Modifier
                    .size(180.dp, 180.dp)
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "택시",
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("Phone_Guide")
                },
                modifier = Modifier
                    .size(180.dp, 180.dp)
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "휴대전화",
                    fontSize = 27.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .size(180.dp, 180.dp)
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "준비중",
                    fontSize = 27.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
//        Button(
//            onClick = { navController.navigate("ProtectorHome") },
//            modifier = Modifier
//                .size(200.dp, 100.dp)
//                .padding(top = 30.dp),
//            colors = ButtonDefaults.buttonColors(Color(0xFF3F3D3E)),
//            shape = RoundedCornerShape(16.dp)
//        ) {
//            Text(
//                text = "위치추적",
//                fontSize = 27.sp,
//                color = Color.White,
//                fontWeight = FontWeight.ExtraBold
//            )
//        }
    }

}

@Preview(showBackground = true)
@Composable
fun GuideScreenPreview() {
    val navController = rememberNavController()
    Guide0(navController = navController)
}

fun setupAudioUploader(): AudioUploader {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://118.67.135.211:9000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val chatService = retrofit.create(ChatService::class.java)
    return AudioUploader(chatService)
}

@Composable
fun currentContext(): Context? {
    return LocalContext.current
}

fun logout(context: Context) {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.remove("user_uid")
    editor.remove("access_token")
    editor.apply()
    context.startActivity(Intent(context, LoginActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    })
}