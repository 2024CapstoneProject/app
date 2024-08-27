import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth

@Composable
fun SubScreen(navController: NavController,showBorder: Boolean) {
    var inputText by remember { mutableStateOf("") }
    var showLayouts by remember { mutableStateOf(false) }

    val yfont = FontFamily(Font(R.font.yfont))

    Column(modifier = Modifier.fillMaxSize()) {
        // 뒤로가기 버튼
        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("←", fontFamily = yfont, fontSize = 28.sp)
        }

        // 현위치
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("ㅇ", color = Color.Black, fontFamily = yfont)
            Spacer(modifier = Modifier.width(8.dp))
            Text("현위치 : 경기대학교", fontSize = 24.sp, fontFamily = yfont, color = Color.Black)
        }

        // 목적지 입력
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("ㅇ", color = Color.Red, fontFamily = yfont)
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    showLayouts = it == "수원역"
                },
                placeholder = { Text("목적지 입력", fontFamily = yfont) },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
            )
            TextButton(onClick = { /* 경유 기능 추가 */ }) {
                Text("+ 경유", color = Color(0xFF00CED1), fontFamily = yfont)
            }
        }

        // 검색 결과 레이아웃
        if (showLayouts) {
            LocationItem("수원역", "경기 수원시 팔달구 덕영대로 924",showBorder) {
                navController.navigate("taxi_set_goal")
            }
            LocationItem("수원역 1호선", "경기 수원시 팔달구 덕영대로 924",false)
            LocationItem("수원역환승센터", "경기 수원시 권선구 세화로 136",false)
        }
    }
}

@Composable
fun LocationItem(title: String, address: String, showBorder: Boolean, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
            .clickable(onClick = onClick)
            .then(
                if (showBorder) Modifier.border(
                    BorderWidth,
                    BorderColor,
                    BorderShape
                ) else Modifier
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.marker),
            contentDescription = "Location marker",
            modifier = Modifier.size(24.dp).padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(title, fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.yfont)), color = Color.Black)
            Text(address, fontFamily = FontFamily(Font(R.font.yfont)),modifier = Modifier.padding(bottom=4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubActivityScreenPreview() {
    val navController = rememberNavController()
   SubScreen(navController,true)
}