package com.example.capstoneapp.taxi.ui.screens.practice

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.R
import com.example.capstoneapp.fastfood.ui.theme.fontFamily

@Preview
@Composable
fun ChooseTaxiScreen() {
    val taxiOptions = listOf(
        TaxiOption("빠른 택시", "부르면 바로 배차되는 택시", "7,300원", R.drawable.temp_way_map),
        TaxiOption("일반 택시", "주변에 가까운 택시 호출", "5,800원", R.drawable.temp_way_map),
        TaxiOption("모범 택시", "편안한 모범택시 호출", "9,800원", R.drawable.temp_way_map),
        TaxiOption("대형 택시", "넓고 쾌적한 대형차량 호출", "8,300원", R.drawable.temp_way_map)
    )
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Image(
            painter = painterResource(id = R.drawable.temp_way_map),
            contentDescription = "temp_way_map",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(40.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "마루아파트",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = ">",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "디지털배움터",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn{
                items(taxiOptions) { option ->
                    TaxiOptionItem(option)
                }
            }
        }
    }
}

@Composable
fun TaxiOptionItem(option: TaxiOption) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = option.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = option.title,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = option.subtitle,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )
        }
        Text(
            text = option.price,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        )
    }
}

data class TaxiOption(
    val title: String,
    val subtitle: String,
    val price: String,
    val imageRes: Int
)