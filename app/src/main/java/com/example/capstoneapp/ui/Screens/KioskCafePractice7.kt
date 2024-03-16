package com.example.capstoneapp.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.R

@Composable
fun Dialog7(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = androidx.compose.ui.Modifier
                .size(330.dp, 380.dp)
                .padding(16.dp)
                .background(color = Color.White)
        ) {
            Column(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxSize() // Column을 Box에 맞추기
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "사진을 확인하시고",
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "확인 눌러주세요.",
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
                Image(
                    painter = painterResource(R.drawable.img7),
                    contentDescription = null,
                    modifier = androidx.compose.ui.Modifier.size(250.dp, 140.dp)
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()

                    },
                    modifier = androidx.compose.ui.Modifier.size(220.dp, 60.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF696969)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "확인",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }

}
@Preview
@Composable
fun Kiosk7PreView() {
    Dialog7(
        onDismiss = { },
        onConfirm = { }
    )
}