package com.example.capstoneapp.component

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.Frame.NotificationScreen


@Composable
fun KioskCafePractice6(navController: NavController){
    /*
    *  KioskCafePractice0 매개변수 viewModel: SharedViewModel 추가
    *  NotificationScreen 인자 viewModel,navController 추가
    * */
    NotificationScreen() {
        Column {
            CafeMenuBarFormat {
                MenuText6()
            }
            Screen6(navController)
        }
    }
}
@Composable
fun Screen6(navController: NavController){
    val price = 2500
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        PayButton(onClick =  {

        })
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "금액",
                modifier = Modifier
                    .padding(start = 30.dp, top = 50.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Text(
                text = "$price",
                modifier = Modifier
                    .padding(end = 5.dp, top = 50.dp)
                    .align(Alignment.CenterVertically),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            Text(
                text = "원",
                modifier = Modifier
                    .padding(end = 30.dp, top = 50.dp)
                    .align(Alignment.CenterVertically),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

    }
}
@Composable
fun PayButton(onClick: () -> Unit){
    var dialog7 by remember { mutableStateOf(false) }
    var dialog10 by remember { mutableStateOf(false) }
    var dialog11 by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 70.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                dialog11 = true
            },
            modifier = Modifier.size(200.dp, 150.dp),
            colors = ButtonDefaults.buttonColors(Color.Gray),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(
                text = "카드결제",
                fontSize = 24.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                dialog7 = true
            },
            modifier = Modifier.size(200.dp, 150.dp),
            colors = ButtonDefaults.buttonColors(Color.Gray),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text(
                text = "쿠폰사용",
                fontSize = 24.sp,
                color = Color.Black
            )
        }
        if (dialog7) {
            Dialog7(
                onDismiss = { dialog7 = false },
                onConfirm = { dialog10 = true }
            )
        }
        if(dialog10){
            Dialog10(onDismiss = { dialog10 = false })
        }
        if(dialog11){
            Dialog11(
                onDismiss = { dialog11 = false },
                onConfirm = { dialog10 = true }
            )
        }
    }
}
@Composable
fun MenuText6(){
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
fun Kiosk6PreView(){
    val navController = rememberNavController()
    KioskCafePractice6(navController)
//    val viewModel: SharedViewModel = viewModel()
//    KioskCafePractice6(navController,viewModel)
}
