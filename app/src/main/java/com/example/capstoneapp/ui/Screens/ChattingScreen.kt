package com.example.capstoneapp.ui.Screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.data.Repository.ChatMessage
import com.example.capstoneapp.data.Repository.ChatMessageRepository
import com.example.capstoneapp.data.Repository.Problem
import com.example.capstoneapp.data.Repository.ProblemRepository
import com.example.capstoneapp.data.ViewModel.ProblemViewModel
import com.example.capstoneapp.data.ViewModel.ProblemViewModelFactory
import com.example.capstoneapp.ui.Components.ChatRoom
import com.example.capstoneapp.ui.Frame.NotificationScreen

@Composable
fun ChattingScreen(navController: NavController, problem: Problem) {
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    LaunchedEffect(Unit) {
        chatMessages.addAll(ChatMessageRepository.getSimpleChat())
    }

    NotificationScreen(navController = navController, problem = problem) {
        ChatRoom(chatMessages = chatMessages)
    }
}

@Preview
@Composable
fun ChattingScreenPreview(){
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    ChattingScreen(navController =navController , problem = problemViewModel.getProblemValue()!!)
}