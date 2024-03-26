package com.example.capstoneapp.ui.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.data.Repository.ChatMessage
import com.example.capstoneapp.data.Repository.ChatMessageRepository
import com.example.capstoneapp.data.Repository.Problem
import com.example.capstoneapp.data.Repository.ProblemRepository
import com.example.capstoneapp.data.ViewModel.ProblemViewModel
import com.example.capstoneapp.data.ViewModel.ProblemViewModelFactory
import com.example.capstoneapp.ui.Components.ChatMessageItem
import com.example.capstoneapp.ui.Components.ChatRoom
import com.example.capstoneapp.ui.Frame.NotificationScreen

@Composable
fun KakaoPractice1(navController: NavController, problem: Problem) {
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    LaunchedEffect(Unit) {
        chatMessages.addAll(ChatMessageRepository.getSimpleChat())
    }

    NotificationScreen(navController = navController, problem = problem) {
        ChatRoom(chatMessages = chatMessages, chatDetail = { messages ->
            ChatDetail(messages)
        })
    }
}

@Composable
fun ChatDetail(chatMessages: List<ChatMessage>) {

    Box(modifier = Modifier.padding(top = 20.dp)) {
        LazyColumn {
            itemsIndexed(chatMessages) { index, message ->
                ChatMessageItem(message)
            }
        }
    }
}

@Composable
@Preview
fun KakaoPractice1Preview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    KakaoPractice1(navController, problemViewModel.getProblemValue()!!)
}