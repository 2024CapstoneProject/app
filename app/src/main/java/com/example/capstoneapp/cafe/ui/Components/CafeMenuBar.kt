package com.example.capstoneapp.cafe.ui.Components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.nav.repository.Problem

@Composable
fun CafeMenuBar(
    menuItems: List<String>,
    selectedMenu: String,
    onMenuItemClick: (String) -> Unit,
    showBorder: Boolean,
    problem: Problem
) {
    var type = 0
    if (problem.c_menu == "HOT아메리카노" || problem.c_menu == "HOT카페라떼") {
        type = 0
    } else if (problem.c_menu == "ICE아메리카노" || problem.c_menu == "ICE바닐라라떼") {
        type = 1
    } else {
        type = 2
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically

    ) {
        IconButton(
            onClick = { onMenuItemClick("HOME") },
            modifier = Modifier
                .padding(top = 12.dp)
                .width(60.dp)
                .height(60.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "setting"
            )
        }
        menuItems.forEachIndexed() { index, item ->
            Box(
                modifier = Modifier
                    .padding(top = 0.dp)
                    .fillMaxHeight()
                    .wrapContentWidth()
//                    .then(
//                        if (showBorder&&index==type) Modifier.border(
//                            BorderWidth,
//                            BorderColor,
//                            BorderShape
//                        ) else Modifier
//                    )
            ) {
                TextButton(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxHeight()
                        .wrapContentWidth()
                        .then(
                            if (showBorder && index == type) Modifier.border(
                                BorderWidth,
                                BorderColor,
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            ) else Modifier
                        ),
                    onClick = {
                        onMenuItemClick(item)
                    },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (item == selectedMenu) Color.White else Color.Transparent,
                        contentColor = if (item == selectedMenu) Color.Black else Color.White
                    ),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

                ) {
                    Text(text = item, fontSize = 14.sp)
                }
            }
        }
        Spacer(Modifier.width(10.dp))
    }
}




