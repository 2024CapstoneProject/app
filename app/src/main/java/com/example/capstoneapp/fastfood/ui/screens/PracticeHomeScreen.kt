package com.example.capstoneapp.fastfood.ui.screens

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
import androidx.compose.ui.graphics.Color
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
import com.example.capstoneapp.cafe.ui.Screens.KioskCafePractice0
import com.example.capstoneapp.cafe.ui.Screens.ProblemCard
import com.example.capstoneapp.cafe.ui.Screens.StartButton
import com.example.capstoneapp.fastfood.ui.theme.CapstoneAppTheme
import com.example.capstoneapp.fastfood.ui.theme.fontFamily
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

@Composable
fun PracticeHomeScreen(navController: NavController,problem: Problem) {
    Column {
        TextScreen(navController = navController, problem = problem)
    }

}

@Composable
fun TextScreen(navController: NavController,problem: Problem) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(//문제 설명 box
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color(0xFFFFBD42))
                .padding(bottom = 80.dp,top = 50.dp, end = 16.dp,start=16.dp),
            contentAlignment = Alignment.TopStart
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp,end=16.dp),
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
                .weight(2.0f)
                .background(color = Color.White)
        )
    }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            FastfoodProblemCard(navController,problem)
            Spacer(modifier = Modifier.height(24.dp))
        }

    }
}
@Composable
fun FastfoodProblemCard(navController: NavController, problem: Problem) {
    val items = listOf(
        "메뉴 : ${problem.menu}",
        "장소 : ${problem.place}",
        "포인트 적립 여부 : ${problem.point}",
        "결제 방식 : ${problem.pay}"
    )
    val checkedStates = remember { mutableStateListOf(false, false, false, false) }
    Box(//문제 box
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.padding(bottom = 40.dp)
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

        StartButton(onClick = {
            navController.navigate("touchToStart")
        })
    }
}

@Preview(showBackground = true)
@Composable
fun TPreview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)

    PracticeHomeScreen(navController, problemViewModel.getProblemValue()!!)
}