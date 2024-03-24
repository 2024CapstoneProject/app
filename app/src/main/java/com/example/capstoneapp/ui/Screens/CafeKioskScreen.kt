package com.example.capstoneapp.ui.Screens

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.data.Repository.Problem
import com.example.capstoneapp.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.data.ViewModel.ProblemViewModel
import com.example.capstoneapp.ui.Components.CafeMenuBar
import com.example.capstoneapp.ui.Components.CafeMenuBarFormat
import com.example.capstoneapp.ui.Components.CafeMenuList
import com.example.capstoneapp.ui.Components.OrderList
import com.example.capstoneapp.ui.Components.totalOrder
import com.example.capstoneapp.ui.Frame.NotificationScreen
import com.example.capstoneapp.ui.Navigation.SetUpNavGraph


@Composable
fun CafeKioskScreen(
    navController: NavController,
    menuItemsViewModel: MenuItemsViewModel,
    problem: Problem
) {

    NotificationScreen(navController, problem) { CafeMenuScreen(navController, menuItemsViewModel) }
}

@Composable
fun CafeMenuScreen(navController: NavController, viewModel: MenuItemsViewModel) {
    val orderItems by viewModel.orderItems.observeAsState(initial = listOf())
    val totalCount by viewModel.totalOrderCount.observeAsState(0)

    var selectedMenu by remember { mutableStateOf("커피(HOT)") }
    val menuCategory = listOf("커피(HOT)", "커피(ICE)", "티(TEA)")

    Column() {
        Column(//메뉴 리스트 Column
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
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
                        if (menuItem.equals("HOME")) {
                            navController.navigate("KioskCafePractice0")
                        } else {
                            selectedMenu = menuItem
                        }
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
                    viewModel.addMenuItem(targetPair, index)
                } else viewModel.addMenuItem(Pair(selectedItem, 1), -1)
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
                            viewModel.addMenuItem(targetPair, targetPairIndex)
                        } else if (onItemStatus.second.equals("Minus")) {
                            viewModel.minusMenuItem(targetPair, targetPairIndex)
                        } else if (onItemStatus.second.equals("Delete")) {
                            viewModel.removeMenuItem(targetPair)
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
            totalOrder(totalCount) {
                if (it.first) {
                    viewModel.clearMenuItem()
                } else if (it.second) {
                    navController.navigate("KioskCafePractice5")
                }
            }
        }
    }
}

@Preview
@Composable
fun cafeKioskScreenPreview() {
    val navController = rememberNavController()
    SetUpNavGraph(navController = navController)
    val menuItemsViewModel: MenuItemsViewModel = viewModel()
    val problemViewModel: ProblemViewModel = viewModel()

    CafeKioskScreen(navController, menuItemsViewModel, problemViewModel.getProblemValue()!!)
}