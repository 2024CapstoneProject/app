package com.example.capstoneapp.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.ui.theme.fontFamily

@Composable
fun KioskButtonFormat(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String,
    backgroundColor: Color,
    contentColor: Color
) {
    Button(
        modifier = modifier
            .width(72.dp)
            .height(54.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, // 배경색
            contentColor = contentColor // 텍스트 색상
        ),
        onClick = onClick
    ) {
        Text(
            text = buttonText,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            fontFamily = fontFamily,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 0.dp),
        )
    }
}