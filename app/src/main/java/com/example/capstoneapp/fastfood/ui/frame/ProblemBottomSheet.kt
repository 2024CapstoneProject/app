@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.capstoneapp.fastfood.ui.frame

import com.example.capstoneapp.fastfood.data.model.ProblemViewModel
import com.example.capstoneapp.fastfood.data.model.ProblemViewModelFactory
import com.example.capstoneapp.fastfood.data.repository.Problem
import com.example.capstoneapp.fastfood.data.repository.ProblemRepository
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capstoneapp.fastfood.ui.theme.Yellow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScreen(
    openBottomSheet: Boolean,
    problem: Problem,
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
                ProblemBox(problem)
                ButtonFormat(
                    modifier = Modifier.height(64.dp), onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                onOpenBottomSheetChange(false)
                            }
                        }
                    }, buttonText = "닫기", backgroundColor = Yellow, contentColor = Color.Black
                )
            }
        }
    }
}

@Composable
fun ProblemBox(problem: Problem) {

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "메뉴 : ${problem.menu}",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = "장소 : ${problem.place}",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = "포인트 적립 여부 : ${problem.point}",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = "결제 방식 : ${problem.pay}",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(Modifier.padding(bottom = 20.dp))
    }
}

@Preview
@Composable
fun BottomSheetPreview() {
    var openBottomSheet by rememberSaveable { mutableStateOf(true) }
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val problem = remember { problemViewModel.getProblemValue() }
    BottomSheetScreen(
        openBottomSheet = openBottomSheet, problem = problem!!
    ) { openBottomSheet = it }
}