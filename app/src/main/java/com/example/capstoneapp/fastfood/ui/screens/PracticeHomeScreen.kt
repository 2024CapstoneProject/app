package com.example.capstoneapp.fastfood.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.cafe.ui.Screens.ChecklistItem
import com.example.capstoneapp.cafe.ui.theme.firaSansFamily
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

@Composable
fun PracticeHomeScreen(navController: NavController, problem: Problem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextScreen(navController = navController, problem = problem)
    }

}

@Composable
fun TextScreen(navController: NavController, problem: Problem) {
    var alpha by remember { mutableStateOf(0.0) }

    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                modifier = Modifier.size(56.dp),
                onClick = { navController.navigate("HamburgerHomeScreen") },
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.size(56.dp)
                )

            }

            Text(
                text = "연습해보기",
                fontSize = 32.sp,
                color = Color.Black,
                fontFamily = firaSansFamily,
                fontWeight = FontWeight.ExtraBold,
            )
        }
    }

    Column(
        modifier = Modifier
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "아래 내용을 확인하고",
                fontSize = 28.sp,
                fontFamily = firaSansFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "키오스크 주문을 연습해요!",
                fontSize = 28.sp,
                fontFamily = firaSansFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "아래 정보는 주문하실 내용입니다.",
                fontSize = 16.sp,
                color = Color(0xFFADADAD),
                fontFamily = firaSansFamily,
                fontWeight = FontWeight.Light,
            )
            Text(
                text = "확인하셨다면 버튼을 눌러 체크해주세요.",
                fontSize = 16.sp,
                color = Color(0xFFADADAD),
                fontFamily = firaSansFamily,
                fontWeight = FontWeight.Light,
            )
        }
        FastfoodProblemCard(navController, problem) {
            if (it) alpha = 1.0 else 0.0
        }
    }
    StartButton(alpha, onClick = {
        navController.navigate("touchToStart")
    })
}


@Composable
fun FastfoodProblemCard(
    navController: NavController,
    problem: Problem,
    checkSuccess: (Boolean) -> Unit
) {
    val items = listOf(
        "메뉴 : ${problem.menu}",
        "장소 : ${problem.place}",
        "포인트 적립 여부 : ${problem.point}",
        "결제 방식 : ${problem.pay}"
    )
    val checkedStates = remember { mutableStateListOf(false, false, false, false) }

    if (checkedStates.all { it == true }) {
        checkSuccess(true)
    } else {
        checkSuccess(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .padding(top = 32.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
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

@Composable
fun StartButton(alpha: Double, onClick: () -> Unit) {

    Button(
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 16.dp,
        ),
        contentPadding = PaddingValues(),
        onClick = onClick,
        modifier = Modifier
            .size(316.dp, 68.dp)
            .alpha(alpha.toFloat()), //시작하기 버튼 위치 수정 offset. 숫자 커지면 아래로 이동
        colors = ButtonDefaults.buttonColors(Color.White),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "시작하기",
                fontSize = 24.sp,
                fontFamily = firaSansFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5C460C)
            )
        }
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