package com.example.capstoneapp.ui.frame

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.ui.theme.fontFamily

//container height:64dp
//icon size : 24dp
//세부사항 https://m3.material.io/components/top-app-bar/specs

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(){
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(horizontal = 16.dp),
        title = {
            Text(
                text = "햄버거 가게",
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                fontFamily = fontFamily,
                fontSize = 24.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "setting",
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = Color.Black,
            containerColor = Color.White
        ),
    )
}