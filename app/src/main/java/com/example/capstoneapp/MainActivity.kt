package com.example.capstoneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.data.model.PreviewOrderViewModel
import com.example.capstoneapp.ui.frame.NotificationScreen
import com.example.capstoneapp.ui.screens.GuideImage
import com.example.capstoneapp.ui.screens.OrderScreen
import com.example.capstoneapp.ui.screens.guideText
import com.example.capstoneapp.ui.theme.CapstoneAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapstoneAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotificationScreen{
                        CapstoneAppTheme {
                            // Mock data to be displayed in the preview, since we cannot use live data here.
                            val mockViewModel = PreviewOrderViewModel()
                            // No actual NavController functionality required for preview

                            val navController = rememberNavController()
                            OrderScreen(navController = navController, mockViewModel)
                        }
                    }
                }
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