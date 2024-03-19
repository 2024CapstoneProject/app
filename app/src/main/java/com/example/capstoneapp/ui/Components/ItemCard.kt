package com.example.capstoneapp.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.data.Repository.MenuItem

@Composable
fun ItemCard(item: MenuItem, onClick :()->Unit){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(130.dp)
            .height(180.dp)
            .absolutePadding(right=8.dp,left=8.dp)
            .background(Color.White)
            .border(1.dp, Color.Black)
            .clickable(onClick = onClick)
    ){
        Icon(
            painter = painterResource(id = item.iconResourceId),
            contentDescription = "",
            modifier = Modifier
                .padding(top=24.dp)
                .width(88.dp)
                .height(88.dp)
        )

        Text(
            text=item.name,
            style= TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier.padding(top=4.dp)
        )

        Text(

            text= buildAnnotatedString {
                withStyle(
                    style= SpanStyle(
                        Color.Red,
                        fontSize=14.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                ){
                    append(item.price.toString())
                }
                append("원")
            },
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            fontSize=12.sp,
            fontWeight = FontWeight.Bold
        )

    }

}