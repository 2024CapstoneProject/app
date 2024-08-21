package com.example.capstoneapp.taxi.ui.screens.practice
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.kakatalk.ui.Components.CloseDialog

@Composable
fun TaxiInform(navController: NavController) {
    var closeDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //호출취소 버튼
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            TextButton(onClick = {  }) {
                Text(
                    "호출취소",
                    color = Color.Gray,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ImageButton(
                imageResource = R.drawable.phone,
                contentDescription = "전화",
                onClick = { }
            )
            Spacer(modifier = Modifier.width(50.dp))
            CircularImage(imageResource = R.drawable.person)
            Spacer(modifier = Modifier.width(50.dp))
            ImageButton(
                imageResource = R.drawable.msg,
                contentDescription = "문자",
                onClick = { }
            )
        }

        Text(
            text = "김oo\n소나타\n경기 바 1234",//problem으로 수정
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )
        Image(
            painter = painterResource(id = R.drawable.taxi_map),
            contentDescription = "temp_way_map",
            modifier = Modifier
                .fillMaxSize()
                .clickable{
                    closeDialog = true
                },
            contentScale = ContentScale.Crop
        )
    }
    if (closeDialog) {
        CloseDialog(
            onDismiss = {
                closeDialog = false
                navController.popBackStack("TaxiHome", inclusive = true)
            }
        )
    }
}

@Composable
fun ImageButton(
    imageResource: Int,
    contentDescription: String?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.2f))
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}



@Composable
fun CircularImage(imageResource: Int) {
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = null,
        modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
fun InformPreview() {
    val navController = rememberNavController()
    TaxiInform(navController)
}
