package com.example.capstoneapp.cafe.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.cafe.ui.Components.CafeMenuBarFormat
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.kakatalk.ui.Components.RepeatDialog
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel

@Composable
fun KioskCafePractice5(
    navController: NavController,
    menuItemsViewModel: MenuItemsViewModel,
    problem: Problem,
    showBorder: Boolean
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        CafeMenuBarFormat {
            MenuText5(navController)
        }
        Screen5(navController, menuItemsViewModel, showBorder, problem)
    }
}

@Composable
fun Screen5(
    navController: NavController,
    viewModel: MenuItemsViewModel,
    showBorder: Boolean,
    problem: Problem
) {
    var repeatAnswer by remember { mutableStateOf(false) }
    val orderItems by viewModel.orderItems.observeAsState()
    val totalAmount by viewModel.totalOrderAmount.observeAsState()
    val totalOrderCount by viewModel.totalOrderCount.observeAsState()

    Surface(
        color = Color(0xFFCACACA),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(276.dp)
            ) {

                LazyColumn(
                    modifier = Modifier
                        .height(280.dp)
                        .fillMaxWidth()
                ) {
                    orderItems?.size?.let {
                        items(it) {
                            val item = orderItems!![it]
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                            ) {
                                Text(
                                    text = item.first.name,
                                    modifier = Modifier
                                        .padding(start = 30.dp, top = 10.dp)
                                        .align(Alignment.CenterVertically),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                                Text(
                                    text = item.second.toString(),
                                    modifier = Modifier
                                        .padding(end = 30.dp, top = 10.dp)
                                        .align(Alignment.CenterVertically),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                    }

                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp, start = 28.dp, end = 28.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "총 수량",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,

                        )
                    Text(
                        text = totalOrderCount.toString(),
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Red,

                        )
                    Text(
                        text = "개",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "금액",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                    Text(
                        text = totalAmount.toString(),
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Red,
                    )
                    Text(
                        text = "원",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        if (problem.c_place != "먹고가기") {
                            repeatAnswer = true
                        } else {
                            navController.navigate("KioskCafePractice6")
                        }
                    },
                    modifier = Modifier
                        .size(150.dp, 80.dp)
                        .then(
                            if (showBorder && problem.c_place == "먹고가기") Modifier.border(
                                BorderWidth,
                                BorderColor,
                                BorderShape
                            ) else Modifier
                        ),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "먹고가기",
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        if (problem.c_place != "포장하기") {
                            repeatAnswer = true
                        } else {
                            navController.navigate("KioskCafePractice6")
                        }
                    },
                    modifier = Modifier
                        .size(150.dp, 80.dp)
                        .then(
                            if (showBorder && problem.c_place == "포장하기") Modifier.border(
                                BorderWidth,
                                BorderColor,
                                BorderShape
                            ) else Modifier
                        ),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFCA0D)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "포장하기",
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
    if (repeatAnswer) {
        RepeatDialog(onDismiss = {
            repeatAnswer = false
        })
    }
}

@Composable
fun MenuText5(navController: NavController) {
    Column(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .wrapContentSize()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "setting",
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
            )
        }
        Text(
            text = "주문 세부내역 확인",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }
}

@Preview
@Composable
fun Kiosk5PreView() {
    val navController = rememberNavController()
    val problemViewModelFactory =
        com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val menuItemsViewModelFactory = MenuItemsViewModelFactory()
    val menuItemsViewModel: MenuItemsViewModel = viewModel(factory = menuItemsViewModelFactory)

    KioskCafePractice5(
        navController,
        menuItemsViewModel,
        problemViewModel.getProblemValue()!!,
        true
    )

}