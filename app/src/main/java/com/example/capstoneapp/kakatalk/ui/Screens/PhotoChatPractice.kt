package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessage
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessageRepository
import com.example.capstoneapp.kakatalk.ui.Components.ChatRoom
import com.example.capstoneapp.kakatalk.ui.Components.photoBlock
import com.example.capstoneapp.nav.repository.KakaotalkProblem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

@Composable
fun PhotoChatPractice(navController: NavController, problem: KakaotalkProblem) {
    val photoMessages = remember { mutableStateListOf<ChatMessage>() }
    val photoList = remember { mutableStateListOf<Int>() }
    var resanswer by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        photoMessages.addAll(ChatMessageRepository.getPhotoSend(problem.person, problem.index))
        photoList.addAll(ChatMessageRepository.getPhotoList())
    }
    ChatRoom(chatMessages = photoMessages, photoList, problem) {
        resanswer = it
        if (resanswer) {
            navController.popBackStack("KakaoPractice0", inclusive = true)
        }
    }
}

@Composable
@Preview
fun PhotoChatPracticePreview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    PhotoChatPractice(navController, problemViewModel.getKakaotalkProblemValue()!!)
}

@Composable
@Preview
fun photoCardPreview() {
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    val photoList = remember { mutableStateListOf<Int>() }
    photoList.addAll(ChatMessageRepository.getPhotoList())
    photoBlock(132.dp, photoList) {}
}