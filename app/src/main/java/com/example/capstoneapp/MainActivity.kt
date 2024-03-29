package com.example.capstoneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.ui.Navigation.SetUpNavGraph
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

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    val navController = rememberNavController()
//    val viewModel: ProblemViewModel = viewModel()
//    NotificationScreen(navController,viewModel.getProblemValue()!!){
//        Text(
//            text = "문제가 나오는 공간",
//            modifier = Modifier.padding(16.dp) // Padding inside the rectangle
//        )
//    }
//}