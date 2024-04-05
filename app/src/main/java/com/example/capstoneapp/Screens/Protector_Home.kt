package com.example.capstoneapp.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.capstoneapp.Frame.TopAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProtectHomeScreen(content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 56.dp)
                .padding(bottom = 128.dp)
                .padding(top = 184.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Fill the width of the parent
                    .padding(horizontal = 1.dp) // Padding from the left and right
                    .fillMaxHeight() // Fill the height of the parent
                    .weight(1f)
                    .background(
                        color = Color.LightGray, // Change this color to your desired background color
                        shape = RoundedCornerShape(16.dp) // Rounded corners
                    )
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),// Border
                contentAlignment = Alignment.Center
            ) {
                //content()
            }
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Fill the width of the parent
                    .padding(horizontal = 1.dp) // Padding from the left and right
                    .fillMaxHeight() // Fill the height of the parent
                    .weight(1f)
                    .background(
                        color = Color.LightGray, // Change this color to your desired background color
                        shape = RoundedCornerShape(16.dp) // Rounded corners
                    )
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),// Border
                contentAlignment = Alignment.Center
            ) {
                //content()
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Fill the width of the parent
                    .padding(horizontal = 1.dp) // Padding from the left and right
                    .fillMaxHeight() // Fill the height of the parent
                    .weight(1f)
                    .background(
                        color = Color.LightGray, // Change this color to your desired background color
                        shape = RoundedCornerShape(16.dp) // Rounded corners
                    )
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),// Border
                contentAlignment = Alignment.Center
            ) {
                //content()
            }
        }
    }
}

@Preview
@Composable
fun ProtectHomeScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 56.dp)
            .padding(bottom = 128.dp)
            .padding(top = 184.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth() // Fill the width of the parent
                .padding(horizontal = 1.dp) // Padding from the left and right
                .fillMaxHeight() // Fill the height of the parent
                .weight(1f)
                .background(
                    color = Color.LightGray, // Change this color to your desired background color
                    shape = RoundedCornerShape(16.dp) // Rounded corners
                )
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),// Border
            contentAlignment = Alignment.Center
        ) {
            //content()
        }
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth() // Fill the width of the parent
                .padding(horizontal = 1.dp) // Padding from the left and right
                .fillMaxHeight() // Fill the height of the parent
                .weight(1f)
                .background(
                    color = Color.LightGray, // Change this color to your desired background color
                    shape = RoundedCornerShape(16.dp) // Rounded corners
                )
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),// Border
            contentAlignment = Alignment.Center
        ) {
            //content()
        }
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth() // Fill the width of the parent
                .padding(horizontal = 1.dp) // Padding from the left and right
                .fillMaxHeight() // Fill the height of the parent
                .weight(1f)
                .background(
                    color = Color.LightGray, // Change this color to your desired background color
                    shape = RoundedCornerShape(16.dp) // Rounded corners
                )
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),// Border
            contentAlignment = Alignment.Center
        ) {
            //content()
        }
    }
}
