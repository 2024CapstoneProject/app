package com.example.capstoneapp.cafe.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun PointDialog(onDismiss: () -> Unit, isYesPoint: () -> Unit, isNoPoint:()->Unit){
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(330.dp, 280.dp)
                .padding(16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "포인트를\n적립하시겠습니까?",
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 30.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = isYesPoint,
                        modifier = Modifier.weight(0.5f).height(60.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF696969)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "예",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = isNoPoint,
                        modifier = Modifier.weight(0.5f).height(60.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xffff602e)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "아니오",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

            }
        }
    }
}


@Preview
@Composable
fun PointDialogPreview() {
    PointDialog(
        onDismiss = { },
        isYesPoint = { },
        isNoPoint = {}
    )
}