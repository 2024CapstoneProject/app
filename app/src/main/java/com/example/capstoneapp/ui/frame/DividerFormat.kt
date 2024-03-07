package com.example.capstoneapp.ui.frame

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DividerFormat(modifier: Modifier = Modifier) {
    Divider(
        color = Color.Gray,
        thickness = 2.dp,
        modifier = modifier.padding(horizontal = 0.dp)
    )
}