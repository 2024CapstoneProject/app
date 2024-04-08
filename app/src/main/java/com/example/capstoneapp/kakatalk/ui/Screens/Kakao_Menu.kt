package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R

@Composable
fun Kakao_Menu(navController:NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //가이드모드
        PictureButton {
            navController.navigate("KakaoGuide0")
        }
        //연습모드
        PracticeButton {
            navController.navigate("KakaoPractice0")
        }
        //이미지(onclick 기능 필요없음)
        ImageButtonKakao {
        }
    }
}


@Composable
fun ImageButtonKakao(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(id = R.drawable.kakaomenu),
            contentDescription = null,
            modifier = Modifier
                .size(194.dp)
                .clickable { onClick() }
        )
    }
}

@Preview
@Composable
fun KakaoMenuPreview() {
    val navController = rememberNavController()
    Kakao_Menu(navController = navController)
}