package com.example.capstoneapp.cafe.ui.Screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import com.example.capstoneapp.cafe.ui.theme.firaSansFamily
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
            MenuText6(navController)
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
        color = Color.White,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
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
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "금액",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = firaSansFamily
                )
                Text(
                    text = totalAmount.toString() + "원",
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Red,
                    fontFamily = firaSansFamily
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
            .padding(top = 40.dp, bottom = 40.dp),
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
                .size(300.dp, 180.dp)
                .then(
                    if (showBorder && problem.c_pay == "카드 결제") Modifier.border(
                        BorderWidth, BorderColor, BorderShape
                    ) else Modifier
                ),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFCA0D)),
            shape = RoundedCornerShape(16.dp),
        ) {

            Text(
                text = "카드결제",
                fontSize = 32.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = firaSansFamily
            )


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
                .size(300.dp, 180.dp)
                .then(
                    if (showBorder && problem.c_pay == "쿠폰 사용") Modifier.border(
                        BorderWidth, BorderColor, BorderShape
                    ) else Modifier
                ),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
            shape = RoundedCornerShape(16.dp)
        ) {

            Text(
                text = "쿠폰 사용",
                fontSize = 32.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = firaSansFamily
            )

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
fun MenuText6(navController: NavController) {
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
            text = "결제수단 선택",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = firaSansFamily,
            color = Color.Black,
        )

    }
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
