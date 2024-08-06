package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.cafe.ui.Screens.BoxButton
import com.example.capstoneapp.cafe.ui.theme.CapstoneAppTheme

@Composable
fun Kakao_Menu(navController: NavController) {
    CapstoneAppTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //가이드모드
            PictureButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onClick = {
                    navController.navigate("KakaoGuide0")
                })
            //연습모드
            PracticeButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onClick = {
                    navController.navigate("KakaoPractice0")
                }
            )
        }
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
        text = "연습 하기",
        backgroundColor = Color(0xFFFFBD42),
        modifier = modifier,
        onClick = onClick
    )
}

@Preview
@Composable
fun KakaoMenuPreview() {
    val navController = rememberNavController()
    Kakao_Menu(navController = navController)
}