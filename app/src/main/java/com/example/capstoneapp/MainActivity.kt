package com.example.capstoneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

import com.example.capstoneapp.fastfood.ui.theme.CapstoneAppTheme
import com.example.capstoneapp.nav.AppNavigation
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapstoneAppTheme {
                //val navController = rememberNavController()
                //SetUpNavGraph(navController = navController)
                val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
                val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
                AppNavigation(problemViewModel)
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