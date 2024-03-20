package com.example.capstoneapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.ui.theme.fontFamily

@Composable
fun touchScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("payment") // Navigate to PaymentScreen
            }, // 전체 Column에 패딩 적용
        horizontalAlignment = Alignment.CenterHorizontally // 자식 요소들을 가운데 정렬

    ) {
        Spacer(modifier = Modifier.height(228.dp))

        Icon(
            painter = painterResource(id = R.drawable.baseline_adb_24),
            contentDescription = null
            // Icon에 별도의 패딩을 적용하지 않음
        )
        Text(
            text = "화면을 터치해 주세요",
            style =
            TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily
            ),
            modifier = Modifier.padding(top = 16.dp) // Icon과 Text 사이의 상단 패딩 적용
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    touchScreen(navController = navController)
}
