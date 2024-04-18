package com.example.capstoneapp.cafe.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.cafe.ui.theme.CapstoneAppTheme
import com.example.capstoneapp.mainPage.VoiceRecogPopup

@Composable
fun Guide0(navController:NavController) {
    CapstoneAppTheme {
        GuideScreen(navController)
    }
}

@Composable
fun GuideScreen(navController:NavController) {
    var showVoiceRecogPopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                showVoiceRecogPopup = true
                println("AI 도우미 서비스 버튼 클릭됨")
            },
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

        if (showVoiceRecogPopup) {
            VoiceRecogPopup(
                showDialog = showVoiceRecogPopup,
                onDismiss = {
                    println("AI 도우미 팝업 닫힘")
                    showVoiceRecogPopup = false
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("CafeHomeScreen")
                },
                modifier = Modifier
                    .size(150.dp, 150.dp),
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
                onClick = {},
                modifier = Modifier
                    .size(150.dp, 150.dp),
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
                .padding(horizontal = 30.dp, vertical = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .size(150.dp, 150.dp),
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
                onClick = {},
                modifier = Modifier
                    .size(150.dp, 150.dp),
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
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .size(150.dp, 150.dp),
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
                    .size(150.dp, 150.dp),
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
        Button(
            onClick = { navController.navigate("ProtectorHome") },
            modifier = Modifier
                .size(200.dp, 100.dp)
                .padding(top = 30.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF3F3D3E)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "위치추적",
                fontSize = 27.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GuideScreenPreview() {
    val navController = rememberNavController()
    Guide0(navController = navController)
}

