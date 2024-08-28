package com.example.capstoneapp.taxi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.cafe.ui.theme.firaSansFamily

@Composable
fun TaxiProblem(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaxiProblemScreen(navController)
    }
}

@Composable
fun TaxiProblemScreen(navController: NavController) {
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
                onClick = { navController.popBackStack("TaxiHome", inclusive = false)},
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
                text = "택시 호출하기를 연습해요!",
                fontSize = 28.sp,
                fontFamily = firaSansFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "아래 정보는 택시 호출 정보입니다.",
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
        ProblemCard(navController) {
            if (it) alpha = 1.0 else 0.0
        }
    }
    StartButton(alpha, onClick = {
        navController.navigate("TaxiMain")
    })
}

@Composable
fun ProblemCard(
    navController: NavController,
    checkSuccess: (Boolean) -> Unit
) {
    val items = listOf(
        "도착지 : 수원역",
        "택시 종류 : 빠른 택시",
    )
    val checkedStates = remember { mutableStateListOf(false, false) }
    if (checkedStates.all { it == true }) {
        checkSuccess(true)
    } else {
        checkSuccess(false)
    }

    Box(
        modifier = Modifier
            .height(380.dp)
            .padding(top = 20.dp, bottom = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 20.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
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

}

@Composable
fun ChecklistItem(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val currentCheckedState = rememberUpdatedState(checked)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable { onCheckedChange(!currentCheckedState.value) },
        colors = CardDefaults.cardColors(
            containerColor = if (currentCheckedState.value) Color(0xFFFFDA77) else Color(0xFFE7E7E7)
        ),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontFamily = firaSansFamily,
                fontWeight = FontWeight.Medium,
            )
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (currentCheckedState.value) Color(0xFF5C460C) else Color(0xFFADADAD),
                modifier = Modifier.size(40.dp)
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
fun TaxiScreenPreview() {
    val navController = rememberNavController()

    TaxiProblem(navController)
}