package com.example.capstoneapp.taxi.ui.screens.practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory
import kotlinx.coroutines.delay

@Composable
fun TaxiRequest(navController: NavController, problem: Problem) {
    Column(
        modifier = Modifier
            .fillMaxHeight(1f)
            .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        TaxiRequestScreen(navController, problem)
    }
}

@Composable
fun TaxiRequestScreen(
    navController: NavController,
    problem: Problem,
) {
    LaunchedEffect(Unit) {
        delay(5000L) // 5000 밀리초 = 5초
        navController.navigate("TaxiInform")
    }
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.Gray)
    ) {
        Image(
            painter = painterResource(id = R.drawable.taxi_map),
            contentDescription = "temp_way_map",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(40.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "경기대학교",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = ">",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "수원역",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "최대 5분 거리의 기사에게 요청 중입니다...",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "출발",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "경기대학교",
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = ".",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = ".",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "도착",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "수원역",
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Preview
@Composable
fun RequestPreview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    TaxiRequest(
        navController = navController,
        problemViewModel.getProblemValue()!!,
    )
}
