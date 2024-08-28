package com.example.capstoneapp.cafe.ui.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.cafe.ui.Components.CafeMenuBar
import com.example.capstoneapp.cafe.ui.Components.CafeMenuBarFormat
import com.example.capstoneapp.cafe.ui.Components.CafeMenuList
import com.example.capstoneapp.cafe.ui.Components.OrderList
import com.example.capstoneapp.cafe.ui.Components.totalOrder
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.kakatalk.ui.Components.RepeatDialog
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory


@Composable
fun CafeKioskScreen(
    navController: NavController,
    menuItemsViewModel: MenuItemsViewModel,
    problem: Problem,
    showBorder: Boolean
) {
    CafeMenuScreen(navController, menuItemsViewModel, showBorder, problem)
}

@Composable
fun CafeMenuScreen(
    navController: NavController,
    viewModel: MenuItemsViewModel,
    showBorder: Boolean,
    problem: Problem
) {
    val orderItems by viewModel.orderItems.observeAsState(initial = listOf())
    val totalCount by viewModel.totalOrderCount.observeAsState(0)
    var isRepeat by remember { mutableStateOf(false) }
    var selectedMenu by remember { mutableStateOf("커피(HOT)") }
    val menuCategory = listOf("커피(HOT)", "커피(ICE)", "티(TEA)")

    var showRetryDialog by remember { mutableStateOf(false) }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .clip(shape = RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.fillMaxHeight(1f)) {
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
                                navController.navigate("touchToStartCafe")
                            } else {
                                selectedMenu = menuItem
                            }
                        }, showBorder, problem
                    )
                }/*
            * 선택한 메뉴 종류에 따라 메뉴 리스트를 보여줌
            *
            * selectedMenu : 종류
            * selectedItem : 선택한 메뉴
            *  */
                CafeMenuList(selectedMenu = selectedMenu, onItemClicked = { selectedItem ->
                    if (selectedItem.name == problem.c_menu) {
                        val targetPair =
                            orderItems.firstOrNull() { it.first.name == selectedItem.name }
                        if (targetPair != null) {
                            val index = orderItems.indexOf(targetPair)
                            viewModel.addMenuItem(targetPair, index)
                        } else viewModel.addMenuItem(Pair(selectedItem, 1), -1)
                    }
                }, closePopup = { isRepeat = !isRepeat }, showBorder, problem)
            }

            Column( //빈공간
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(100.dp))
            }

            Row(//선택한 메뉴, 남은시간, 결제 버튼 공간
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .width(230.dp)
                        .fillMaxHeight()
                ) {
                    OrderList(orderItems = orderItems,
                        onItemStatus = { onItemStatus ->
                            val targetPairIndex = orderItems.indexOfFirst { onItemStatus.first == it.first }
                            if (targetPairIndex != -1) {
                                val targetPair = orderItems[targetPairIndex]
                                when (onItemStatus.second) {
                                    "Add" -> viewModel.addMenuItem(targetPair, targetPairIndex)
                                    "Minus" -> viewModel.minusMenuItem(targetPair, targetPairIndex)
                                    "Delete" -> viewModel.removeMenuItem(targetPair)
                                }
                            }
                        },
                        false // Ensure `showBorder` is passed correctly
                    )
                }
                Column(
                    modifier = Modifier
                        .width(230.dp)
                        .fillMaxHeight()
                ){
                    totalOrder(totalCount, isRepeat, {
                        if (!isRepeat && it.first) {
                            viewModel.clearMenuItem()
                        } else if (it.second) {
                            navController.navigate("KioskCafePractice5")
                        } else if (isRepeat && it.first) {
                            isRepeat = false
                        }
                    }, showBorder)
                }

                //결제하기, 선택 상품 개수, 시간 표시

            }
        }
    }

    if (showRetryDialog) {
        RepeatDialog(onDismiss = { showRetryDialog = false })
    }
}

@Preview(widthDp=412, heightDp = 846)
@Composable
fun cafeKioskScreenPreview() {
    val navController = rememberNavController()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val menuItemsViewModelFactory = MenuItemsViewModelFactory()
    val menuItemsViewModel: MenuItemsViewModel = viewModel(factory = menuItemsViewModelFactory)

    CafeKioskScreen(navController, menuItemsViewModel, problemViewModel.getProblemValue()!!, true)
}