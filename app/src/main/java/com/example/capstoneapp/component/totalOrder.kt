package com.example.capstoneapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.Navigation.Screen
import com.example.capstoneapp.Repository.MenuItem

@Composable
fun totalOrder(orderItems:List<Pair<MenuItem,Int>>, navController : NavController
){
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
                                append(orderItems.sumOf { it.second }.toString())
                            }
                            append("개")
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.SansSerif,
                    )
                }
                Spacer(modifier= Modifier.height(8.dp))

                Button(
                    modifier = Modifier
                        .width(114.dp)
                        .height(70.dp)
                    ,
                    shape = MaterialTheme.shapes.small,
                    colors= ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    ),
                    onClick = { navController.navigate("PayTest")}
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