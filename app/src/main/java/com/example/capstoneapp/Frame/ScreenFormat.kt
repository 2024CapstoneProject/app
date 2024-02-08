package com.example.capstoneapp.Frame

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(content: @Composable () -> Unit) {

    Scaffold(
        topBar = {
            navVar2()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Illustration
            Spacer(Modifier.height(64.dp))
            Box(

                modifier = Modifier
                    //테두리
                    .border(1.dp, Color.Black)

                    .fillMaxWidth() // Fill the width of the parent
                    .padding(horizontal = 16.dp) // Padding from the left and right

                    // Height to wrap content
                    .fillMaxHeight(0.9f) // Fill 50% of the height of the parent
                    .background(
                        color = Color.LightGray, // Change this color to your desired background color
                        shape = RoundedCornerShape(8.dp) // Rounded corners

                    ),
                contentAlignment = Alignment.Center
            ) {
                content()
            }

            // Buttons
            Spacer(Modifier.weight(1f)) // This will push the buttons up to be just above the bottom bar

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // Apply horizontal padding
                horizontalArrangement = Arrangement.SpaceBetween // Arrange buttons with space in between
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle click */ }
                ) {
                    Text("문제 보기")
                }
                Spacer(modifier = Modifier.width(8.dp)) // Space between buttons
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { /* Handle click */ }
                ) {
                    Text("정답확인")
                }
            }

            // This adds space between the buttons and the bottom bar
        }
    }
}
