package com.example.capstoneapp.mainPage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonWithRoundedBorder(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = modifier
    ) {
        Text("로그아웃", fontSize = 16.sp, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonWithRoundedBorderPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ButtonWithRoundedBorder(
                onClick = { /* Preview Click Action */ },
                modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
            )
        }
    }
}