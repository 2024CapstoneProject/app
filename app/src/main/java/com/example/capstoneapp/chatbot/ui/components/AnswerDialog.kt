import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.ui.theme.LightYellow
import com.example.capstoneapp.cafe.ui.theme.Yellow
import com.example.capstoneapp.fastfood.ui.theme.fontFamily
@Composable
fun ButtonFormat(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String,
    backgroundColor: Color,
    contentColor: Color
) {
    Button(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, // 배경색
            contentColor = contentColor // 텍스트 색상
        ),
        onClick = onClick
    ) {
        Text(
            text = buttonText,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            fontFamily = fontFamily,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 8.dp),
        )
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun AnswerDialog(
    responseText: String,
 //   onDismiss: () -> Unit,
    onReplay: (String) -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    val responseLines = responseText.split("\n").filter { it.isNotBlank() }
    val chunkedResponse = responseLines.chunked(1)
    val totalPages = chunkedResponse.size

  //  Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()  // 가로 전체 채우기
                .heightIn(min = 260.dp)  // 초기 높이를 260dp로 유지하되 필요시 확장
                .padding(horizontal = 16.dp, vertical = 8.dp)  // 상하 좌우 패딩
                .background(
                    color = LightYellow,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),  // 모든 방향으로 여유 공간을 주기 위한 패딩
                verticalArrangement = Arrangement.spacedBy(16.dp),  // 컴포넌트 사이 간격
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 상단 아이콘 및 네비게이션
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 챗봇 아이콘
                   Image(
                        painter = painterResource(id = R.drawable.chatboticon), // 이미지 리소스를 사용
                        contentDescription = null,
                        modifier = Modifier.size(72.dp)
                    )

                    // 네비게이션 화살표
                    Row {
                        IconButton(
                            onClick = { if (currentIndex > 0) currentIndex-- },
                            enabled = currentIndex > 0
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Previous",
                                tint = if (currentIndex > 0) Color.Black else Color.Gray,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        IconButton(
                            onClick = { if (currentIndex < chunkedResponse.size - 1) currentIndex++ },
                            enabled = currentIndex < chunkedResponse.size - 1
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Next",
                                tint = if (currentIndex < chunkedResponse.size - 1) Color.Black else Color.Gray,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Text(
                            text = "${currentIndex + 1} / $totalPages",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,

                        )

                    }
                }

                // 챗봇 텍스트 및 응답
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.weight(1f),  // 여유 공간을 차지하도록 설정
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "챗봇",  // 챗봇 라벨
                        color = Color.DarkGray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    chunkedResponse.getOrNull(currentIndex)?.forEach { line ->
                        Text(
                            text = line,
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // 다시 듣기 버튼
                ButtonFormat(
                    onClick = { onReplay(chunkedResponse.getOrNull(currentIndex)?.joinToString("\n") ?: "") },
                    buttonText = "🎧 다시 듣기",
                    backgroundColor = Color(0xFFFAFAFA),  // 버튼 배경색
                    contentColor = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()  // 전체 너비 사용
                        .padding(top = 16.dp)  // 버튼과 텍스트 사이의 간격
                )
            }
        }
    }




@Preview
@Composable
fun PreviewAnswerDialog() {
    AnswerDialog(
        responseText = "첫번째, 식사장소가 매장인지 포장인지를 선택하세요\n두번째, 화면 왼쪽열에서 버거를 선택하세요. 화면 오른쪽에 있는 흰색막대기를 위아래로 움직이면서 불고기 버거 이미지를 찾으면 됩니다.\n세번째, 세트를 선택하세요. 기본크기의 사이드와 음료를 먹고 싶으면 세트를, 더 많이 먹고 싶으면 라지를 선택해주세요.",
    //    onDismiss = { },
        onReplay = { }
    )
}
