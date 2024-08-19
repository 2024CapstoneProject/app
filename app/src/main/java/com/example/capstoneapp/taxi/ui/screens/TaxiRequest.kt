package com.example.capstoneapp.taxi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaxiRequest(
    navController: NavController,
    openBottomSheet: Boolean,
    problem: Problem,
    onOpenBottomSheetChange: (Boolean) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onOpenBottomSheetChange(false)
                navController.popBackStack() // 뒤로가기
            },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .padding(horizontal = 20.dp, vertical = 0.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "최대 5분 거리의 기사에게 요청 중입니다...",
                    fontSize = 20.sp,
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
                        text = "마루아파트",
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
                        text = "디지털 배움터",
                        fontSize = 18.sp,
                        color = Color.DarkGray
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Preview
@Composable
fun RequestPreview() {
    var openBottomSheet by rememberSaveable { mutableStateOf(true) }
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    TaxiRequest(
        navController = navController,
        openBottomSheet = openBottomSheet,
        problemViewModel.getProblemValue()!!,
        onOpenBottomSheetChange = { openBottomSheet = it }
    )
}
