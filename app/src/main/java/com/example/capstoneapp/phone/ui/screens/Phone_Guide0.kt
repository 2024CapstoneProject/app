package com.example.capstoneapp.phone.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.cafe.ui.theme.CapstoneAppTheme

@Composable
fun PhoneGuide0(navController: NavController) {
    CapstoneAppTheme {
        PhoneGuideScreen(navController)
    }
}

@Composable
fun PhoneGuideScreen(navController: NavController) {
    var showVoiceRecogPopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("Phone_Call_Guide")
                },
                modifier = Modifier
                    .size(150.dp, 150.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "전화",
                        fontSize = 30.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Button(
                onClick = {
                    navController.navigate("Phone_Contact_Guide")
                },
                modifier = Modifier
                    .size(150.dp, 150.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "연락처",
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("Phone_Message_Guide")
                },
                modifier = Modifier
                    .size(150.dp, 150.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFDA77)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "메시지",
                    fontSize = 27.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Button(
                onClick = {
                    navController.navigate("Phone_Camera_Guide")
                },
                modifier = Modifier
                    .size(150.dp, 150.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFBD42)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "카메라",
                    fontSize = 27.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PhoneGuideScreenPreview() {
    val navController = rememberNavController()
    PhoneGuide0(navController = navController)
}

