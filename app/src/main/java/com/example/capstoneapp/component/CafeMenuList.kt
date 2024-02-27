package com.example.capstoneapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.R

@Composable
fun CafeMenuList(selectedMenu : String,onItemClicked:(MenuItem) ->Unit) {

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(16.dp)
    ) {
        val items = when (selectedMenu) {
            "커피(HOT)" -> listOf(
                MenuItem("아메리카노", R.drawable.cafe_icon, 2500),
                MenuItem("카페라떼", R.drawable.cafe_icon, 3000)
            )

            else -> listOf()
        }

        val rows = items.chunked(2)

        rows.forEach { rowItems ->
            Row {
                rowItems.forEach { item ->
                    ItemCard(item = item) {
                        onItemClicked(item)
                    }
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

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
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Cursive
            ),
            modifier = Modifier.padding(top=4.dp)
        )

        Text(

            text= buildAnnotatedString {
                withStyle(
                    style=SpanStyle(
                        Color.Red,
                        fontSize=16.sp,
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

data class MenuItem(
    val name:String,
    val iconResourceId:Int,
    val price:Int
)

@Preview
@Composable
fun CafeMenuListPreview(){
    CafeMenuList("커피(HOT)", onItemClicked = {})
}