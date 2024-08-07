package com.example.capstoneapp.kakatalk.ui.Components

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capstoneapp.MainActivity
import com.example.capstoneapp.fastfood.ui.theme.BorderColor
import com.example.capstoneapp.fastfood.ui.theme.BorderShape
import com.example.capstoneapp.fastfood.ui.theme.BorderWidth
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessage
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessageRepository
import com.example.capstoneapp.nav.repository.KakaotalkProblem
import com.example.capstoneapp.nav.repository.ProblemRepository
import com.example.capstoneapp.nav.viewmodel.ProblemViewModel
import com.example.capstoneapp.nav.viewmodel.ProblemViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun ChatRoom(
    chatMessages: MutableList<ChatMessage>,
    photoList: MutableList<Int>,
    showBorder:Boolean,
    problem: KakaotalkProblem,
    onButtonClick: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val keyboardVisible = isKeyboardVisible()
    val listState = rememberLazyListState()
    var closePractice by remember { mutableStateOf(false) }
    var repeatAnswer by remember { mutableStateOf(false) }

    var weight: Float = 1f
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color("#AFC0CF".toColorInt()), shape = RoundedCornerShape(16.dp)),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .weight(weight)
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            ChatDetail(chatMessages = chatMessages, listState)
        }
        TextBox(onNewMessageSent = { newMessage ->
            chatMessages.add(newMessage)
            coroutineScope.launch {
                // 키보드가 올라올 때 ChatDetail을 스크롤하도록 함
                if (keyboardVisible) {
                    listState.scrollToItem(chatMessages.size - 1)
                    weight = 0.5f
                } else {
                    weight = 1f
                }
            }

            if (problem.type.equals("simple")) {
                if (newMessage.content.contains(problem.answer)) {
                    closePractice = true
                }else{
                    repeatAnswer = true
                }
            } else if (problem.type.equals("photo")) {
                if (newMessage.photoId == problem.photoId) {
                    closePractice = true
                }else{
                    repeatAnswer = true
                }
            }
        }, photoList,showBorder,problem)
        if (closePractice) {
            CloseDialog(onDismiss = {
                closePractice = false
                onButtonClick(true)
            })
        }
        if(repeatAnswer){
            RepeatDialog(onDismiss = {
                repeatAnswer = false })
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextBox(onNewMessageSent: (ChatMessage) -> Unit, photoList: List<Int>,showBorder:Boolean,problem: KakaotalkProblem) {
    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        focusedIndicatorColor = Color.White,
        unfocusedIndicatorColor = Color.White,
        disabledIndicatorColor = Color.White
    )

    /*
    * isKeyboardOrButtonOrBox[0] = 옵션 버튼 선택 여부
    * isKeyboardOrButtonOrBox[1] = 키보드 스크롤 여부
    * isKeyboardOrButtonOrBox[2] = 앨범 버튼 선택 여부
    * */
    val isButtonOrKeyboardOrBox = listOf(remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) })

    var inputTextState by remember { mutableStateOf(TextFieldValue()) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var keyboardHeight by remember { mutableStateOf(0.dp) }

    var extraPadding: Dp = 0.dp

    val context = LocalContext.current
    val activity = context as? ComponentActivity

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var onNewPhotoMessage = remember { mutableStateOf(0) }

    var photoProblemBorder = mutableListOf(
        0.dp,Color.Transparent,RoundedCornerShape(0.dp)
    )

    var textProblemBorder = mutableListOf(
        1.dp,Color.Gray, RoundedCornerShape(16.dp)
    )

    if(showBorder && problem.type == "photo"){
        if(isButtonOrKeyboardOrBox[2].value){
            photoProblemBorder[0] = 0.dp
            photoProblemBorder[1] = Color.Transparent
            photoProblemBorder[2] = RoundedCornerShape(0.dp)
        }else{
            photoProblemBorder[0] = BorderWidth
            photoProblemBorder[1] = BorderColor
            photoProblemBorder[2] = BorderShape
        }

    }else if(showBorder && problem.type == "simple"){
        textProblemBorder[0] = BorderWidth
        textProblemBorder[1] = BorderColor
        textProblemBorder[2] = RoundedCornerShape(16.dp)
    }else{
        photoProblemBorder[0] = 0.dp
        photoProblemBorder[1] = Color.Transparent
        photoProblemBorder[2] = RoundedCornerShape(0.dp)
        textProblemBorder[0] = 1.dp
        textProblemBorder[1] = Color.Gray
        textProblemBorder[2] = RoundedCornerShape(16.dp)
    }

    LaunchedEffect(activity) {
        val rootView = activity?.window?.decorView?.rootView
        rootView?.viewTreeObserver?.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val insets = rootView.rootWindowInsets
                val keyboardHeightNow = insets?.systemWindowInsetBottom ?: 0
                keyboardHeight = keyboardHeightNow.dp
            }
        })
    }

    /*
    * 옵션 버튼 선택했을 경우
    * -> 키보드 스크롤 됐다면 옵션창 닫기
    * -> 아니라면 키보드 높이만큼 옵션창 높이 설정
    * 옵션 버튼 선택하지 않았을 경우
    * -> 옵션창 닫기
    * */
    if (isButtonOrKeyboardOrBox[0].value) {
        if (isButtonOrKeyboardOrBox[1].value) isButtonOrKeyboardOrBox[0].value =
            !isButtonOrKeyboardOrBox[0].value
        else extraPadding = keyboardHeight
    } else extraPadding = 0.dp

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(80.dp + extraPadding)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                //만약 앨범 버튼 선택했을 경우 아이콘 변경
                imageVector = if (isButtonOrKeyboardOrBox[2].value) Icons.Filled.ArrowBack else Icons.Filled.Add,
                contentDescription = "add",
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        width = photoProblemBorder[0] as Dp,
                        color = photoProblemBorder[1] as Color,
                        shape = photoProblemBorder[2] as RoundedCornerShape
                    )
                    .clickable {
                        /*
                        * 아이콘 처음 선택했을 경우
                        * -> 텍스트필드 포커스 해제
                        * -> 키보드 스크롤 해제
                        * -> 옵션창 열기
                        * 두 번 선택시
                        * -> 앨범 버튼 해제
                        * -> 옵션창 닫기
                        * */
                        focusManager.clearFocus()
                        isButtonOrKeyboardOrBox[1].value = false
                        inputTextState = TextFieldValue()
                        isButtonOrKeyboardOrBox[0].value = !isButtonOrKeyboardOrBox[0].value
                        isButtonOrKeyboardOrBox[2].value = false

                    })
            TextField(
                value = inputTextState,
                onValueChange = { inputTextState = it },
                placeholder = { Text(text = "") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .border(
                        BorderStroke(textProblemBorder[0] as Dp, textProblemBorder[1] as Color), shape =textProblemBorder[2] as RoundedCornerShape
                    )
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        /*
                        * 텍스트 필드 포커싱되면
                        * -> 앨범 닫기
                        * -> 키보드 스크롤
                        * */
                        isButtonOrKeyboardOrBox[1].value = focusState.isFocused
                        if (focusState.isFocused) {
                            isButtonOrKeyboardOrBox[2].value = false
                            keyboardController?.show()
                        } else {
                            keyboardController?.hide()
                        }
                    },
                colors = textFieldColors,
                singleLine = true,
                textStyle = TextStyle(fontSize = 16.sp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    val newMessage = ChatMessage("m", "나", inputTextState.text, 0, "Now")
                    onNewMessageSent(newMessage)
                    inputTextState = TextFieldValue()
                    keyboardController?.hide()
                })
            )
            Icon(Icons.Filled.Send,
                contentDescription = "send",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        /*
                        * 사진 전송 시 -> 메세지
                        * */
                        val newMessage: ChatMessage
                        if (inputTextState.text == "" && onNewPhotoMessage.value != 0) { //사진 전송할 경우
                            newMessage = ChatMessage("m", "나", "", onNewPhotoMessage.value, "Now")
                            isButtonOrKeyboardOrBox[0].value = !isButtonOrKeyboardOrBox[0].value
                        } else {
                            newMessage = ChatMessage("m", "나", inputTextState.text, 0, "Now")
                        }
                        onNewMessageSent(newMessage)
                        inputTextState = TextFieldValue()
                        onNewPhotoMessage.value = 0
                        keyboardController?.hide()
                    })
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    0.dp,
                    Color.Transparent,
                    RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                ), contentAlignment = Alignment.BottomCenter
        ) {
            if (!isButtonOrKeyboardOrBox[2].value) PhotoBox(boxSize = extraPadding,showBorder, problem) {
                isButtonOrKeyboardOrBox[2].value = !isButtonOrKeyboardOrBox[2].value
            }
            else if (isButtonOrKeyboardOrBox[2].value) photoBlock(extraPadding, photoList,showBorder,problem) {
                onNewPhotoMessage.value = it
            }
        }
    }
}

@SuppressLint("ServiceCast")
@Composable
fun isKeyboardVisible(): Boolean {
    val context = LocalContext.current
    val activity = (context as? MainActivity) ?: return false
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return imm.isAcceptingText
}

@Composable
@Preview
fun ChatRoomPreview() {
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    val photoList = remember { mutableStateListOf<Int>() }
    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
    chatMessages.addAll(ChatMessageRepository.getSimpleChat(problemViewModel.getKakaotalkProblemValue()!!.person,problemViewModel.getKakaotalkProblemValue()!!.index))
    ChatRoom(chatMessages = chatMessages, photoList,true,problemViewModel.getKakaotalkProblemValue()!!){}
}