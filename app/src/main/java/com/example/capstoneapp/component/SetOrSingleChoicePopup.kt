package com.example.capstoneapp.component
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
import androidx.compose.material3.Button
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
import com.example.capstoneapp.R
import com.example.capstoneapp.repository.MenuItem
import com.example.capstoneapp.repository.MenuItemsRepository.getMenuItemById

@Composable
fun SetOrSingleChoicePopup(
    showDialog: Boolean,
    currentItem: MenuItem?,
    onDismiss: () -> Unit,
    onAddToOrder: (MenuItem) -> Unit // Boolean 값은 세트 주문이면 true, 단품 주문이면 false
) {
   val setItem = getMenuItemById(1);

    //create currentItem set

    
    if (showDialog && currentItem != null) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),

            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("세트로 드시겠어요?", style = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween, // Arrange buttons with space in between
                        verticalAlignment = Alignment.CenterVertically,

                    ){
                        ItemCard(
                            item = currentItem
                        ) { onAddToOrder(currentItem) }
                        ItemCard(item = currentItem) { onAddToOrder(currentItem) }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = { onAddToOrder(currentItem) }) {
                            Text("주문하기")
                        }
                        Button(onClick = onDismiss) {
                            Text("취소")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetPopupPreview() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        SetOrSingleChoicePopup(
            showDialog = showDialog,
            MenuItem(1,"불고기 버거", R.drawable.baseline_adb_24,7000),
            onDismiss = { showDialog = false },
            onAddToOrder = { /* 주문 추가 이벤트 처리는 불필요하므로 비워둠 */ }
        )
    }
}
