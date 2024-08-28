package com.example.capstoneapp.fastfood.ui.guide

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.ui.Frame.GuidePopup
import com.example.capstoneapp.cafe.ui.Frame.VerticalAlignment
import com.example.capstoneapp.cafe.ui.Guide.ClosePopup
import com.example.capstoneapp.chatbot.utils.TtsPlaybackHandler
import com.example.capstoneapp.fastfood.data.model.OrderViewModel
import com.example.capstoneapp.fastfood.ui.frame.DividerFormat
import com.example.capstoneapp.fastfood.ui.frame.KioskButtonFormat
import com.example.capstoneapp.fastfood.ui.screens.ItemRow
import com.example.capstoneapp.fastfood.ui.screens.OptionCard
import com.example.capstoneapp.fastfood.ui.screens.OptionType
import com.example.capstoneapp.fastfood.ui.screens.OrderText
import com.example.capstoneapp.fastfood.ui.screens.PaymentPopup
import com.example.capstoneapp.fastfood.ui.screens.SummaryRow
import com.example.capstoneapp.kakatalk.ui.Components.RepeatDialog
import com.example.capstoneapp.nav.repository.Problem
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.texttospeech.v1.TextToSpeechClient
import com.google.cloud.texttospeech.v1.TextToSpeechSettings
import java.io.InputStream

@SuppressLint("RememberReturnType")
@Composable
fun Fastfood_Guide4(
    navController: NavController,
    viewModel: OrderViewModel,
    showBorder: Boolean,
    problem: Problem
) {
    //가이드팝업
    var showPopup by remember { mutableStateOf(true) }
    var currentStep by remember { mutableStateOf(1) } // 현재 단계 관리 변수
    val place: String
    val point: String
    val pay: String
    if (problem.place.contains("포장")){
        place = "포장"
    }else{
        place = "매장"
    }
    if (problem.point.contains("O")){
        point = "포인트"
    }else{
        point = "선택없음"
    }
    if (problem.pay.contains("카드 결제")){
        pay = "신용/체크카드"
    }else{
        pay = "모바일/페이"
    }
    val messageStep1 = "결제화면 입니다. ${place}을 눌러주세요."
    val messageStep2 = "그 다음 ${point}를 눌러주세요"
    val messageStep3 = "${pay}를 선택한 후 결제하기를 눌러주세요."

    val orderItems by viewModel.orderItems.observeAsState(initial = listOf())
    val totalAmount = orderItems.sumOf { it.menuItem.price * it.quantity }
    var closeDialog by remember { mutableStateOf(false) }
    var paymentDialog by remember { mutableStateOf(false) }

    var repeatAnswer by remember { mutableStateOf(false) }

    // 각 옵션의 선택 상태를 관리하는 상태 변수
    val selectedPackingOption = remember { mutableStateOf<OptionType?>(null) }
    val selectedDiscountOption = remember { mutableStateOf<OptionType?>(null) }
    val selectedPaymentOption = remember { mutableStateOf<OptionType?>(null) }

    // 모든 옵션이 선택되었는지 확인하는 상태 변수
    val allOptionsSelected = selectedPackingOption.value != null &&
            selectedDiscountOption.value != null &&
            selectedPaymentOption.value != null

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

    // 뒤로가기 처리
    BackHandler {
        viewModel.clearOrderItems()
        navController.navigate("HamburgerHomeScreen") {
            popUpTo("HamburgerHomeScreen") { inclusive = true }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.padding(8.dp))
        Row {
            Column(modifier = Modifier
                .weight(1.1f)
                .padding(start = 8.dp)
                .padding(top = 16.dp)
            ) {
                // Header
                Row {
                    Text("제품", modifier = Modifier.weight(1.7f))
                    Text("수량", modifier = Modifier
                        .weight(0.8f)
                        .align(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                    Text("금액", modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.End)
                    )
                }
                // Rows
                Spacer(modifier = Modifier.padding(4.dp))
                orderItems.forEach { orderItem ->
                    ItemRow(orderItem.menuItem.name, orderItem.quantity.toString(), "${orderItem.menuItem.price * orderItem.quantity}")
                }

                Spacer(modifier = Modifier.padding(16.dp))
                DividerFormat()
                Spacer(modifier = Modifier.padding(4.dp))

                Column {
                    SummaryRow(label = "주문금액", amount = totalAmount.toString())
                    SummaryRow(label = "할인금액", amount = "0")
                    Spacer(modifier = Modifier.padding(4.dp))
                    SummaryRow(label = "결제할금액", amount = totalAmount.toString(), isTotal = true)
                }

            }

            // Right Section for selectable image buttons
            Column(modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
            ) {
                OrderText("포장을 선택하세요.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionCard(
                        onClick = {
                            if(problem.place!="포장 하기"){
                                repeatAnswer=true
                            }else if(selectedPackingOption.value == OptionType.Packing){
                                selectedPackingOption.value = null
                            }else{
                                selectedPackingOption.value = OptionType.Packing
                            }},
                        text = "포장",
                        icon = painterResource(id = R.drawable.bag),
                        isSelected = selectedPackingOption.value == OptionType.Packing,
                        showBorder = showBorder&&(problem.place=="포장 하기"),
                    )
                    OptionCard(
                        onClick = {
                            if(problem.place!="매장에서 먹기"){
                                repeatAnswer=true
                            }else if(selectedPackingOption.value == OptionType.Store){
                                selectedPackingOption.value = null
                            }else{
                                selectedPackingOption.value = OptionType.Store
                            }},
                        text = "매장",
                        icon = painterResource(id = R.drawable.shop),
                        isSelected = selectedPackingOption.value == OptionType.Store,
                        showBorder = showBorder&&(problem.place=="매장에서 먹기"),
                    )
                }

                OrderText("할인/적립을 선택하세요.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionCard(
                        onClick = {
                            if(problem.point!="O"){
                                repeatAnswer=true
                            }else if(selectedDiscountOption.value == OptionType.Point){
                                selectedDiscountOption.value = null
                            }else{
                                selectedDiscountOption.value = OptionType.Point
                            }},
                        text = "포인트",
                        icon = painterResource(id = R.drawable.discount),
                        isSelected = selectedDiscountOption.value == OptionType.Point,
                        showBorder = showBorder&&(problem.point=="O"),
                    )
                    OptionCard(
                        onClick = {
                            if(problem.point!="X"){
                                repeatAnswer=true
                            }else if(selectedDiscountOption.value == OptionType.None){
                                selectedDiscountOption.value = null
                            }else{
                                selectedDiscountOption.value = OptionType.None
                            }},
                        text = "선택\n없음",
                        icon = painterResource(id = R.drawable.x),
                        isSelected = selectedDiscountOption.value == OptionType.None,
                        showBorder = showBorder&&(problem.point=="X"),
                    )
                }

                OrderText("결제를 선택하세요.")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OptionCard(
                        onClick = {
                            if(problem.pay!="카드 결제"){
                                repeatAnswer=true
                            }else if(selectedPaymentOption.value == OptionType.CreditCard){
                                selectedPaymentOption.value = null
                            }else{
                                selectedPaymentOption.value = OptionType.CreditCard
                            }},
                        text = "신용\n/체크카드",
                        icon = painterResource(id = R.drawable.cardicon),
                        isSelected = selectedPaymentOption.value == OptionType.CreditCard,
                        showBorder = showBorder&&(problem.pay=="카드 결제"),
                    )
                    OptionCard(
                        onClick = {
                            if(problem.pay!="모바일 페이"){
                                repeatAnswer=true
                            }else if(selectedPaymentOption.value == OptionType.MobilePay){
                                selectedPaymentOption.value = null
                            }else{
                                selectedPaymentOption.value = OptionType.MobilePay
                            }},
                        text = "모바일\n/페이",
                        icon = painterResource(id = R.drawable.pay),
                        isSelected = selectedPaymentOption.value == OptionType.MobilePay,
                        showBorder = showBorder&&(problem.pay.split(" ").contains("모바일")||problem.pay.split(" ").contains("페이")),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))

        // 결제 버튼
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp), // Apply horizontal padding
            horizontalArrangement = Arrangement.SpaceBetween // Arrange buttons with space in between
        ) {
            KioskButtonFormat(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp),
                onClick = {
                    if (allOptionsSelected) {
                        closeDialog = true
                    }
                },
                buttonText = "결제하기",
                backgroundColor = if (allOptionsSelected) Color.Red else Color.Gray,
                contentColor = Color.Black,
                enabled = allOptionsSelected
            )
        }
    }
    if(repeatAnswer){
        RepeatDialog(onDismiss = {
            repeatAnswer = false })
    }
    if (paymentDialog) {
        PaymentPopup(
            showDialog = true,
            onDismiss = {
                paymentDialog = false
                closeDialog = true
                        },
            onConfirm = { },
            navController = navController,
            viewModel = viewModel
        )
    }
    if(closeDialog){
        ClosePopup (
            onDismiss = {
                closeDialog = false
                navController.navigate("HamburgerHomeScreen") {
                    popUpTo("HamburgerHomeScreen") { inclusive = true }
                }
            }
        )
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
            title = "결제안내",
            message = when (currentStep) {
                1 -> messageStep1
                2 -> messageStep2
                3 -> messageStep3
                else -> ""
            },
            highlights = when (currentStep) {
                1 -> listOf(place,)
                2 -> listOf(point)
                3 -> listOf(pay,"결제하기")
                else -> listOf("")
            },
            verticalAlignment = VerticalAlignment.Bottom,
            ttsPlaybackHandler = ttsPlaybackHandler,
        )
    }
}

