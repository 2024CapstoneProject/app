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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.kakatalk.data.Repository.Problem
import com.example.capstoneapp.kakatalk.data.Repository.ProblemRepository
import com.example.capstoneapp.kakatalk.data.ViewModel.ProblemViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.ProblemViewModelFactory
import com.example.capstoneapp.kakatalk.ui.Components.ChatDetail
import com.example.capstoneapp.kakatalk.ui.Components.ChatMessageItem
import com.example.capstoneapp.kakatalk.ui.Components.ChatRoom
import com.example.capstoneapp.kakatalk.ui.Components.TextBox
import com.example.capstoneapp.kakatalk.ui.Frame.NotificationScreen
import kotlinx.coroutines.launch

@Composable
fun Kakao_FriendList(navController: NavController, problem: Problem){
    NotificationScreen(navController = navController, problem = problem) {
        friendList()
    }
}

@Preview
@Composable
fun friendList(){
    val listState = rememberLazyListState()
    var weight: Float = 1f

    val friendList = listOf(
        Pair(painterResource(id = R.drawable.sample_1),"아내"),
        Pair(painterResource(id = R.drawable.sample_2),"남편"),
        Pair(painterResource(id = R.drawable.burgerimg),"아들"),
        Pair(painterResource(id = R.drawable.burgerimg),"딸")
    )

    val isButtonClick = remember{ mutableStateOf("친구") }


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
                    0.dp,
                    Color.Transparent,
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text="친구",
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
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .border(0.dp, Color.Black))

        PersonalProfile(painterResource(id = R.drawable.sample_3),"김희연")

        Spacer(modifier = Modifier
            .fillMaxWidth(0.96f)
            .height(1.dp)
            .border(0.dp, Color.Black)
        )

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxWidth()
                ){
                    itemsIndexed(friendList){index,item->
                        if(isButtonClick.value.equals("친구")){
                            PersonalProfile(
                                painter = item.first,
                                name = item.second)
                        }else{
                            TestPersonalProfile(
                            painter = item.first,
                            name = item.second)

                        }
                    }
                }
            }
            BottomButton(){
                isButtonClick.value = it
            }
        }
    }
}

@Composable
fun BottomButton(isButtonName:(String)->Unit){
    val isButtonClick = remember{ mutableStateOf("친구") }

    val icons = listOf(
        Icons.Filled.Person,
        Icons.Filled.Email,
        Icons.Filled.Menu
    )

    val names = listOf(
        "친구", "채팅","더보기"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                Color.LightGray,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ){

        for(i in icons.indices){
            val color = if(isButtonClick.value.equals(names[i]))Color.Black else Color.DarkGray

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        isButtonClick.value = names[i]
                        isButtonName(names[i]) }
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Icon(
                    imageVector = icons[i],
                    contentDescription = "",
                    tint = color,
                    modifier = Modifier
                        .size(36.dp)

                )
                Text(
                    text = names[i],
                    fontSize = 16.sp,
                    color = color,
                fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PersonalProfile(painter:Painter,name:String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp)
            .clickable { },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painter,
            contentDescription = null, // 이미지에 대한 접근성 설명은 필요하지 않습니다
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp) // 이미지 크기 조정
                .padding(top = 8.dp, start = 8.dp)
                .border(2.dp, Color.Transparent, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(20.dp))
        )
        Text(
            text=name,
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start=8.dp)
        )
    }
}

@Composable
fun TestPersonalProfile(painter:Painter,name:String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp)
            .clickable { },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painter,
            contentDescription = null, // 이미지에 대한 접근성 설명은 필요하지 않습니다
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp) // 이미지 크기 조정
                .padding(top = 8.dp, start = 8.dp)
                .border(2.dp, Color.Transparent, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(20.dp))
        )
        Text(
            text="김희연",
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start=8.dp)
        )
    }
}


@Preview
@Composable
fun Kakao_FriendList_Preview(){
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    Kakao_FriendList(navController =navController , problem = problemViewModel.getProblemValue()!!)
}