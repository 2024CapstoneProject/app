package com.example.capstoneapp.protector.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R

@Composable
fun ProtectorRegister(navController:NavController){
    Column {
        //이름 및 관계 입력
        InputInfoProtect {
        }
        Text("등록 링크 보내기", modifier = Modifier.fillMaxWidth(), fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
        //카카오 링크 보내기 버튼
        KakaoLoginButton {
            // 카카오 링크 보내기
        }
        //등록하기 버튼
        RegisterButton {
            navController.navigate("ProtectorList")
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun InputInfoProtect(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier
                .width(260.dp) // 너비 설정
                .height(70.dp) // 높이 설정
                .clip(RectangleShape)
                .background(color = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("이름", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Box(
            modifier = Modifier
                .width(260.dp) // 너비 설정
                .height(70.dp) // 높이 설정
                .clip(RectangleShape)
                .background(color = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("관계", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun RegisterButton(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(Color(0xFFD9D9D9)),
            modifier = Modifier
                .width(240.dp) // 너비 설정
                .height(120.dp) // 높이 설정
                .clip(RectangleShape)
        ) {
            Text(
                text = "등록하기",
                fontSize = 24.sp,
                color = Color.Black, // 텍스트 색상을 검은색으로 설정
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)

            )
        }
    }
}

@Composable
fun KakaoLoginButton(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(Color.Yellow),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 16.dp)
                .width(400.dp) // 너비 설정
                .height(52.dp) // 높이 설정
                .clip(RectangleShape)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_kakao),
//                    contentDescription = "Kakao Icon",
//                    modifier = Modifier.size(24.dp)
//                )
                Text(
                    text = "카카오 로그인",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 60.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProtectorRegisterPreview() {
    val navController = rememberNavController()
    ProtectorRegister(navController)
}