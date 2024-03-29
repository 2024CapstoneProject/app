package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
        ChatRoom(chatMessages = photoMessages)
    }
}

@Composable
fun photoBlock(photoList : List<Int>){
    Box(modifier = Modifier
        .height(200.dp)
    ){
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                state = LazyListState()
            ){
                items(photoList){photo ->
                    photoCard(photo)
                }

            }
    }
}

@Composable
fun photoCard(photoId: Int) {
    var buttonClick by rememberSaveable { mutableStateOf(false) }
    val ImageBoxcolor: Color
    val buttonColor: Color
    val buttonContainerColor: Color
    val border: Dp
    val icon: ImageVector
    var alpha = 1f

    if (buttonClick) {
        ImageBoxcolor = Color.Yellow
        buttonColor = Color.Yellow
        buttonContainerColor = Color.Yellow
        border = 4.dp
        icon = Icons.Filled.Favorite
        alpha = 0.5f
    } else {
        ImageBoxcolor = Color.Black
        buttonColor = Color.Gray
        buttonContainerColor = Color.White
        border = 1.dp
        icon = Icons.Filled.FavoriteBorder
        alpha = 1f
    }

    Box(
        modifier = Modifier
            .width(150.dp)
            .width(150.dp)
            .border(border, ImageBoxcolor)
    ) {

        Image(
            painter = painterResource(id = photoId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.alpha(alpha)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.TopEnd){
            OutlinedButton(
                onClick = {buttonClick=true},
                border = BorderStroke(2.dp, buttonColor),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = buttonContainerColor)
            ) {}
        }



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
    photoBlock(photoList)
}