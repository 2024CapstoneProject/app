package com.example.capstoneapp.kakatalk.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.kakatalk.data.Repository.ChatItemData
import com.example.capstoneapp.kakatalk.data.Repository.FriendChatRoomRepository
import com.example.capstoneapp.kakatalk.ui.Components.ChatList
import com.example.capstoneapp.kakatalk.ui.Components.PersonalProfile
import com.example.capstoneapp.kakatalk.ui.Components.friendList
import com.example.capstoneapp.kakatalk.ui.Components.profileDialog
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

@Composable
fun Kakao_FriendChatList(navController: NavController, problem: Problem, showBorder: Boolean) {
    val chatData = remember { mutableStateListOf<ChatItemData>() }
    val friendList = remember { mutableStateListOf<Pair<Int, String>>() }

    LaunchedEffect(Unit) {
        chatData.addAll(FriendChatRoomRepository.getchatData())
        friendList.addAll(FriendChatRoomRepository.getfriendList())
    }
    FriendChatList(navController, chatData, friendList, showBorder)
}

@Composable
fun FriendChatList(
    navController: NavController,
    chatData: List<ChatItemData>,
    friendList: List<Pair<Int, String>>,
    showBorder: Boolean
) {
    var weight: Float = 1f
    val listState = rememberLazyListState()
    val isButtonClick = remember { mutableStateOf("친구") }
    val showPopup = remember { mutableStateOf(false) }
    val username = remember { mutableStateOf("") }
    val userImage = remember { mutableStateOf(0) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White, shape = RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    0.dp, Color.Transparent, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = isButtonClick.value,
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "friendSearch",
                modifier = Modifier.size(36.dp)
            )
        }


        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            when (isButtonClick.value) {
                "친구" -> {
                    PersonalProfile(
                        painter = painterResource(id = R.drawable.sample_3),
                        name = "김희연",
                        showBorder = false,
                        onItemClick = {
                            showPopup.value = true
                            username.value = "김희연"
                            userImage.value = R.drawable.sample_3
                        }
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(2.dp)
                            .background(Color.Gray)

                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {
                        friendList(
                            friendList = friendList,
                            listState = listState,
                            showBorder,
                            showProfile = { isShow, name, imageId ->
                                if (isShow) {
                                    username.value = name
                                    userImage.value = imageId
                                    showPopup.value = isShow
                                }
                            }
                        )
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        ChatList(
                            navController = navController,
                            chatData = chatData,
                            listState = listState,
                            showBorder
                        )
                    }
                }
            }
            BottomButton() {
                isButtonClick.value = it
            }
        }
    }
    if (showPopup.value) {
        PersonalPopup(username.value, userImage.value) {
            showPopup.value = it
            if (!it) {
                username.value = ""
                userImage.value = 0
            }
        }
    }
}

@Composable
fun PersonalPopup(userName: String, userImage: Int, closePopup: (Boolean) -> Unit) {
    profileDialog(onClickOutside = { closePopup(false) }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 8.dp, top = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "friendSearch",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { closePopup(false) }
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                Image(
                    painter = painterResource(id = userImage),
                    contentDescription = null, // 이미지에 대한 접근성 설명은 필요하지 않습니다
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(132.dp) // 이미지 크기 조정
                        .border(2.dp, Color.Transparent, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(20.dp))
                )
                Text(
                    text = userName,
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.White)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(88.dp)
                        .background(Color.Transparent)
                        .wrapContentSize()
                        .clickable {}
                        .padding(top = 8.dp, bottom = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                    Text(
                        text = if (userName.equals("김희연")) "나와의 채팅" else "1:1채팅",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun BottomButton(isButtonName: (String) -> Unit) {
    val isButtonClick = remember { mutableStateOf("친구") }

    val icons = listOf(
        Icons.Filled.Person, Icons.Filled.Email, Icons.Filled.Menu
    )

    val names = listOf(
        "친구", "채팅", "더보기"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                Color.LightGray, shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        for (i in icons.indices) {
            val color = if (isButtonClick.value.equals(names[i])) Color.Black else Color.DarkGray

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        isButtonClick.value = names[i]
                        isButtonName(names[i])
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icons[i],
                    contentDescription = "",
                    tint = color,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = names[i], fontSize = 16.sp, color = color, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SearchBox() {

}

@Preview
@Composable
fun Kakao_FriendList_Preview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    Kakao_FriendChatList(
        navController = navController, problem = problemViewModel.getProblemValue()!!, false
    )
}