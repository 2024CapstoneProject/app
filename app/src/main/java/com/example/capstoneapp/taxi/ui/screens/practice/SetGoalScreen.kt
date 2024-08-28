package com.example.capstoneapp.taxi.ui.screens.practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.fastfood.ui.frame.ButtonFormat
import com.example.capstoneapp.fastfood.ui.theme.Brown
import com.example.capstoneapp.fastfood.ui.theme.White
import com.example.capstoneapp.fastfood.ui.theme.Yellow
import com.example.capstoneapp.fastfood.ui.theme.fontFamily

@Composable
fun SetGoalScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight(1f)
            .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        SetGoal(navController)
    }
}
@Preview(showBackground = true)
@Composable
fun SetGoalScreenPreview() {
    val navController = rememberNavController()
    SetGoalScreen(navController)
}

@Composable
fun SetGoal(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Image(
            painter = painterResource(id = R.drawable.suwon_map),
            contentDescription = "goal_map",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds

        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "수원역",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "경기 수원시 팔달구 덕영대로 924",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = { navController.navigate("TaxiChoose") },
//                modifier = Modifier
//                    .fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text(
//                    text = "목적지 설정 완료",
//                    style = TextStyle(
//                        fontSize = 16.sp,
//                        color = Color.White
//                    )
//                )
//            }

            ButtonFormat(
                modifier = Modifier.fillMaxWidth(),
                onClick = {navController.navigate("TaxiChoose") },
                buttonText = "목적지 설정 완료",
                backgroundColor = White,
                contentColor = Brown,
                showShadow = true
            )
        }
    }
//    }
}