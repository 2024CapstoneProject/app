package com.example.capstoneapp.cafe.ui.Screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.capstoneapp.R

@Composable
fun NumberDialog(onDismiss: () -> Unit, onConfirm: () -> Unit){
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(330.dp, 480.dp)
                .padding(16.dp)
                .background(color = Color.White,shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize() // Column을 Box에 맞추기
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "사진과 같은 창이 뜨면\n전화번호를 입력하세요.\n확인하셨다면 확인 버튼을 눌러주세요",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(R.drawable.cafe_number),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp, 240.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()

                    },
                    modifier = Modifier.size(220.dp, 60.dp),
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
fun NumberDialogPreview() {
    NumberDialog(
        onDismiss = { },
        onConfirm={}
    )
}