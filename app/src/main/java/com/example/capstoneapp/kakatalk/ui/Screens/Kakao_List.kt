package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.kakatalk.data.Repository.Problem
import com.example.capstoneapp.kakatalk.data.Repository.ProblemRepository
import com.example.capstoneapp.kakatalk.data.ViewModel.ProblemViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.ProblemViewModelFactory
import com.example.capstoneapp.kakatalk.ui.Components.ChatList
import com.example.capstoneapp.kakatalk.ui.Frame.NotificationScreen

@Composable
fun Kakao_List(navController: NavController, problem: Problem){
    NotificationScreen(navController = navController, problem = problem) {
        ChatList()
    }
}
@Preview
@Composable
fun KakaoListPreview(){
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    Kakao_List(navController = navController, problem = problemViewModel.getProblemValue()!!)
}