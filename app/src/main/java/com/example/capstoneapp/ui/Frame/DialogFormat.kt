package com.example.capstoneapp.ui.Frame


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.capstoneapp.R

@Preview
@Composable
fun DialogFormat(

) {
    Dialog(onDismissRequest = {}){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            shape= RoundedCornerShape(16.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.cafe_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .width(70.dp)
                        .height(70.dp)
                )

                Text(text="메뉴이름", textAlign = TextAlign.Center)

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly

            ){
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
                    onClick = {}
                ){
                    Text(
                        text="취소",
                        fontSize=24.sp
                    )

                }

                Button(
                    modifier = Modifier
                        .width(114.dp)
                        .height(70.dp)
                    ,
                    shape = MaterialTheme.shapes.small,
                    colors= ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    onClick = {}
                ){
                    Text(
                        text="주문담기",
                        fontSize=24.sp
                    )

                }
            }
        }
    }
}