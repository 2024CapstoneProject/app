package com.example.capstoneapp.fastfood.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.fastfood.ui.theme.fontFamily
import com.example.capstoneapp.nav.repository.MenuItem

@Composable
fun ItemCard(item: MenuItem, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(18.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(id = item.iconResourceId),
            contentDescription = "",
            modifier = Modifier
                .width(72.dp) // 아이콘의 너비를 48dp로 설정
                .height(64.dp)
        )
        Text(
            text = item.name,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = item.price.toString() + "원",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = fontFamily
            ),
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
fun OptionCard(
    onClick: () -> Unit,
    text: String,
    icon: Painter,
    showBorder: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(18.dp)
            .clickable(onClick = onClick)
            .then(if (showBorder) Modifier.border(BorderWidth, BorderColor, BorderShape) else Modifier)
    ) {
        Icon(
            painter = icon,
            contentDescription = "",
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}