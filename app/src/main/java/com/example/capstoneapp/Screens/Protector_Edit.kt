package com.example.capstoneapp.Screens

import android.annotation.SuppressLint
import android.text.style.BackgroundColorSpan
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.Frame.TopAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProtectEditScreen(content: @Composable () -> Unit) {
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
                    .fillMaxHeight(0.3f) // Fill the height of the parent
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
                    .fillMaxHeight(0.3f) // Fill the height of the parent
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
                    .fillMaxHeight(0.3f) // Fill the height of the parent
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
fun ProtectEditScreenPreview() {
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
                    color = Color.White, // Change this color to your desired background color
                    shape = RoundedCornerShape(16.dp) // Rounded corners
                )
                .border(1.dp, Color.White, RoundedCornerShape(16.dp)),// Border
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .fillMaxHeight(0.3f)
                        .background(color = Color.White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("이름", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                        Spacer(modifier = Modifier.width(16.dp))
                        TextField(value = "", onValueChange = {}, shape = MaterialTheme.shapes.medium)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .fillMaxHeight(0.3f)
                        .background(color = Color.White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("관계", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                        Spacer(modifier = Modifier.width(16.dp))
                        TextField(value = "", onValueChange = {}, shape = MaterialTheme.shapes.medium)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .fillMaxHeight(0.3f)
                        .background(color = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Color.LightGray),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.padding(8.dp).weight(0.5f)
                        ) {
                            Text(
                                "수정",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Color.LightGray),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.padding(8.dp).weight(0.5f)
                        ) {
                            Text(
                                "삭제",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(200.dp))
    }
}
