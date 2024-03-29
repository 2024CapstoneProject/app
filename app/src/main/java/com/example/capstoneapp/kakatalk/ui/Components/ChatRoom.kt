package com.example.capstoneapp.kakatalk.ui.Components

import android.annotation.SuppressLint
import android.content.Context
import android.view.inputmethod.InputMethodManager
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.capstoneapp.MainActivity
import com.example.capstoneapp.R
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessage
import com.example.capstoneapp.kakatalk.data.Repository.ChatMessageRepository
import kotlinx.coroutines.launch


@Composable
fun ChatRoom(
    chatMessages: MutableList<ChatMessage>
) {
    val coroutineScope = rememberCoroutineScope()
    val keyboardVisible = isKeyboardVisible()
    val listState = rememberLazyListState()
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
        ) {
            ChatDetail(chatMessages = chatMessages, listState)
        }
        TextBox(onNewMessageSent = { newMessage ->
            chatMessages.add(newMessage)
            coroutineScope.launch {
                // 키보드가 올라올 때 ChatDetail을 스크롤하도록 함
                if (keyboardVisible) {
                    listState.scrollToItem(Int.MAX_VALUE)
                    weight = 0.5f
                } else {
                    weight = 1f
                }
            }
        })
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextBox(onNewMessageSent: (ChatMessage) -> Unit) {
    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        focusedIndicatorColor = Color.White,
        unfocusedIndicatorColor = Color.White,
        disabledIndicatorColor = Color.White
    )
    var inputTextState by remember { mutableStateOf(TextFieldValue()) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(80.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Add,
                contentDescription = "add",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        // 메세지 보내기
                        inputTextState = TextFieldValue()
                        keyboardController?.hide()
                    })
            TextField(value = inputTextState,
                onValueChange = { inputTextState = it },
                placeholder = { Text(text = "입력해주세요") },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .border(
                        BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(16.dp)
                    )
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
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
                    val newMessage = ChatMessage("m", "나", inputTextState.text, "Now")
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
                        // 메세지 보내기
                        val newMessage = ChatMessage("m", "나", inputTextState.text, "Now")
                        onNewMessageSent(newMessage)
                        inputTextState = TextFieldValue()
                        keyboardController?.hide()
                    })
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

@Preview
@Composable
fun PhotoBox() {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(400.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .height(76.dp)
                        .width(76.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(

                        modifier = Modifier
                            .width(48.dp)
                            .height(48.dp)
                            .background(
                                color = Color("#87D459".toColorInt()),
                                shape = RoundedCornerShape(40.dp)
                            )
                    ) {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(id = R.drawable.gallery_icon),
                                contentDescription = "Favorite",
                                modifier = Modifier
                                    .width(48.dp)
                                    .height(48.dp),
                                tint = Color.White
                            )
                        }
                    }
                    Text(text = "앨범")
                }

            }
        }
    }
}

@Composable
@Preview
fun ChatRoomPreview() {
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    LaunchedEffect(Unit) {
        chatMessages.addAll(ChatMessageRepository.getSimpleChat())
    }

    ChatRoom(chatMessages = chatMessages)
}