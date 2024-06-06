package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import com.example.capstoneapp.cafe.ui.Screens.ChecklistItem
import com.example.capstoneapp.cafe.ui.Screens.ProblemCard
import com.example.capstoneapp.nav.repository.KakaotalkProblem
import com.example.capstoneapp.nav.repository.Problem
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
    val photoId = if (problem.type.equals("photo")) problem.photoId else R.drawable.kakaotalk_icon
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(//문제 설명 box
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = Color(0xFFFFBD42))
                    .padding(bottom = 80.dp, top = 65.dp, end = 16.dp, start = 16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "아래 문제에 맞는",
                        fontSize = 33.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "버튼을 터치하세요!",
                        fontSize = 33.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "항목을 읽고 이해했으면 항목을 터치하세요",
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "항목을 다 터치했다면 시작하기 버튼을 눌러주세요",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f)
                    .background(color = Color.White)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(0.dp))
            KakaoProblemCard(navController, problem)
            Spacer(modifier = Modifier.height(0.dp))
        }

    }
}
@Composable
fun KakaoProblemCard(navController: NavController, problem: KakaotalkProblem) {
    val items = listOf(
        "${problem.person}에게",
        "${problem.content}",
    )
    val checkedStates = remember { mutableStateListOf(false, false, false, false) }
    Box(//문제 box
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.padding(bottom = 40.dp) // Add some padding to ensure the button overlaps
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal =16.dp,vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "문제",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                items.forEachIndexed { index, item ->
                    ChecklistItem(
                        title = item,
                        checked = checkedStates[index],
                        onCheckedChange = { checked ->
                            checkedStates[index] = checked
                        }
                    )
                }
            }
        }

        com.example.capstoneapp.cafe.ui.Screens.StartButton(onClick = {
            navController.navigate("touchToStartCafe")
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
