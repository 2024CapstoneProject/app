package com.example.capstoneapp.ui.screens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.data.repository.MenuItem
import com.example.capstoneapp.data.repository.MenuItemsRepository.getMenuItemById
import com.example.capstoneapp.ui.components.ItemCard
import com.example.capstoneapp.ui.theme.fontFamily

@Composable
fun SetOrSingleChoicePopup(
    showDialog: Boolean,
    currentItem: MenuItem?,
    onDismiss: () -> Unit,
    onAddToOrder: (MenuItem) -> Unit, // Boolean 값은 세트 주문이면 true, 단품 주문이면 false // NavController 추가
) {
    val setItem = getMenuItemById(currentItem!!.id + 1)

    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // Card의 전체 공간을 채웁니다.
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally, // 컬럼 내부의 항목들을 수평 중앙 정렬 r

                ) {
                    Text("세트로 드시겠어요?",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween, // Arrange buttons with space in between
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        ItemCard(
                            item = currentItem
                        ) { onAddToOrder(currentItem) }

                        if (setItem != null) {
                            ItemCard(item = setItem) { onAddToOrder(setItem) }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetPopupPreview() {
    var showDialog by remember { mutableStateOf(true) }
    val navController = rememberNavController()

    if (showDialog) {
        SetOrSingleChoicePopup(
            showDialog = showDialog,
            MenuItem(1,"불고기 버거", R.drawable.baseline_adb_24,7000),
            onDismiss = { showDialog = false },
            onAddToOrder = { /* 주문 추가 이벤트 처리는 불필요하므로 비워둠 */ }

        )
    }
}
