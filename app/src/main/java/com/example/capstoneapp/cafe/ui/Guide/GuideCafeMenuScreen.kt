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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.data.Repository.MenuItemsRepository
import com.example.capstoneapp.cafe.ui.Components.CafeMenuBarFormat
import com.example.capstoneapp.cafe.ui.Components.OrderList
import com.example.capstoneapp.cafe.ui.Components.totalOrder
import com.example.capstoneapp.cafe.ui.Frame.GuidePopup
import com.example.capstoneapp.cafe.ui.Frame.VerticalAlignment
import com.example.capstoneapp.chatbot.utils.TtsPlaybackHandler
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModel
import com.example.capstoneapp.kakatalk.data.ViewModel.MenuItemsViewModelFactory
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.texttospeech.v1.TextToSpeechClient
import com.google.cloud.texttospeech.v1.TextToSpeechSettings
import java.io.InputStream

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

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val ttsClient = remember {
        val credentialsStream: InputStream =
            context.resources.openRawResource(R.raw.service_account_key) // 서비스 계정 키 파일
        val credentials = GoogleCredentials.fromStream(credentialsStream)
        val settings = TextToSpeechSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .build()
        TextToSpeechClient.create(settings)
    }

    val ttsPlaybackHandler = remember {
        TtsPlaybackHandler(context, ttsClient, coroutineScope)
    }

    // 자동으로 메뉴 선택하기
    LaunchedEffect(currentStep) {
        if (currentStep == 3) {
            // 자동으로 메뉴를 선택합니다.
            selectedMenu = when (problem.c_menu) {
                in listOf("HOT아메리카노", "HOT카페라떼") -> "커피(HOT)"
                in listOf("ICE아메리카노", "ICE바닐라라떼") -> "커피(ICE)"
                else -> "티(TEA)"
            }
        } else if (currentStep == 4) {
            // Step 4에서 문제의 메뉴를 OrderList에 추가
            val menuItem = MenuItemsRepository.getItemsForMenu(selectedMenu).find { it.name == problem.c_menu }
            if (menuItem != null && orderItems.none { it.first == menuItem }) {
                menuItemsViewModel.addMenuItem(Pair(menuItem, 1), -1)
            }
        }
    }

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
                    onItemClicked = {
//                        selectedItem ->
//                        if (selectedItem.name == problem.c_menu) {
//                            val targetPair = orderItems.firstOrNull { it.first.name == selectedItem.name }
//                            if (targetPair != null) {
//                                val index = orderItems.indexOf(targetPair)
//                                menuItemsViewModel.addMenuItem(targetPair, index)
//                            } else {
//                                menuItemsViewModel.addMenuItem(Pair(selectedItem, 1), -1)
//                            }
//                        } else {
//                            showRetryPopup = true
//                            popupMessage = "다시 골라주세요!"
//                        }
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
                            4, 5 -> true
                            else -> false
                        },
                        currentStep
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
                        //navController.navigate("KioskCafePractice5")
                    } else if (isRepeat && it.first) {
                        isRepeat = false
                    }
                }, showBorder = currentStep == 6, false) // 타이머를 멈춘 상태로 표시
            }
        }
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