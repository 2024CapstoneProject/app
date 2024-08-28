package com.example.capstoneapp.fastfood.ui.guide

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.ui.Frame.GuidePopup
import com.example.capstoneapp.cafe.ui.Frame.VerticalAlignment
import com.example.capstoneapp.chatbot.utils.TtsPlaybackHandler
import com.example.capstoneapp.fastfood.data.model.OrderViewModel
import com.example.capstoneapp.fastfood.ui.components.CustomizedNavigationBar
import com.example.capstoneapp.fastfood.ui.components.ItemList
import com.example.capstoneapp.fastfood.ui.components.OrderList
import com.example.capstoneapp.fastfood.ui.frame.DividerFormat
import com.example.capstoneapp.fastfood.ui.frame.KioskButtonFormat
import com.example.capstoneapp.fastfood.ui.screens.SelectSetDessertScreen
import com.example.capstoneapp.fastfood.ui.screens.SetOrSingleChoicePopup
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.kakatalk.ui.Components.RepeatDialog
import com.example.capstoneapp.nav.repository.MenuItem
import com.example.capstoneapp.nav.repository.Problem
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.texttospeech.v1.TextToSpeechClient
import com.google.cloud.texttospeech.v1.TextToSpeechSettings
import kotlinx.coroutines.delay
import java.io.InputStream

@Composable
fun Fastfood_Guide3(
    navController: NavController,
    viewModel: OrderViewModel = viewModel(),
    showBorder: Boolean,
    problem: Problem
){
    //가이드팝업
    var showPopup by remember { mutableStateOf(true) }
    var menu = problem.menu.split(",")
    var currentStep by remember { mutableStateOf(1) } // 현재 단계 관리 변수
    val messageStep1 = "주문하는 법을 안내해드립니다! 예시로 ${problem.menu}를 주문하는 법입니다."
    val messageStep2 = "먼저 화면에서 ${menu.firstOrNull()}를 찾아주세요. ${menu.firstOrNull()}는 햄버거에 있습니다."
    val messageStep3 = "${menu.firstOrNull()}를 클릭후 세트를 눌러주세요."
    //가이드팝업2
    var showPopup2 by remember { mutableStateOf(false) }
    var currentStep2 by remember { mutableStateOf(1) }

    // 주문한 목록
    var showDialog by remember { mutableStateOf(false) }
    var currentItemForDialog by remember { mutableStateOf<MenuItem?>(null) }
    var showDessertScreen by remember { mutableStateOf(false) }
    var selectedDessert by remember { mutableStateOf<MenuItem?>(null) }
    var selectedDrink by remember { mutableStateOf<MenuItem?>(null) }
    val orderItems by viewModel.orderItems.observeAsState(emptyList())

    var repeatAnswer by remember { mutableStateOf(false) }

    //TTS를 위해 추가해야 하는 부분-----------------------------------
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // TTS 클라이언트 및 핸들러 초기화
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

    // 컴포저블이 소멸될 때 TTS 클라이언트를 정리하는 코드 추가
    DisposableEffect(Unit) {
        onDispose {
            ttsPlaybackHandler.shutdown()
        }
    }

    val onButtonClick = {
        if (showDessertScreen) {
            selectedDessert?.let {
                viewModel.addMenuItem(it, 1)
            }
            selectedDrink?.let {
                viewModel.addMenuItem(it, 1)
            }
            showDessertScreen = false
        } else {
            navController.navigate("finalOrder")
        }
    }

    // 뒤로가기 처리
    BackHandler {
        viewModel.clearOrderItems()
        navController.navigate("HamburgerHomeScreen") {
            popUpTo("HamburgerHomeScreen") { inclusive = true }
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
                            if (menuItem == "추천메뉴" || menuItem == "햄버거" || menuItem == "디저트/치킨" || menuItem == "음료/커피") { // "햄버거" 메뉴만 선택 가능
                                selectedMenu = menuItem
                                if (menuItem == "추천메뉴") {
                                    navController.navigate("recommend")
                                }
                                if (menuItem == "디저트/치킨") {
                                    navController.navigate("dessertChicken")
                                }
                                if (menuItem == "음료/커피") {
                                    navController.navigate("drinkCoffee")
                                }
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
                        if (problem.menu.split(",").contains(item.name)||item.name.split(" ").contains("세트")) {
                            viewModel.addMenuItem(item, 1)
                            showDialog = false
                            showDessertScreen = item.id % 2 == 0
                        } else {
                            repeatAnswer = true
                        }
                    }, showBorder,problem
                )
            }
        }
        if (showDessertScreen) {
            showPopup2 = true
            SelectSetDessertScreen(
                selectedDessert = selectedDessert,
                selectedDrink = selectedDrink,
                onDessertSelected = { selectedItem ->
                    if (selectedDessert == null && problem.menu.split(",").contains(selectedItem.name)) {
                        selectedDessert = selectedItem
                    }
                },
                onDrinkSelected = { selectedItem ->
                    if (selectedDrink == null && problem.menu.split(",").contains(selectedItem.name)) {
                        selectedDrink = selectedItem
                    }
                }, showBorder, problem
            )
        }

        Box(
            modifier = Modifier
                .weight(1f) // 화면의 절반을 차지
                .fillMaxSize()
        ) {
            DividerFormat()
            OrderList(orderItems = orderItems) // OrderList에 OrderItem 목록 전달
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
                enabled = if (showDessertScreen) selectedDessert != null && selectedDrink != null else orderItems.isNotEmpty()
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
    if (showPopup) {
        GuidePopup(
            isPopupVisible = showPopup,
            onDismiss = {
                currentStep += 1
                if (currentStep > 3) { // 모든 단계가 끝난 후
                    showPopup = false
                }
            },
            title = "주문안내",
            message = when (currentStep) {
                1 -> messageStep1
                2 -> messageStep2
                3 -> messageStep3
                else -> ""
            },
            highlights = when (currentStep) {
                1 -> listOf(problem.menu,)
                2 -> listOf(*problem.menu.split(",").toTypedArray())
                3 -> listOf(*problem.menu.split(",").toTypedArray(),"세트")
                else -> listOf("")
            },
            verticalAlignment = VerticalAlignment.Bottom,
            ttsPlaybackHandler = ttsPlaybackHandler,
        )
    }
    if (showPopup2) {
        GuidePopup(
            isPopupVisible = showPopup2,
            onDismiss = {
                currentStep2 += 1
                if (currentStep2 > 2) { // 모든 단계가 끝난 후
                    showPopup2 = false
                }
            },
            title = "디저트 선택 안내",
            message = when (currentStep2) {
                1 -> messageStep1
                2 -> messageStep2
                3 -> messageStep3
                else -> ""
            },
            highlights = when (currentStep2) {
                else -> listOf("")
            },
            verticalAlignment = VerticalAlignment.Bottom,
            ttsPlaybackHandler = ttsPlaybackHandler,
        )
    }



}
