package com.example.capstoneapp.fastfood.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
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
import com.example.capstoneapp.fastfood.data.model.OrderViewModel
import com.example.capstoneapp.nav.repository.MenuItem
import com.example.capstoneapp.fastfood.ui.components.CustomizedNavigationBar
import com.example.capstoneapp.fastfood.ui.frame.DividerFormat
import com.example.capstoneapp.fastfood.ui.components.ItemList
import com.example.capstoneapp.fastfood.ui.frame.KioskButtonFormat
import com.example.capstoneapp.fastfood.ui.components.OrderList
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.kakatalk.ui.Components.RepeatDialog
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory

@Composable
fun ItemMenu(
    navController: NavController,
    viewModel: OrderViewModel,
    showBorder: Boolean,
    problem: Problem
) {
    // 주문한 목록
    val orderItems = remember { mutableStateListOf<MenuItem>() }
    var showDialog by remember { mutableStateOf(false) }
    var currentItemForDialog by remember { mutableStateOf<MenuItem?>(null) }
    var showDessertScreen by remember { mutableStateOf(false) }
    var selectedDessert by remember { mutableStateOf<MenuItem?>(null) }
    var selectedDrink by remember { mutableStateOf<MenuItem?>(null) }

    var repeatAnswer by remember { mutableStateOf(false) }

    val onButtonClick = {
        if (showDessertScreen) {
            selectedDessert?.let {
                orderItems.removeAll { item -> item.type == "디저트" }
                orderItems.add(it)
            }
            selectedDrink?.let {
                orderItems.removeAll { item -> item.type == "드링크" }
                orderItems.add(it)
            }
            showDessertScreen = false
        } else {
            navController.navigate("finalOrder")
        }
    }

    // 네비게이션 카테고리 선택
    var selectedMenu by remember { mutableStateOf("햄버거") } // 초기값 설정
    val myMenuItems = listOf("추천메뉴", "햄버거", "디저트/치킨", "음료/커피")
    val buttonText = if (showDessertScreen) "선택완료" else "결제하기"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        // 메뉴 목록 표
        if (!showDessertScreen) {
            Box(
                modifier = Modifier
                    .weight(1.8f) // 화면의 절반을 차지
                    .fillMaxWidth()
            ) {
                Column {
                    CustomizedNavigationBar(
                        menuItems = myMenuItems,
                        selectedMenuItem = selectedMenu,
                        onMenuItemClick = { menuItem ->
                            if (menuItem == "햄버거") { // "햄버거" 메뉴만 선택 가능
                                selectedMenu = menuItem
                            }
                        }
                    )
                    ItemList(
                        selectedMenu = selectedMenu,
                        selectedItem = currentItemForDialog,
                        showBorder = showBorder,
                        problem = problem,
                        onItemClicked = { selectedItem ->
                            if (selectedMenu == "햄버거") { // "햄버거" 메뉴가 선택되었을 때만 클릭 이벤트 활성화
                                if(!problem.menu.split(",").contains(selectedItem.name)){
                                    repeatAnswer=true
                                }else{
                                    currentItemForDialog = selectedItem
                                    showDialog = true // 아이템 클릭 시 팝업 표시
                                }
                            }
                        }
                    )
                }
            }
            if(repeatAnswer){
                RepeatDialog(onDismiss = {
                    repeatAnswer = false })
            }
            if (showDialog) {
                SetOrSingleChoicePopup(
                    showDialog = showDialog,
                    currentItem = currentItemForDialog,
                    onDismiss = { showDialog = false },
                    onAddToOrder = { item ->
                        if (problem.menu.split(",").contains(item.name)||item.name=="불고기 버거 세트") {
                            orderItems.add(item)
                            viewModel.addMenuItem(item, 1)
                            showDialog = false
                            showDessertScreen = item.id % 2 == 0
                        } else {
                            repeatAnswer = true
                        }
                    }, showBorder,problem
                )
            }
        } else {
            SelectSetDessertScreen(
                selectedDessert = selectedDessert,
                selectedDrink = selectedDrink,
                onDessertSelected = { selectedItem ->
                    if (problem.menu.split(",").contains(selectedItem.name)) {
                        selectedDessert = selectedItem
                        viewModel.addMenuItem(selectedItem, 1)
                    }
                },
                onDrinkSelected = { selectedItem ->
                    if (problem.menu.split(",").contains(selectedItem.name)) {
                        selectedDrink = selectedItem
                        viewModel.addMenuItem(selectedItem, 1)
                    }
                },showBorder,problem
            )
        }

        Box(
            modifier = Modifier
                .weight(1f) // 화면의 절반을 차지
                .fillMaxSize()
        ) {
            DividerFormat()
            OrderList(orderItems = orderItems)
        }

        // 결제 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Apply horizontal padding
            horizontalArrangement = Arrangement.SpaceBetween // Arrange buttons with space in between
        ) {
            KioskButtonFormat(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (showBorder) Modifier.border(BorderWidth, BorderColor)
                        else Modifier
                    ),
                onClick = onButtonClick,
                buttonText = buttonText,
                backgroundColor = Color.Red,
                contentColor = Color.Black,
                enabled = orderItems.isNotEmpty() // orderItems가 비어 있으면 버튼 비활성화
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
}



@Preview(showBackground = true)
@Composable
fun ItemMenuPreview() {
    val navController = rememberNavController()
    val viewModel = OrderViewModel()
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    val problem by problemViewModel.problem.observeAsState()
    problem?.let {
        ItemMenu(
        navController = navController,
        viewModel = viewModel,
        showBorder = true, // 또는 미리보기에 맞는 값으로 설정합니다.
        problem= problem!!
    )
    }
}
