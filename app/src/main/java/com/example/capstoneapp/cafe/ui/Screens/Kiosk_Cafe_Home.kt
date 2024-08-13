package com.example.capstoneapp.cafe.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.cafe.ui.theme.CapstoneAppTheme

@Composable
fun CafeHomeScreen(navController: NavController) {
    CapstoneAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        modifier = Modifier.size(56.dp),
                        onClick = { navController.navigate("Guide0") },
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(56.dp)
                        )

                    }

                    Text(
                        text = "카페",
                        fontSize = 32.sp,
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OptionButton(
                    navController = navController,
                    paddingValue = 16.dp,
                    imageVector = Icons.Default.Warning,
                    title = "사진 설명서",
                    description = "사진으로 설명서를 보여줘요.",
                    route = "Guide1"
                )
                OptionButton(
                    navController = navController,
                    paddingValue = 0.dp,
                    imageVector = Icons.Default.Warning,
                    title = "연습해보기",
                    description = "직접 문제를 풀며 연습해요.",
                    route = "KioskCafePractice0"
                )
            }

        }
    }
}

@Composable
fun OptionButton(
    navController: NavController,
    paddingValue: Dp,
    imageVector: ImageVector,
    title: String,
    description: String,
    route: String
) {
    Button(
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 16.dp,
        ),
        contentPadding = PaddingValues(),
        onClick = {
            navController.navigate(route)
        },
        modifier = Modifier
            .width(360.dp)
            .height(320.dp)
            .padding(bottom = paddingValue),
        colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
        shape = RoundedCornerShape(28.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "",
                    tint = Color(0xFF5C460C),
                    modifier = Modifier.size(72.dp)
                )
                Text(
                    text = title,
                    color = Color.Black,
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                )
                Text(
                    text = description,
                    color = Color(0xFFADADAD),
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 24.sp,
                )
            }

        }

    }

}


@Composable
fun PictureButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    BoxButton(
        text = "사진 설명",
        backgroundColor = Color(0xFFFFFFFF),
        modifier = modifier,
        onClick = onClick
    )

}

@Composable
fun PracticeButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    BoxButton(
        text = "연습 하기",
        backgroundColor = Color(0xFFFFBD42),
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun BoxButton(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(backgroundColor)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Black,
            style = MaterialTheme.typography.displayLarge,
            fontSize = 50.sp,
        )
        Text(
            text = "사진으로 설명서를 보여줘요.",
            color = Color.Black,
            style = MaterialTheme.typography.displayLarge,
            fontSize = 50.sp,
        )
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 846)
@Composable
fun cafeHomeScreenPreview() {
    val navController = rememberNavController()
    CafeHomeScreen(navController = navController)
}
