package com.example.capstoneapp.fastfood.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.fastfood.ui.theme.CapstoneAppTheme
import com.example.capstoneapp.fastfood.ui.theme.fontFamily

@Composable
fun PictureButton(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
            modifier = Modifier
                .width(260.dp) // 너비 설정
                .height(130.dp) // 높이 설정
        ) {
            Text(
                text = "사진으로 설명보기",
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                fontFamily = fontFamily,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}

@Composable
fun PracticeButton(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
            modifier = Modifier
                .width(260.dp) // 너비 설정
                .height(130.dp) // 높이 설정
                .clip(RectangleShape)
        ) {
            Text(
                text = "직접 연습해보기",
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                fontFamily = fontFamily,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}

@Composable
fun ImageButton(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.burgerimg),
            contentDescription = null, // 이미지에 대한 접근성 설명은 필요하지 않습니다
            modifier = Modifier.size(194.dp) // 이미지 크기 조정
        )
    }
}


@Composable
fun GreetingPreview(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PictureButton(onClick = { navController.navigate("HamburgerGuideScreen") })
        PracticeButton(onClick = { navController.navigate("HamburgerPracticeHomeScreen") })
        ImageButton {}
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    CapstoneAppTheme {
        Column {
            PictureButton {}
            PracticeButton {}
            ImageButton {}
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}