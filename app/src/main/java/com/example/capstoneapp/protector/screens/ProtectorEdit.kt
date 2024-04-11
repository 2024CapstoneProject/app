package com.example.capstoneapp.protector.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R

@Composable
fun ProtectorEdit(navController: NavController) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        InputInfoProtect {
        }
        Row {
            ProtectEditButton {
                navController.navigate("ProtectorList")
            }
            ProtectDeleateButton {
                navController.navigate("ProtectorList")
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun ProtectEditButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(Color(0xFFD9D9D9)),
        modifier = Modifier.padding(16.dp)
            .width(140.dp) // 너비 설정
            .height(60.dp) // 높이 설정
    ) {
        Text(
            text = "수정",
            color = Color.Black, // 텍스트 색상을 검은색으로 설정
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
    }

}

@Composable
fun ProtectDeleateButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(Color(0xFFD9D9D9)),
        modifier = Modifier.padding(16.dp)
            .width(140.dp) // 너비 설정
            .height(60.dp) // 높이 설정
    ) {
        Text(
            text = "삭제",
            color = Color.Black, // 텍스트 색상을 검은색으로 설정
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
    }

}

@Preview
@Composable
fun ProtectEditPreview() {
    val navController = rememberNavController()
    ProtectorEdit(navController)
}