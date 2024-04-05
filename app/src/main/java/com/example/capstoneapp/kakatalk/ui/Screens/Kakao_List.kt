package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.kakatalk.data.Repository.ChatItemData
import com.example.capstoneapp.kakatalk.data.Repository.FriendChatRoomRepository
import com.example.capstoneapp.cafe.data.Repository.Problem
import com.example.capstoneapp.cafe.data.Repository.ProblemRepository
import com.example.capstoneapp.kakatalk.data.ViewModel.ProblemViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.ProblemViewModelFactory
import com.example.capstoneapp.kakatalk.ui.Components.ChatList
import com.example.capstoneapp.cafe.ui.Frame.NotificationScreen

@Composable
fun Kakao_List(navController: NavController, problem: Problem) {
    val listState = rememberLazyListState()
    val chatData = remember { mutableStateListOf<ChatItemData>() }
    chatData.addAll(FriendChatRoomRepository.getchatData())
    NotificationScreen(navController = navController, problem = problem) {
        ChatList(navController, chatData, listState)
    }
}

@Preview
@Composable
fun KakaoListPreview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    Kakao_List(navController = navController, problem = problemViewModel.getProblemValue()!!)
}

@Preview
@Composable
fun imagePreview() {
    val listState = rememberLazyListState()
    val chatData = remember { mutableStateListOf<ChatItemData>() }
    chatData.addAll(FriendChatRoomRepository.getchatData())
    val navController = rememberNavController()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.top),
            contentDescription = "상단바",
            modifier = Modifier
                .size(width = 400.dp, height = 60.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        ChatList(navController, chatData, listState)
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.bottom),
            contentDescription = "하단바",
            modifier = Modifier
                .size(width = 400.dp, height = 60.dp),
        )
    }
}