package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R

@Composable
fun ProtectorHome(navController:NavController){
    Column {
        //가이드모드
        RegisterButtonProtect {
            //navController.navigate("KioskCafeGuide0")
        }
        //연습모드
        ListButtonProtect {
            //navController.navigate("KioskCafePractice0")
        }
        //이미지(onclick 기능 필요없음)
        ImageButtonProtect {
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}


@Composable
fun ImageButtonProtect(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(id = R.drawable.map),
            contentDescription = null,
            modifier = Modifier
                .size(194.dp)
                .clickable { onClick() }
        )
    }
}
@Composable
fun RegisterButtonProtect(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(Color(0xFFD9D9D9)),
            modifier = Modifier
                .width(260.dp) // 너비 설정
                .height(130.dp) // 높이 설정
                .clip(RectangleShape)
        ) {
            Text(
                text = "(피)보호자 등록",
                color = Color.Black, // 텍스트 색상을 검은색으로 설정
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)

            )
        }
    }
}
@Composable
fun ListButtonProtect(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(Color(0xFFD9D9D9)),
            modifier = Modifier
                .width(260.dp) // 너비 설정
                .height(130.dp) // 높이 설정
                .clip(RectangleShape)
        ) {
            Text(
                text = "(피)보호자 목록",
                color = Color.Black, // 텍스트 색상을 검은색으로 설정
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)

            )
        }
    }
}

@Preview
@Composable
fun ProtectHomePreview() {
    val navController = rememberNavController()
    ProtectorHome(navController)
}