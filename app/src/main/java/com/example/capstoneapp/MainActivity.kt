package com.example.capstoneapp

import BottomSheetScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.Frame.NotificationScreen
import com.example.capstoneapp.Navigation.SetUpNavGraph
import com.example.capstoneapp.ui.theme.CapstoneAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapstoneAppTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                SetUpNavGraph(navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotificationScreen{
        Text(
            text = "문제가 나오는 공간",
            modifier = Modifier.padding(16.dp) // Padding inside the rectangle
        )
    }
}