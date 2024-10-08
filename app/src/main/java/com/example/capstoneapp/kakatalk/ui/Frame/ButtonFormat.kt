package com.example.capstoneapp.kakatalk.ui.Frame

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonFormat(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String,
    backgroundColor: Color,
    contentColor: Color,
    showShadow: Boolean = false
) {
    Button(
        modifier = modifier
            .then(
                if(showShadow) {
                    Modifier.shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
                } else {
                    Modifier
                }
            ),
            //.border(width = 2.dp, color = Color.Gray), // 테두리
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
            color = contentColor,
            //fontFamily = suite,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 8.dp),
        )
    }
}