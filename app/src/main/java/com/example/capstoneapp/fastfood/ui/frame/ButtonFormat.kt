package com.example.capstoneapp.fastfood.ui.frame

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
import com.example.capstoneapp.cafe.ui.theme.firaSansFamily
import com.example.capstoneapp.fastfood.ui.theme.fontFamily

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
            //.border(width = 2.dp, color = Color.Gray),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, // 배경색
            contentColor = contentColor // 텍스트 색상
        ),
        onClick = onClick
    ) {
        Text(
            text = buttonText,
            fontWeight = FontWeight.Bold,
            color = contentColor,
            fontFamily = firaSansFamily,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 8.dp),
        )
    }
}