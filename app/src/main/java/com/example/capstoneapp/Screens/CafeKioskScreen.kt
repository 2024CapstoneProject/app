package com.example.capstoneapp.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.capstoneapp.Frame.NotificationScreen
import com.example.capstoneapp.Repository.MenuItem
import com.example.capstoneapp.component.CafeMenuBar
import com.example.capstoneapp.component.CafeMenuBarFormat
import com.example.capstoneapp.component.CafeMenuList
import com.example.capstoneapp.component.OrderList
import com.example.capstoneapp.component.totalOrder


@Composable
fun CafeKioskScreen() {
    NotificationScreen { CafeMenuScreen() }

}

@Composable
fun CafeMenuScreen() {

    val orderItems = remember {
        mutableStateListOf<Pair<MenuItem, Int>>()
    }
    var selectedMenu by remember { mutableStateOf("커피(HOT)") }
    val menuCategory = listOf("커피(HOT)", "커피(ICE)", "티(TEA)")

    Column() {
        Column(//메뉴 리스트 Column
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()
        ) {
            //카페 네비게이션 바
            CafeMenuBarFormat {
                /*
                menuItems : 카테고리 리스트
                selectedMenu :
                onMenuItemClick : 카테고리 클릭
                 */
                CafeMenuBar(
                    menuItems = menuCategory,
                    selectedMenu = selectedMenu,
                    onMenuItemClick = { menuItem ->
                    selectedMenu = menuItem
                })
            }/*
            * 선택한 메뉴 종류에 따라 메뉴 리스트를 보여줌
            *
            * selectedMenu : 종류
            * selectedItem : 선택한 메뉴
            *  */
            CafeMenuList(selectedMenu = selectedMenu) { selectedItem ->
                val targetPair = orderItems.firstOrNull() { it.first.name == selectedItem.name }

                if (targetPair != null) {
                    val index = orderItems.indexOf(targetPair)
                    orderItems[index] = Pair(targetPair.first, targetPair.second + 1)
                } else orderItems.add(Pair(selectedItem, 1))
            }
        }

        Column( //빈공간
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(160.dp))
        }

        Row(//선택한 메뉴, 남은시간, 결제 버튼 공간
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .width(230.dp)
                    .fillMaxHeight()
            ) {
                Divider(
                    color = Color.Gray, // 선의 색상 지정
                    thickness = 2.dp, // 선의 두께 지정
                    modifier = Modifier
                        .padding()
                        .width(234.dp)
                )

                OrderList(orderItems = orderItems) { onItemStatus ->
                    var targetPairIndex = orderItems.indexOfFirst { onItemStatus.first == it.first }

                    if (targetPairIndex != -1) {
                        val targetPair = orderItems[targetPairIndex]

                        if (onItemStatus.second.equals("Add")) {
                            orderItems[targetPairIndex] =
                                Pair(targetPair.first, targetPair.second + 1)
                        } else if (onItemStatus.second.equals("Minus")) {
                            if (targetPair.second - 1 == 0) {
                                orderItems.remove(targetPair)
                            } else {
                                orderItems[targetPairIndex] =
                                    Pair(targetPair.first, targetPair.second - 1)
                            }

                        } else if (onItemStatus.second.equals("Delete")) {
                            orderItems.remove(targetPair)
                        }
                    }
                }
            }
            Divider(
                color = Color.Gray, // 선의 색상 지정
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
            )
            //결제하기, 선택 상품 개수, 시간 표시
            totalOrder(orderItems) {
                if (it) {
                    orderItems.clear()
                }
            }
        }
    }
}

@Preview
@Composable
fun cafeKioskScreenPreview() {
    CafeKioskScreen()
}