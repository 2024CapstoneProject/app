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
import com.example.capstoneapp.Frame.NotificationScreen
import com.example.capstoneapp.R
import com.example.capstoneapp.Repository.MenuItem
import com.example.capstoneapp.component.CafeMenuBar
import com.example.capstoneapp.component.CafeMenuBarFormat
import com.example.capstoneapp.component.CafeMenuList
import com.example.capstoneapp.component.SelectedMenuSpec


@Composable
fun CafeKioskScreen(){
    NotificationScreen {CafeMenuScreen()}

}

@Composable
fun CafeMenuScreen(){

    val orderItems = remember{
        mutableStateListOf<MenuItem>()
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
            * */
            CafeMenuList(selectedMenu = selectedMenu){selectedItem ->
                orderItems.add(selectedItem)
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
                Box(//선택한 메뉴 보여줌
                    modifier = Modifier
                        .width(230.dp)
                        .height(100.dp)
                ){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ){//선택한 메뉴 갯수에 따라 메뉴리스트 조회가능
                        items(orderItems.size){ i->
                            SelectedMenuSpec(selectedMenuName = orderItems.get(i).name)
                        }
                    }
                }
            }

            Divider(
                color = Color.Gray, // 선의 색상 지정
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(76.dp),
                    contentAlignment = Alignment.CenterStart

                ) {

                    Column(
                        modifier = Modifier.padding(start=4.dp)
                    ){
                        Text(
                            text="남은 시간",
                            style= TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.Cursive
                            ),
                        )

                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        Color.Red,
                                        fontSize =34.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontFamily = FontFamily.SansSerif
                                    ),
                                ) {
                                    append("120")
                                }
                                append("초")
                            },
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.SansSerif,

                            )

                    }

                }

                Divider(
                    color = Color.Gray, // 선의 색상 지정
                    thickness = 1.dp, // 선의 두께 지정
                    modifier = Modifier
                        .padding(2.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 4.dp),
                    contentAlignment = Alignment.TopStart
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                text="선택 상품",
                                style= TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily.SansSerif
                                ),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            Color.Red,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontFamily = FontFamily.SansSerif
                                        ),
                                    ) {
                                        append(orderItems.size.toString())
                                    }
                                    append("개")
                                },
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.SansSerif,
                            )
                        }
                        Spacer(modifier=Modifier.height(8.dp))

                        Button(
                            modifier = Modifier
                                .width(114.dp)
                                .height(70.dp)
                            ,
                            shape = MaterialTheme.shapes.small,
                            colors=ButtonDefaults.buttonColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            ),
                            onClick = {}
                        ){
                            Text(
                                text="결제하기",
                                fontSize=24.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun cafeKioskScreenPreview(){
    CafeKioskScreen()
}