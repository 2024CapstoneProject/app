package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessage
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessageRepository
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory
import com.example.capstoneapp.kakatalk.ui.Components.ChatRoom
import com.example.capstoneapp.cafe.ui.Frame.NotificationScreen

@Composable
fun ChattingScreen(navController: NavController, problem: Problem) {
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    val photoList = remember{mutableStateListOf<Int>()}

    LaunchedEffect(Unit) {
        chatMessages.addAll(ChatMessageRepository.getSimpleChat())
        photoList.addAll(ChatMessageRepository.getPhotoList())
    }

    NotificationScreen(navController = navController, problem = problem) {
        ChatRoom(chatMessages = chatMessages,photoList)
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