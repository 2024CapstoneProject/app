package com.example.capstoneapp.cafe.ui.Guide

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
import com.example.capstoneapp.cafe.ui.Components.CafeMenuBarFormat
import com.example.capstoneapp.cafe.ui.Components.OrderList
import com.example.capstoneapp.cafe.ui.Components.totalOrder
import com.example.capstoneapp.cafe.ui.Frame.GuidePopup
import com.example.capstoneapp.cafe.ui.Frame.VerticalAlignment
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository

@Composable
fun GuideCafeMenuScreen(
    navController: NavController,
    menuItemsViewModel: MenuItemsViewModel,
    showBorder: Boolean,
    currentStep: Int,
    problem: Problem
) {
    val orderItems by menuItemsViewModel.orderItems.observeAsState(initial = listOf())
    val totalCount by menuItemsViewModel.totalOrderCount.observeAsState(0)
    var isRepeat by remember { mutableStateOf(false) }
    var selectedMenu by remember { mutableStateOf("커피(HOT)") }
    val menuCategory = listOf("커피(HOT)", "커피(ICE)", "티(TEA)")

    var showRetryPopup by remember { mutableStateOf(false) }
    var popupMessage by remember { mutableStateOf("") }

    Surface(
        color = Color(0xFFCACACA),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(16.dp))
    ) {
        Column {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                CafeMenuBarFormat {
                    GuideCafeMenuBar(
                        menuItems = menuCategory,
                        selectedMenu = selectedMenu,
                        onMenuItemClick = { menuItem ->
                            if (menuItem == "HOME") {
                                // 홈 버튼 클릭 시 팝업 상태 유지와 네비게이션 처리
                                navController.navigate("Guide1_touchscreen?showPopup=false")
                                menuItemsViewModel.clearMenuItem()
                            } else {
                                selectedMenu = menuItem
                            }
                        },
                        showBorder = when (currentStep) {
                            1 -> true
                            2 -> false // Step 2일 때는 보더를 적용하지 않음
                            3 -> true
                            else -> false
                        },
                        problem = problem,
                        currentStep = currentStep
                    )
                }

                GuideCafeMenuList(
                    selectedMenu = selectedMenu,
                    onItemClicked = { selectedItem ->
                        if (selectedItem.name == problem.c_menu) {
                            val targetPair = orderItems.firstOrNull { it.first.name == selectedItem.name }
                            if (targetPair != null) {
                                val index = orderItems.indexOf(targetPair)
                                menuItemsViewModel.addMenuItem(targetPair, index)
                            } else {
                                menuItemsViewModel.addMenuItem(Pair(selectedItem, 1), -1)
                            }
                        } else {
                            showRetryPopup = true
                            popupMessage = "다시 골라주세요!"
                        }
                    },
                    showBorder = when (currentStep) {
                        3 -> true
                        else -> false
                    },
                    problem = problem,
                    currentStep = currentStep
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(140.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .width(230.dp)
                        .fillMaxHeight()
                ) {
                    Divider(
                        color = Color.Gray,
                        thickness = 2.dp,
                        modifier = Modifier
                            .padding()
                            .width(234.dp)
                    )

                    OrderList(
                        orderItems = orderItems,
                        onItemStatus = { onItemStatus ->
                            val targetPairIndex = orderItems.indexOfFirst { onItemStatus.first == it.first }
                            if (targetPairIndex != -1) {
                                val targetPair = orderItems[targetPairIndex]
                                when (onItemStatus.second) {
                                    "Add" -> menuItemsViewModel.addMenuItem(targetPair, targetPairIndex)
                                    "Minus" -> menuItemsViewModel.minusMenuItem(targetPair, targetPairIndex)
                                    "Delete" -> menuItemsViewModel.removeMenuItem(targetPair)
                                }
                            }
                        },
                        showBorder = when (currentStep) {
                            4 -> true
                            else -> false
                        },
                    )
                }
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                )
                // totalOrder 호출 시 remainingTime을 고정하여 타이머 기능을 비활성화
                totalOrder(totalCount, isRepeat, {
                    if (!isRepeat && it.first) {
                        menuItemsViewModel.clearMenuItem()
                    } else if (it.second) {
                        navController.navigate("KioskCafePractice5")
                    } else if (isRepeat && it.first) {
                        isRepeat = false
                    }
                }, showBorder = currentStep == 5, false) // 타이머를 멈춘 상태로 표시
            }
        }
    }

    if (showRetryPopup) {
        GuidePopup(
            isPopupVisible = showRetryPopup,
            onDismiss = { showRetryPopup = false },
            title = "다시 선택",
            message = popupMessage,
            highlights = listOf(problem.c_menu),
            verticalAlignment = VerticalAlignment.Center
        )
    }
}

@Preview
@Composable
fun GuideCafeMenuScreenPreview() {
    val navController = rememberNavController()
    val menuItemsViewModelFactory = MenuItemsViewModelFactory()
    val menuItemsViewModel: MenuItemsViewModel = viewModel(factory = menuItemsViewModelFactory)
    val problem = ProblemRepository.createProblem()

    GuideCafeMenuScreen(navController, menuItemsViewModel, true, 1, problem)
}