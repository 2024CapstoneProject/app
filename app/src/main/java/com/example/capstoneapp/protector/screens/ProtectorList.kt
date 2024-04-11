package com.example.capstoneapp.protector.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R

@Composable
fun ProtectorList(navController:NavController) {
    var isRegistered by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        UserInfoBox(navController, isRegistered = isRegistered)
        Spacer(modifier = Modifier.height(8.dp))
        RegisteredUsersList(navController)
    }
}

@Composable
fun UserInfoBox(navController: NavController, isRegistered: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp) // 높이 설정
                .clip(RectangleShape)
                .background(color = Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Icon",
                    modifier = Modifier.size(48.dp)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text("이소윤",
                        fontWeight = FontWeight.ExtraBold)
                    Text("본인",
                        fontWeight = FontWeight.ExtraBold)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { navController.navigate("ProtectorEdit") },
                    colors = ButtonDefaults.buttonColors(Color.LightGray),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("수정",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun RegisteredUsersList(navController: NavController) {
    // 가상의 등록된 사용자 목록
    val registeredUsers = listOf(
        UserInfo("이영학", "아들", false),
        UserInfo("조혜은", "딸", true),
        UserInfo("김희연", "농담곰", false)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        registeredUsers.forEach { user ->
            Divider(color = Color.Black, thickness = 1.dp)
            RegisteredUserButton(navController, user)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun RegisteredUserButton(navController: NavController, userInfo: UserInfo) {
    Button(
        onClick = { navController.navigate("ProtectorMap") },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Icon",
                modifier = Modifier.size(48.dp)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(userInfo.name, color = Color.Black, fontWeight = FontWeight.ExtraBold,)
                Text(userInfo.relation, color = Color.Black, fontWeight = FontWeight.ExtraBold,)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if (userInfo.isRegistered) "등록 완료" else "등록 대기",
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp
            )
        }
    }
}

data class UserInfo(val name: String, val relation: String, val isRegistered: Boolean)

@Preview
@Composable
fun ProtectListPreview() {
    val navController = rememberNavController()
    ProtectorList(navController)
}