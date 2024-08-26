package com.example.capstoneapp.cafe.ui.Screens


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.ui.res.painterResource
import com.example.capstoneapp.R


@Composable
fun Guide0(navController: NavController) {
    val LocalContext = staticCompositionLocalOf<Context?> { null }
    CapstoneAppTheme {
        CompositionLocalProvider(LocalContext provides currentContext()) {
            GuideScreen(navController)
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun GuideScreen(navController: NavController) {
    val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )
        {


        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(start=8.dp,end=8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text="효자손",
                    fontSize = 32.sp,
                    color = Color(0xFF5C460C),
                    style = MaterialTheme.typography.displayLarge)
                Button(
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                    ),
                    contentPadding = PaddingValues(),
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight(),
                    onClick = {logout(context)},
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
                    shape = RoundedCornerShape(16.dp),

                    ){
                    Text(text="로그아웃",
                        fontSize = 20.sp,
                        color = Color(0xFF5C460C),
                        style = MaterialTheme.typography.displayLarge,)
                }

            }

        }
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top, // Align content to the top
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                ),
                contentPadding = PaddingValues(),
                onClick = { navController.navigate("chatbotHome") },
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(bottom = 20.dp,start=8.dp,end=8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFBF4B6)),
                shape = RoundedCornerShape(16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFDA77)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center

                    ){
                        Image(
                            painter = painterResource(id = R.drawable.chatboticon), // 이미지 리소스를 사용
                            contentDescription = null,
                            modifier = Modifier.size(40.dp).padding(end=12.dp)
                        )
                        Text(
                            text = "AI 도우미 대화 서비스",
                            fontSize = 24.sp,
                            color = Color(0xFF5C460C),
                            style = MaterialTheme.typography.displayLarge,
                        )
                    }

                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 16.dp,
                    ),
                    contentPadding = PaddingValues(),
                    onClick = {
                        navController.navigate("CafeHomeScreen")
                    },
                    modifier = Modifier
                        .size(156.dp, 156.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.cafe), // 이미지 리소스를 사용
                                contentDescription = null,
                                modifier = Modifier.size(72.dp)
                            )
                            Text(
                                text = "카페",
                                fontSize = 32.sp,
                                color = Color.Black,
                                style = MaterialTheme.typography.displayLarge
                            )
                        }

                    }

                }
                Button(
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 20.dp,
                    ),
                    contentPadding = PaddingValues(),
                    onClick = {
                        navController.navigate("Kakao_Menu")
                    },
                    modifier = Modifier
                        .size(156.dp, 156.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.kakaotalk), // 이미지 리소스를 사용
                                contentDescription = null,
                                modifier = Modifier.size(72.dp)
                            )
                            Text(
                                text = "카카오톡",
                                fontSize = 32.sp,
                                color = Color.Black,
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 20.dp,
                    ),
                    contentPadding = PaddingValues(),
                    onClick = {
                        navController.navigate("HamburgerHomeScreen")
                    },
                    modifier = Modifier
                        .size(156.dp, 156.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Column(
                                modifier = Modifier
                                    .wrapContentSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.fastfood), // 이미지 리소스를 사용
                                    contentDescription = null,
                                    modifier = Modifier.size(72.dp)
                                )
                                Text(
                                    text = "패스트푸드",
                                    fontSize = 30.sp,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.displayLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Button(
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 20.dp,
                    ),
                    contentPadding = PaddingValues(),
                    onClick = {
                        navController.navigate("TaxiHome")
                    },
                    modifier = Modifier
                        .size(156.dp, 156.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.taxi), // 이미지 리소스를 사용
                                contentDescription = null,
                                modifier = Modifier.size(72.dp)
                            )
                            Text(
                                text = "택시",
                                fontSize = 32.sp,
                                color = Color.Black,
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 20.dp,
                    ),
                    contentPadding = PaddingValues(),
                    onClick = {
                        navController.navigate("Phone_Guide")
                    },
                    modifier = Modifier
                        .size(156.dp, 156.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.phonehome), // 이미지 리소스를 사용
                                contentDescription = null,
                                modifier = Modifier.size(72.dp)
                            )
                            Text(
                                text = "휴대전화",
                                fontSize = 32.sp,
                                color = Color.Black,
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                    }
                }
                Button(
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 20.dp,
                    ),
                    contentPadding = PaddingValues(),
                    onClick = {},
                    modifier = Modifier
                        .size(156.dp, 156.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE7E7E7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "준비중",
                            fontSize = 32.sp,
                            color = Color(0xFFADADAD),
                            style = MaterialTheme.typography.displayLarge,
                        )
                    }
                }
            }
        }
        }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 846)
@Composable
fun GuideScreenPreview() {
    val navController = rememberNavController()
    CapstoneAppTheme{
        Guide0(navController = navController)
    }

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