@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.ui.Frame.ButtonFormat
import com.example.capstoneapp.ui.theme.Yellow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScreen(openBottomSheet: Boolean, onOpenBottomSheetChange: (Boolean) -> Unit) {

    //var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    // App content (뜨게 하는 버튼)
//    Column(
//        horizontalAlignment = Alignment.Start,
//        verticalArrangement = Arrangement.spacedBy(4.dp)
//    ) {
//        Button(
//            onClick = { openBottomSheet = !openBottomSheet },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text(text = "Show Bottom Sheet")
//        }
//    }

    // Sheet content
    if (openBottomSheet) {
        val windowInsets =
            if (edgeToEdgeEnabled) WindowInsets(0) else BottomSheetDefaults.windowInsets

        ModalBottomSheet(
            onDismissRequest = { onOpenBottomSheetChange(false) },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.27f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

//            var text by remember { mutableStateOf("") }
//                OutlinedTextField(
//                    value = text,
//                    onValueChange = { text = it },
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    label = { Text("Text field") }
//                )
//                Button(
//                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
//                    // you must additionally handle intended state cleanup, if any.
//                    onClick = {
//                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
//                            if (!bottomSheetState.isVisible) {
//                                openBottomSheet = false
//                            }
//                        }
//                    }
//                ) {
//                    Text("닫기") // 닫기 버튼
//                }
                //ProblemBox(viewModel)
                ProblemBox()
                ButtonFormat(
                    modifier = Modifier.height(64.dp), onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                onOpenBottomSheetChange(false)
                            }
                        }
                    }, buttonText = "닫기", backgroundColor = Yellow, contentColor = Color.Black
                )

            }
        }
    }
}

@Composable
fun ProblemBox() {/*ProblemBox 매개변수 viewModel: SharedViewModel 추가
    *
    val problem = viewModel.getProblem()
    */
    val menu = "아메리카노"
    val place = "매장에서 먹기"
    val point = "O"
    val pay = "카드 결제"
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "메뉴 : $menu",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = "장소 : $place",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = "포인트 적립 여부 : $point",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = "결제 방식 : $pay",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif
        )
    }


}

@Preview
@Composable
fun BottomSheetPreview() {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    BottomSheetScreen(openBottomSheet = openBottomSheet,
        onOpenBottomSheetChange = { openBottomSheet = it })

//    val viewModel: SharedViewModel = viewModel()
//    BottomSheetScreen(openBottomSheet = openBottomSheet,
//        onOpenBottomSheetChange = {openBottomSheet = it},viewModel
//    )
}