package com.example.capstoneapp.kakatalk.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.capstoneapp.cafe.ui.theme.Yellow
import com.example.capstoneapp.cafe.ui.theme.firaSansFamily

@Composable
fun RepeatDialog(
    onDismiss: () -> Unit
){
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(320.dp, 260.dp)
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
                    text = "정답이 아닙니다!",
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = Color.Red,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = firaSansFamily

                )
                Text(
                    text = "아래 문제 보기 버튼을 터치해",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = firaSansFamily
                )
                Text(
                    text = "문제를 다시 확인해보세요!",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = firaSansFamily
                )
                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFE7E7E7)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "닫기",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        fontFamily = firaSansFamily
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RepeatDialogPreview() {
    RepeatDialog(onDismiss={})
}