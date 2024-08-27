@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.capstoneapp.fastfood.ui.frame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capstoneapp.cafe.ui.theme.Brown
import com.example.capstoneapp.cafe.ui.theme.firaSansFamily
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScreen(
    openBottomSheet: Boolean,
    problem: Problem,
    screenType: Int,
    onOpenBottomSheetChange: (Boolean) -> Unit
) {
    val skipPartiallyExpanded by remember { mutableStateOf(false) }
    val edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    if (openBottomSheet) {
        val windowInsets =
            if (edgeToEdgeEnabled) WindowInsets(0) else BottomSheetDefaults.windowInsets

        ModalBottomSheet(
            onDismissRequest = { onOpenBottomSheetChange(false) },
            sheetState = bottomSheetState,
            windowInsets = windowInsets,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ProblemBox(problem, screenType)
                ButtonFormat(
                    modifier = Modifier
                        .height(64.dp)
                        .width(152.dp), onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                onOpenBottomSheetChange(false)
                            }
                        }
                    }, buttonText = "닫기",
                    backgroundColor = Color.White,
                    contentColor = Brown,
                    showShadow = true
                )
            }
        }
    }
}

@Composable
fun ProblemBox(problem: Problem, screenType: Int) {
    when (screenType) {
        1 -> {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "메뉴 : ${problem.menu}",
                    fontSize = 24.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "장소 : ${problem.place}",
                    fontSize = 24.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "포인트 적립 여부 : ${problem.point}",
                    fontSize = 24.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "결제 방식 : ${problem.pay}",
                    fontSize = 24.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Light
                )
                Spacer(Modifier.padding(bottom = 20.dp))
            }
        }

        2 -> {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "메뉴 : ${problem.c_menu}",
                    fontSize = 24.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "장소 : ${problem.c_place}",
                    fontSize = 24.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "포인트 적립 여부 : ${problem.c_point}",
                    fontSize = 24.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "결제 방식 : ${problem.c_pay}",
                    fontSize = 24.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Light
                )
                Spacer(Modifier.padding(bottom = 20.dp))
            }
        }

        3 -> {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = problem.order.replaceLast(problem.order, " ", "\n"),
                    fontSize = 24.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp

                )
                Spacer(Modifier.padding(bottom = 20.dp))
            }
        }

        4 -> {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "도착지 : 수원역",
                    fontSize = 24.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp,
                    modifier = Modifier.padding(bottom = 8.dp)

                )
                Text(
                    text = "택시 종류 : 빠른 택시",
                    fontSize = 24.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp
                )
                Spacer(Modifier.padding(bottom = 20.dp))
            }

        }

        else -> {}
    }

}

fun String.replaceLast(target: String, oldValue: String, newValue: String): String {
    if (target.contains("전화번호")) return this

    val lastIndex = this.lastIndexOf(oldValue)
    if (lastIndex == -1) return this // 공백이 없으면 그대로 반환

    val secondLastIndex = this.lastIndexOf(oldValue, startIndex = lastIndex - 1)
    return if (secondLastIndex == -1) this else this.substring(
        0,
        secondLastIndex
    ) + newValue + this.substring(secondLastIndex + oldValue.length)
}

@Preview
@Composable
fun BottomSheetPreview() {
    var openBottomSheet by rememberSaveable { mutableStateOf(true) }
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val problem = remember { problemViewModel.getProblemValue() }
    BottomSheetScreen(
        openBottomSheet = openBottomSheet, problem = problem!!,
        4
    ) { openBottomSheet = it }
}