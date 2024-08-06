package com.example.capstoneapp.cafe.ui.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.cafe.data.Repository.MenuItemsRepository
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

@Composable
fun KioskCafePractice0(navController: NavController, problem: Problem) {
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
                .padding(bottom = 80.dp,top = 45.dp, end = 16.dp,start=16.dp),
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
            ProblemCard(navController,problem)
            Spacer(modifier = Modifier.height(24.dp))
        }

    }
}

@Composable
fun ProblemCard(navController: NavController, problem: Problem) {
    val items = listOf(
        "메뉴 : ${problem.c_menu}",
        "장소 : ${problem.c_place}",
        "포인트 적립 여부 : ${problem.c_point}",
        "결제 방식 : ${problem.c_pay}"
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

        StartButton(onClick = {
            navController.navigate("touchToStartCafe")
        })
    }
}

@Composable
fun ChecklistItem(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val currentCheckedState = rememberUpdatedState(checked)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCheckedChange(!currentCheckedState.value) },
        colors = CardDefaults.cardColors(
            containerColor = if (currentCheckedState.value) Color(0xFFFFF8D6) else Color(0xFFFFFFFF)
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,)
            Icon(
                imageVector = if (currentCheckedState.value) Icons.Default.Check else Icons.Default.Close,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
            )
        }
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
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
            ),
            contentPadding = PaddingValues(),
            onClick = onClick,
            modifier = Modifier.size(260.dp, 100.dp).offset(y=(98).dp), //시작하기 버튼 위치 수정 offset. 숫자 커지면 아래로 이동
            colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFFF8D6),  // 엣지 하이라이트 색상 (밝은 노란색)
                                Color(0xFFFFEEA8),  // 하이라이트 색상 (밝은 노란색)
                                Color(0xFFFFE47A),  // 더 밝은 메인 색상
                                Color(0xFFFFBD42),  // 메인 색상
                                Color(0xFFD8A531)   // 어두운 그림자 색상
                            ),
                            center = Offset(0.5f, 0.5f),
                            radius = 400f
                        ), alpha = 0.1f
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "시작하기", fontSize = 24.sp, color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextScreenPreview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)

    KioskCafePractice0(navController, problemViewModel.getProblemValue()!!)
}