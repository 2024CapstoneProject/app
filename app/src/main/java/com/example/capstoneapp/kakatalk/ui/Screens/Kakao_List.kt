package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.kakatalk.data.Repository.ChatItemData
import com.example.capstoneapp.kakatalk.data.Repository.FriendChatRoomRepository
import com.example.capstoneapp.kakatalk.ui.Components.ChatList
import com.example.capstoneapp.nav.repository.KakaotalkProblem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

@Composable
fun Kakao_List(navController: NavController, problem: KakaotalkProblem, showBorder: Boolean) {
    val listState = rememberLazyListState()
    val chatData = remember { mutableStateListOf<ChatItemData>() }
    chatData.addAll(FriendChatRoomRepository.getchatData(problem))
    ChatList(navController, chatData, listState, showBorder)
}

@Preview
@Composable
fun KakaoListPreview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    Kakao_List(
        navController = navController,
        problem = problemViewModel.getKakaotalkProblemValue()!!,
        true
    )
}

@Preview
@Composable
fun imagePreview() {
//    val listState = rememberLazyListState()
//    val chatData = remember { mutableStateListOf<ChatItemData>() }
//    chatData.addAll(FriendChatRoomRepository.getchatData())
//    val navController = rememberNavController()
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(color = Color.White)
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.top),
//            contentDescription = "상단바",
//            modifier = Modifier
//                .size(width = 400.dp, height = 60.dp),
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//        ChatList(navController, chatData, listState,true)
//        Spacer(modifier = Modifier.height(10.dp))
//        Image(
//            painter = painterResource(id = R.drawable.bottom),
//            contentDescription = "하단바",
//            modifier = Modifier
//                .size(width = 400.dp, height = 60.dp),
//        )
//    }
}