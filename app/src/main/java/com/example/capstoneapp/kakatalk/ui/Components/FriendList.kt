package com.example.capstoneapp.kakatalk.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth

@Composable
fun friendList(friendList: List<Pair<Int, String>>, listState: LazyListState,showBorder:Boolean) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(2.dp)
            .background(Color.Gray)

    )
    //친구 목록 LazyColumn
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth()
            .clickable{},
    ) {
        itemsIndexed(friendList) { index, item ->
            PersonalProfile(
                painter = painterResource(id = item.first),
                name = item.second,
                showBorder = showBorder && index == 2//테두리 설정
            )
        }
    }
}

@Composable
fun PersonalProfile(painter: Painter, name: String,showBorder:Boolean) {
    //프로필 기본 설정
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp)
            .clickable { }
            .then(if (showBorder) Modifier.border(BorderWidth, BorderColor, BorderShape) else Modifier),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
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
            text = name,
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
