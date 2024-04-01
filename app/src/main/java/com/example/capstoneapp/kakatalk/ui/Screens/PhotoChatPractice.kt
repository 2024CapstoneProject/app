package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessage
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessageRepository
import com.example.capstoneapp.kakatalk.data.Repository.Problem
import com.example.capstoneapp.kakatalk.data.Repository.ProblemRepository
import com.example.capstoneapp.kakatalk.data.ViewModel.ProblemViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.ProblemViewModelFactory
import com.example.capstoneapp.kakatalk.ui.Components.ChatRoom
import com.example.capstoneapp.kakatalk.ui.Components.photoBlock
import com.example.capstoneapp.kakatalk.ui.Frame.NotificationScreen

@Composable
fun PhotoChatPractice(navController: NavController, problem: Problem){
    val photoMessages = remember { mutableStateListOf<ChatMessage>() }
    val photoList = remember{mutableStateListOf<Int>()}
    LaunchedEffect(Unit) {
        photoMessages.addAll(ChatMessageRepository.getPhotoSend())
        photoList.addAll(ChatMessageRepository.getPhotoList())
    }

    NotificationScreen(navController = navController, problem = problem) {
        ChatRoom(chatMessages = photoMessages,photoList)
    }
}

@Composable
@Preview
fun PhotoChatPracticePreview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    PhotoChatPractice(navController, problemViewModel.getProblemValue()!!)
}

@Composable
@Preview
fun photoCardPreview(){
    val photoList = remember{mutableStateListOf<Int>()}
    photoList.addAll(ChatMessageRepository.getPhotoList())
    photoBlock(132.dp,photoList)
}