package com.example.capstoneapp.chatbot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ChatbotHomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 가이드모드
        PictureButton(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            onClick = { navController.navigate("Chat_Guide") }
        )
        // 연습모드
        PracticeButton(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            onClick = { navController.navigate("chatUI") }
        )
    }
}

@Composable
fun PictureButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    BoxButton(
        text = "사진 설명",
        backgroundColor = Color(0xFFFFFFFF),
        modifier = modifier,
        onClick = onClick
    )

}

@Composable
fun PracticeButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    BoxButton(
        text = "채팅 하기",
        backgroundColor = Color(0xFFFFBD42),
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun BoxButton(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(backgroundColor)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Black,
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 50.sp,

                )
        )
    }
}

@Preview
@Composable
fun ChatbotHomeScreenPreview() {
    val navController = rememberNavController()
    ChatbotHomeScreen(navController = navController)
}
