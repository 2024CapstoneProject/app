package com.example.capstoneapp.cafe.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.data.Repository.MenuItem
import com.example.capstoneapp.cafe.ui.Frame.ButtonFormat
import com.example.capstoneapp.cafe.ui.theme.LightYellow
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import kotlinx.coroutines.delay

@Composable
fun totalOrder(
    totalCount: Int,
    isRepeat: Boolean,
    ResetOrPayOrder: (Pair<Boolean, Boolean>) -> Unit,
    showBorder: Boolean,
    userTimer: Boolean = true
) {
    //타이머 120초
    var remainingTime by remember { mutableStateOf(120) }
    //타이머 실행 여부
    var isTimerRunning by remember { mutableStateOf(userTimer) }
    var noItemDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isTimerRunning) {
        if (isRepeat) {
            isTimerRunning = false
        }
        if (isTimerRunning && userTimer) {
            while (remainingTime > 0) {
                //1초씩 감소
                delay(1000)
                remainingTime--
            }
            //120초가 지난 뒤 타이머 실행 중단
            isTimerRunning = false
        }
    }

    //만약 타이머 실행이 중단되었다면 팝업 띄움
    if (!isTimerRunning && !isRepeat && userTimer) {
        showTimerEndPopup() {
            //재시작 버튼 클릭 시 타이머 재시작
            isTimerRunning = it
            //리스트 초기화
            ResetOrPayOrder(Pair(true, false))
        }
        remainingTime = 120
    } else if (isRepeat) {
        isTimerRunning = true
        ResetOrPayOrder(Pair(true, false))
        remainingTime = 120
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(start = 8.dp, end = 4.dp),
            contentAlignment = Alignment.CenterStart

        ) {
            Column(
                modifier = Modifier.padding(start = 2.dp)
            ) {
                Text(
                    text = "남은 시간",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Cursive
                    ),
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                Color.Red,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.SansSerif
                            ),
                        ) {
                            append("$remainingTime")
                        }
                        append("초")
                    },
                    color = Color.Red,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                )
            }
        }

        Divider(
            color = Color.Gray, // 선의 색상 지정
            thickness = 1.dp, // 선의 두께 지정
            modifier = Modifier.padding(8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 2.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "총수량",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.SansSerif
                        ),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    Color.Red,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily.SansSerif
                                ),
                            ) {
                                append(totalCount.toString())
                            }
                            append("개")
                        },
                        color = Color.Red,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.SansSerif,
                    )
                }
//                Spacer(modifier = Modifier.height(8.dp))


                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(start = 8.dp, end = 8.dp)
                    .then(
                        if (showBorder) Modifier.border(
                            BorderWidth,
                            BorderColor,
                            BorderShape
                        ) else Modifier
                    ),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFDA77), contentColor = Color.White
                    ),
                    onClick = {
                        if (totalCount == 0) {
                            noItemDialog = true
                        } else ResetOrPayOrder(Pair(false, true))
                    }) {
                    Text(
                        color = Color.Black,
                        text = "결제",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
            }
        }
    }
    if (noItemDialog) {
        NoItemDialog(onDismiss = { noItemDialog = false })
    }
}

@Composable
fun showTimerEndPopup(restartButton: (Boolean) -> Unit) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(4.dp)
                    .wrapContentWidth()
                    .background(
                        color = Color.White, // Change this color to your desired background color
                        shape = RoundedCornerShape(
                            16.dp
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "120초 안에 선택해야합니다.\n다시 시작하기 위해\n아래 버튼을 눌러주세요",
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                    lineHeight = TextUnit.Unspecified
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonFormat(
                    modifier = Modifier.height(68.dp), onClick = {
                        restartButton(true)
                    }, buttonText = "확인", backgroundColor = LightYellow, contentColor = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    val navController = rememberNavController()
    val dummyOrderItems = listOf(
        MenuItem(
            1,
            "불고기 버거",
            R.drawable.americano_hot,
            7000
        )
    )
    val orderItems = remember {
        mutableStateListOf<Pair<MenuItem, Int>>()
    }
    orderItems.add(
        Pair(
            MenuItem(
                1,
                "불고기 버거",
                R.drawable.americano_hot,
                7000
            ), 1
        )
    )
    totalOrder(1, true, {
        if (it.first) {
            orderItems.clear()
        }
    }, true)
}