package com.example.capstoneapp.fastfood.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.capstoneapp.R
import com.example.capstoneapp.fastfood.ui.theme.fontFamily

@Composable
fun PaymentPopup(
    showDialog: Boolean,
    onDismiss: () -> Unit,
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // Card의 전체 공간을 채웁니다.
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally, // 컬럼 내부의 항목들을 수평 중앙 정렬

                ) {
                    Text(
                        text = "신용/체크카드",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.put_ic_card),
                        contentDescription = null, // 접근성을 위한 설명 추가, 필요하지 않다면 null 처리
                        modifier = Modifier
                            .fillMaxWidth() // 가로를 최대로 설정
                            .aspectRatio(1.4f) // 가로세로비를 1.6:1로 설정
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 취소 버튼 추가
                    CancelButton(onDismiss)
                }
            }
        }
    }
}


@Composable
fun CancelButton(onDismiss: () -> Unit) {
    Button(
        modifier = Modifier.padding(horizontal = 110.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, // 배경색을 투명으로 설정
            contentColor = Color.Black // 텍스트 색상
        ),
        border = BorderStroke(1.dp, Color.Gray), // 테두리 설정
        onClick = onDismiss
    ) {
        Text(
            text = "취소",
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black, // 텍스트 색상을 흑색으로 설정
            fontFamily = fontFamily,
            fontSize = 14.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentPopupPreview() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        PaymentPopup(
            showDialog = showDialog,
            onDismiss = { showDialog = false }
        )
    }
}