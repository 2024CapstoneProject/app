package com.example.capstoneapp.cafe.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun NoItemDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(330.dp, 380.dp)
                .padding(16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize() // Column을 Box에 맞추기
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "상품을 선택하세요",
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        onDismiss()
                    },
                    modifier = Modifier.size(220.dp, 60.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF696969)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "닫기",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}