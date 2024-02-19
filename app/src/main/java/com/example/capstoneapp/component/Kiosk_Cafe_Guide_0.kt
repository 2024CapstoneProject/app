package com.example.capstoneapp.component

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.Frame.TopAppBar
import com.example.capstoneapp.R
import com.example.capstoneapp.ui.theme.CapstoneAppTheme


@Composable
fun guideImage() {
    val imageResources = listOf(
        R.drawable.ex1,
        R.drawable.ex2,
        R.drawable.ex3
    )
    var currentImageIndex by remember { mutableStateOf(0) }
    val currentImageResourceId = imageResources[currentImageIndex]

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${getResourceName(currentImageResourceId)}",
                style = TextStyle(fontSize = 30.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { currentImageIndex = showPreviousImage(imageResources.size, currentImageIndex) },
                    //modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_),
                        contentDescription = "Previous"
                    )
                }

                Spacer(modifier = Modifier.width(0.dp))

                Image(
                    painter = painterResource(id = imageResources[currentImageIndex]),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(20.dp))
                )

                Spacer(modifier = Modifier.width(0.dp))

                IconButton(
                    onClick = { currentImageIndex = showNextImage(imageResources.size, currentImageIndex) },
                    //modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_forward),
                        contentDescription = "Next"
                    )
                }
            }
        }
    }
}

@Composable
fun getResourceName(resourceId: Int): String {
    val context = LocalContext.current
    return try {
        context.resources.getResourceName(resourceId).substringAfterLast('/')
    } catch (e: Resources.NotFoundException) {
        "Unknown"
    }
}

fun showNextImage(size: Int, currentIndex: Int): Int {
    return (currentIndex + 1) % size
}

fun showPreviousImage(size: Int, currentIndex: Int): Int {
    return if (currentIndex == 0) size - 1 else currentIndex - 1
}

@Composable
fun guideText() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ){
        Text(
            text = "사진을 옆으로 넘기거나",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "화살표를 눌러 확인해주세요",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "사진을 누르면 확대됩니다",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,)
    }
}

@Preview(showBackground = true)
@Composable
fun cafeGuideScreenPreview() {
    CapstoneAppTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar()
            Spacer(modifier = Modifier.height(40.dp))
            guideImage()
            Spacer(modifier = Modifier.height(16.dp))
            guideText()
        }
    }
}