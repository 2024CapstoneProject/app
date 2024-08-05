package com.example.capstoneapp.cafe.ui.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.ui.Frame.GuidePopup
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.fastfood.ui.theme.fontFamily

@Composable
fun Guide1(navController: NavController, showBorder: Boolean) {
    // 팝업을 표시할지 여부를 관리하는 상태
    var showPopup by remember { mutableStateOf(true) }

    BackHandler(enabled = showPopup) {
        showPopup = false
        navController.navigate("CafeHomeScreen") {
            popUpTo("CafeHomeScreen") { inclusive = true }
        }
    }

    // 다이얼로그가 열려 있을 때만 GuideDialog를 표시
    if (showPopup) {
        GuidePopup(
            isPopupVisible = showPopup,
            onDismiss = { showPopup = false },
            title = "광고",
            message = "광고 화면입니다. 화면을 터치하여 주문을 시작해주세요!",
            confirmButtonText = "확인"
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth() // Fill the width of the parent
            .padding(horizontal = 1.dp) // Padding from the left and right
            .fillMaxHeight() // Fill 50% of the height of the parent
            .background(
                color = Color.White, // Change this color to your desired background color
                shape = RoundedCornerShape(16.dp) // Rounded corners
            )
            .border(2.dp, Color.Gray, RoundedCornerShape(25.dp)),// Border
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                    if (!showPopup) {
                        navController.navigate("CafeKioskScreen")
                    }
                }
                .then(
                    if (showBorder) Modifier.border(
                        BorderWidth,
                        BorderColor,
                        BorderShape
                    ) else Modifier
                ),
            horizontalAlignment = Alignment.CenterHorizontally // 자식 요소들을 가운데 정렬

        ) {
            Spacer(modifier = Modifier.height(36.dp))

            Icon(
                painter = painterResource(id = R.drawable.touch_icon),
                contentDescription = null
            )
            Text(
                text = "화면을 터치해 주세요",
                style =
                TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Guide1Preview() {
    val navController = rememberNavController()
    Guide1(navController = navController, showBorder = false)
}
