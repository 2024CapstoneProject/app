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
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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

@Composable
fun AnswerDialog(
    responseText: String,
    onDismiss: () -> Unit,
    onReplay: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    val responseLines = responseText.split("\n").filter { it.isNotBlank() }
    val chunkedResponse = responseLines.chunked(2)
    val totalPages = chunkedResponse.size

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(720.dp, 520.dp)
                .padding(16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { if (currentIndex > 0) currentIndex-- },
                        enabled = currentIndex > 0
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
                    }
                    Text(
                        text = "챗봇 대답",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    IconButton(
                        onClick = { if (currentIndex < chunkedResponse.size - 1) currentIndex++ },
                        enabled = currentIndex < chunkedResponse.size - 1
                    ) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${currentIndex + 1} / $totalPages",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        chunkedResponse.getOrNull(currentIndex)?.forEach { line ->
                            Box(
                                modifier = Modifier
                                    .background(Yellow, shape = RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = line,
                                    color = Color.Black,
                                    fontSize = 21.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ButtonFormat(
                        onClick = onDismiss,
                        buttonText = "닫기",
                        backgroundColor = Color(0xFF696969),
                        contentColor = Color.White,
                        modifier = Modifier.size(120.dp, 50.dp)
                    )
                    ButtonFormat(
                        onClick = onReplay,
                        buttonText = "다시 듣기",
                        backgroundColor = Yellow,
                        contentColor = Color.Black,
                        modifier = Modifier.size(120.dp, 50.dp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewAnswerDialog() {
    AnswerDialog(
        responseText = "첫번째, 식사장소가 매장인지 포장인지를 선택하세요\n두번째, 화면 왼쪽열에서 버거를 선택하세요. 화면 오른쪽에 있는 흰색막대기를 위아래로 움직이면서 불고기 버거 이미지를 찾으면 됩니다.\n세번째, 세트를 선택하세요. 기본크기의 사이드와 음료를 먹고 싶으면 세트를, 더 많이 먹고 싶으면 라지를 선택해주세요.",
        onDismiss = { },
        onReplay = { }
    )
}
