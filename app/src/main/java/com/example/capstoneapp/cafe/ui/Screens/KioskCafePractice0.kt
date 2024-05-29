package com.example.capstoneapp.cafe.ui.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.capstoneapp.cafe.data.Repository.MenuItemsRepository
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

@Composable
fun KioskCafePractice0(navController: NavController, problem: Problem) {

    Column {
        //TopAppBar()
        TextScreen(navController, problem)
    }
}

@Composable
fun TextScreen(navController: NavController, problem: Problem) {
    Log.d("menu이름", problem.c_menu)
    var painter = MenuItemsRepository.getMenuItemPhotoId(problem.c_menu)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center, // 수직 방향으로 요소를 동일한 간격으로 배치합니다.
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "메뉴 : ${problem.c_menu}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "장소 : ${problem.c_place}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "포인트 적립 여부 : ${problem.c_point}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "결제 방식 : ${problem.c_pay}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )

        Image(
            painter = painterResource(painter),
            contentDescription = "cafe image",
            modifier = Modifier
                .size(200.dp)
                .fillMaxWidth(),
            alignment = Alignment.Center
        )
        Text(
            text = "화면에서 정답인 버튼을",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "눌러주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        StartButton(onClick = {
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

    KioskCafePractice0(navController, problemViewModel.getProblemValue()!!)
}
