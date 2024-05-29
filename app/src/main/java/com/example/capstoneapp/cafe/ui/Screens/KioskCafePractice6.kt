package com.example.capstoneapp.cafe.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.ui.Components.CafeMenuBarFormat
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.kakatalk.ui.Components.CloseDialog
import com.example.capstoneapp.kakatalk.ui.Components.RepeatDialog
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory


@Composable
fun KioskCafePractice6(
    navController: NavController,
    menuItemsViewModel: MenuItemsViewModel,
    problem: Problem,
    showBorder: Boolean
) {
    Column {
        CafeMenuBarFormat {
            MenuText6()
        }
        Screen6(navController, menuItemsViewModel, showBorder, problem)
    }
}

@Composable
fun Screen6(
    navController: NavController,
    viewModel: MenuItemsViewModel,
    showBorder: Boolean,
    problem: Problem
) {
    val totalAmount by viewModel.totalOrderAmount.observeAsState()
    Surface(
        color = Color(0xFFCACACA),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            PayButton(navController, onClick = {}, showBorder, problem)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Text(
                    text = "금액",
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Text(
                    text = totalAmount.toString(),
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Red
                )
                Text(
                    text = "원",
                    modifier = Modifier
                        .padding(end = 30.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

        }
    }
}

@Composable
fun PayButton(
    navController: NavController,
    onClick: () -> Unit,
    showBorder: Boolean,
    problem: Problem
) {
    var dialog7 by remember { mutableStateOf(false) }
    var dialog10 by remember { mutableStateOf(false) }
    var dialog11 by remember { mutableStateOf(false) }
    var closeDialog by remember { mutableStateOf(false) }
    var repeatAnswer by remember { mutableStateOf(false) }
    var pointDialog by remember { mutableStateOf(false) }
    var numberDialog by remember { mutableStateOf(false) }

    var yesPoint by remember { mutableStateOf(false) }
    var noPoint by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                if (problem.c_pay != "카드 결제") {
                    repeatAnswer = true
                } else {
                    pointDialog = true
                }
            },
            modifier = Modifier
                .size(220.dp, 150.dp)
                .then(
                    if (showBorder && problem.c_pay == "카드 결제") Modifier.border(
                        BorderWidth, BorderColor, BorderShape
                    ) else Modifier
                ),
            colors = ButtonDefaults.buttonColors(Color(0xFFFB2929)),
            shape = RoundedCornerShape(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .background(Color(0xFFD01B1B), RoundedCornerShape(80.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cardicon),
                        contentDescription = null, // 이미지에 대한 접근성 설명은 필요하지 않습니다
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(54.dp)
                        // 이미지 크기 조정
                    )
                }
                Text(
                    text = "카드결제",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            }

        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                if (problem.c_pay != "쿠폰 사용") {
                    repeatAnswer = true
                } else {
                    dialog7 = true
                }
            },
            modifier = Modifier
                .size(220.dp, 150.dp)
                .then(
                    if (showBorder && problem.c_pay == "쿠폰 사용") Modifier.border(
                        BorderWidth, BorderColor, BorderShape
                    ) else Modifier
                ),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFCA0D)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .background(Color.White, RoundedCornerShape(80.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.coupon),
                        contentDescription = null, // 이미지에 대한 접근성 설명은 필요하지 않습니다
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .size(46.dp)
                        // 이미지 크기 조정
                    )
                }
                Text(
                    text = "쿠폰 사용",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        if (pointDialog) {
            PointDialog(
                onDismiss = { pointDialog = false },
                isYesPoint = {
                    if (problem.c_point == "O") {
                        pointDialog = false
                        numberDialog = true
                    } else {
                        pointDialog = false
                        repeatAnswer = true
                    }
                }, isNoPoint = {
                    if (problem.c_point == "X") {
                        pointDialog = false
                        dialog11 = true

                    } else {
                        pointDialog = false
                        repeatAnswer = true
                    }
                })
        }

        if (numberDialog) {
            NumberDialog(onDismiss = { numberDialog = false }, onConfirm = { dialog11 = true })
        }

        if (dialog7) {
            Dialog7(onDismiss = { dialog7 = false }, onConfirm = { dialog10 = true })
        }
        if (dialog10) {
            Dialog10(onDismiss = { dialog10 = false }, onConfirm = { closeDialog = true })
        }
        if (dialog11) {
            Dialog11(onDismiss = { dialog11 = false }, onConfirm = { dialog10 = true })
        }
        if (closeDialog) {
            CloseDialog(
                onDismiss = {
                    closeDialog = false
                    navController.popBackStack("KioskCafePractice0", inclusive = true)
                }
            )
        }
    }
    if (repeatAnswer) {
        RepeatDialog(onDismiss = {
            repeatAnswer = false
        })
    }
}

@Composable
fun MenuText6() {
    Text(
        text = "결제수단 선택",
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(left = 30.dp),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
    )
}

@Preview
@Composable
fun Kiosk6PreView() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val menuItemsViewModelFactory = MenuItemsViewModelFactory()
    val menuItemsViewModel: MenuItemsViewModel = viewModel(factory = menuItemsViewModelFactory)

    KioskCafePractice6(
        navController,
        menuItemsViewModel,
        problemViewModel.getProblemValue()!!,
        true
    )
}
