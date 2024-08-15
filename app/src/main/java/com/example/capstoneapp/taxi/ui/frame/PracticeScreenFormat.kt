package com.example.capstoneapp.taxi.ui.frame

import BottomSheetScreen
import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.capstoneapp.cafe.ui.Frame.ButtonFormat
import com.example.capstoneapp.fastfood.ui.theme.Brown
import com.example.capstoneapp.fastfood.ui.theme.LightYellow
import com.example.capstoneapp.fastfood.ui.theme.White
import com.example.capstoneapp.fastfood.ui.theme.Yellow
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.taxi.ui.screens.practice.ChooseTaxiScreen
import com.example.capstoneapp.taxi.ui.screens.practice.TaxiConfirm

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationScreen(
    problem: Problem,
    screenType: Int,
    content: @Composable () -> Unit,
    onAnswerCheckClicked: () -> Unit
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 1.dp)
                    .fillMaxHeight(0.85f)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(2.dp, Color.Gray, RoundedCornerShape(25.dp)),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
            Spacer(Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonFormat(
                    modifier = Modifier.weight(1f),
                    onClick = { openBottomSheet = true },
                    buttonText = "문제 보기",
                    backgroundColor = Color.White,
                    contentColor = Brown,
                    showShadow = true
                )
                Spacer(modifier = Modifier.width(16.dp)) // Space between buttons
                ButtonFormat(
                    modifier = Modifier.weight(1f),
                    onClick = { onAnswerCheckClicked() },
                    buttonText = "정답 확인",
                    backgroundColor = White,
                    contentColor = Brown,
                    showShadow = true
                )
            }
        }
        if (openBottomSheet){
            BottomSheetScreen(
                openBottomSheet = openBottomSheet,
                problem = problem,
                screenType
            ) { openBottomSheet = it }
        }
    }
}

@Preview
@Composable
fun NotificationScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 1.dp)
                .fillMaxHeight(0.9f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(3.dp, Color.Gray, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            TaxiConfirm()
        }
        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonFormat(
                modifier = Modifier.weight(1f),
                onClick = { /* Handle click */ },
                buttonText = "문제보기",
                backgroundColor = LightYellow,
                contentColor = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))
            ButtonFormat(
                modifier = Modifier.weight(1f),
                onClick = { /* Handle click */ },
                buttonText = "정답확인",
                backgroundColor = Yellow,
                contentColor = Color.Black
            )
        }
    }
}