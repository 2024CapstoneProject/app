package com.example.capstoneapp.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.Frame.DialogFormat
import com.example.capstoneapp.Frame.NotificationScreen
import com.example.capstoneapp.R
import com.example.capstoneapp.Repository.MenuItem
import com.example.capstoneapp.component.CafeMenuBar
import com.example.capstoneapp.component.CafeMenuBarFormat
import com.example.capstoneapp.component.CafeMenuList
import com.example.capstoneapp.component.ShowMenu


@Composable
fun CafeKioskScreen(){
    NotificationScreen {ShowMenu(navController = rememberNavController())}

}

@Composable
fun CafeMenuScreen(){


    val orderItems = remember{
        mutableStateListOf<Pair<MenuItem,Int>>()
    }

    var selectedMenu by remember{ mutableStateOf("커피(HOT)") }
    val menuCategory = listOf("커피(HOT)","커피(ICE)","티(TEA)")

    val dummyOrderItems = listOf(
        MenuItem(1,"아메리카노", R.drawable.cafe_icon, 2500),
        MenuItem(2,"카페라떼", R.drawable.cafe_icon, 3000)
    )

    Column(){
        Column (//메뉴 리스트 Column
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CafeMenuBarFormat{
                CafeMenuBar(
                    menuItems = menuCategory,
                    onMenuItemClick = {menuItem -> selectedMenu =menuItem
                })
            }
            /*
            * 선택한 메뉴 종류에 따라 메뉴 리스트를 보여줌
            *
            * selectedMenu : 종류
            * selectedItem : 선택한 메뉴
            *  */
            CafeMenuList(selectedMenu = selectedMenu){selectedItem ->
                val targetPair = orderItems.firstOrNull(){it.first.name == selectedItem.name}

                if(targetPair != null){
                    val index = orderItems.indexOf(targetPair)
                    orderItems[index] = targetPair.copy(second = targetPair.second+1)
                } else
                    orderItems.add(Pair(selectedItem,1))
            }
        }

        Column( //빈공간
            modifier = Modifier.fillMaxWidth()
        ){
            Spacer(modifier = Modifier.height(160.dp))
        }

        Row(//선택한 메뉴, 남은시간, 결제 버튼 공간
            modifier = Modifier.fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .width(230.dp)
                    .fillMaxHeight()
            ){
                Divider(
                    color = Color.Gray, // 선의 색상 지정
                    thickness = 2.dp, // 선의 두께 지정
                    modifier = Modifier
                        .padding()
                        .width(234.dp)
                )
            }

            Divider(
                color = Color.Gray, // 선의 색상 지정
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
            )
        }
    }
}

@Preview
@Composable
fun cafeKioskScreenPreview(){
    CafeKioskScreen()
}