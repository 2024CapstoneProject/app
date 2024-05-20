package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessage
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessageRepository
import com.example.capstoneapp.kakatalk.ui.Components.ChatRoom
import com.example.capstoneapp.nav.repository.KakaotalkProblem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

@Composable
fun ChattingScreen(navController: NavController, problem: KakaotalkProblem) {
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    val photoList = remember { mutableStateListOf<Int>() }
    var showBorder by remember { mutableStateOf(false) }
    var resanswer by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (problem.type == "simple") {
            chatMessages.addAll(
                ChatMessageRepository.getSimpleChat(
                    person = problem.person,
                    problemNumber = problem.index
                )
            )
        } else {
            chatMessages.addAll(
                ChatMessageRepository.getPhotoSend(
                    person = problem.person,
                    problemNumber = problem.index
                )
            )
        }
        photoList.addAll(ChatMessageRepository.getPhotoList())
    }

    ChatRoom(chatMessages = chatMessages, photoList = photoList, problem = problem) {
        resanswer = it
        if (resanswer) {
            navController.popBackStack("KakaoPractice0", inclusive = true)
        }
    }


}

@Preview
@Composable
fun ChattingScreenPreview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    ChattingScreen(
        navController = navController,
        problem = problemViewModel.getKakaotalkProblemValue()!!
    )
}