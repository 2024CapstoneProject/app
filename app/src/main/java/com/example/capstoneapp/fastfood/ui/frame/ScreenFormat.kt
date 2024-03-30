package com.example.capstoneapp.fastfood.ui.frame


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
import com.example.capstoneapp.fastfood.data.repository.Problem
import com.example.capstoneapp.fastfood.ui.theme.LightYellow
import com.example.capstoneapp.fastfood.ui.theme.Yellow


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationScreen(
    problem: Problem,
    content: @Composable () -> Unit,
    onAnswerCheckClicked: () -> Unit
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .padding(top = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Fill the width of the parent
                    .padding(horizontal = 1.dp) // Padding from the left and right
                    .fillMaxHeight(0.85f) // Fill 50% of the height of the parent
                    .background(
                        color = Color.LightGray, // Change this color to your desired background color
                        shape = RoundedCornerShape(16.dp) // Rounded corners
                    )
                    .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)),// Border
                contentAlignment = Alignment.Center
            ) {
                content()
            }

            // Buttons
            Spacer(Modifier.padding(8.dp)) // This will push the buttons up to be just above the bottom bar

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp), // Apply horizontal padding
                horizontalArrangement = Arrangement.SpaceBetween // Arrange buttons with space in between
            ) {
                ButtonFormat(
                    modifier = Modifier.weight(1f),
                    onClick = { openBottomSheet = true },
                    buttonText = "문제보기",
                    backgroundColor = LightYellow,
                    contentColor = Color.Black
                )
                Spacer(modifier = Modifier.width(16.dp)) // Space between buttons

                ButtonFormat(
                    modifier = Modifier.weight(1f),
                    onClick = { onAnswerCheckClicked() },
                    buttonText = "정답확인",
                    backgroundColor = Yellow,
                    contentColor = Color.Black
                )
            }
        }

        if (openBottomSheet){
            BottomSheetScreen(
                openBottomSheet = openBottomSheet,
                problem = problem
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
                .fillMaxWidth() // Fill the width of the parent
                .padding(horizontal = 1.dp) // Padding from the left and right
                .fillMaxHeight(0.9f) // Fill 50% of the height of the parent
                .background(
                    color = Color.LightGray, // Change this color to your desired background color
                    shape = RoundedCornerShape(16.dp) // Rounded corners
                )
                .border(3.dp, Color.Gray, RoundedCornerShape(16.dp)),// Border
            contentAlignment = Alignment.Center
        ) {}

        // Buttons
        Spacer(Modifier.weight(1f)) // This will push the buttons up to be just above the bottom bar

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp), // Apply horizontal padding
            horizontalArrangement = Arrangement.SpaceBetween // Arrange buttons with space in between
        ) {
            ButtonFormat(
                modifier = Modifier.weight(1f),
                onClick = { /* Handle click */ },
                buttonText = "문제보기",
                backgroundColor = LightYellow,
                contentColor = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp)) // Space between buttons

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