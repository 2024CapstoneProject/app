package com.example.capstoneapp.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun touchScreen() {

    Column(
        modifier = Modifier.padding(16.dp), // 전체 Column에 패딩 적용
        horizontalAlignment = Alignment.CenterHorizontally // 자식 요소들을 가운데 정렬
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Icon(
            painter = painterResource(id = R.drawable.baseline_adb_24),
            contentDescription = null
            // Icon에 별도의 패딩을 적용하지 않음
        )
        Text(
            text = "화면을 터치해 주세요",
            style =
            TextStyle(
                fontSize = 24.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif),
            modifier = Modifier.padding(top = 16.dp) // Icon과 Text 사이의 상단 패딩 적용
        )
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    touchScreen()
}
