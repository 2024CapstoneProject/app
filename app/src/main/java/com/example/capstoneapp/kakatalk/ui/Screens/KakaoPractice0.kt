package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.nav.repository.KakaotalkProblem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

@Composable
fun KakaoPractice0(navController: NavController, problem: KakaotalkProblem) {

    Column {
        TextScreen(navController, problem)
    }
}

@Composable
fun TextScreen(navController: NavController, problem: KakaotalkProblem) {
    val photoId = if(problem.type.equals("photo")) problem.photoId else R.drawable.kakaotalk_icon

    Column(
        modifier = Modifier
            .fillMaxSize()
        ,verticalArrangement = Arrangement.Center, // 수직 방향으로 요소를 동일한 간격으로 배치합니다.
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "문제",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )

        Text(
            text = "${problem.person}에게 ${problem.content}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp
        )

        if(problem.type == "simple"){
            Image(
                painter = painterResource(photoId),
                contentDescription = "kakao image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .padding(bottom=16.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillBounds,
            )
        }else{
            Card(
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom=16.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp)
            ){
                Image(
                    painter = painterResource(photoId),
                    contentDescription = "kakao image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillBounds,
                )
            }
        }

        Text(
            text = "화면에서 정답인 버튼을\n눌러주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 0.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )

        StartButton(onClick = {
            navController.navigate("Kakao_FriendList")
        })
    }
}

@Composable
fun StartButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.size(260.dp, 100.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "시작하기", fontSize = 24.sp, color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextScreenPreview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)

    KakaoPractice0(navController, problemViewModel.getKakaotalkProblemValue()!!)
}
