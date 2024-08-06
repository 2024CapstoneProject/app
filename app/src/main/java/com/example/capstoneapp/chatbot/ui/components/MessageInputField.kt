package com.example.capstoneapp.chatbot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.R

@Composable
fun MessageInputField(
    question: String,
    onQuestionChange: (String) -> Unit,
    onSendClick: () -> Unit,

) {

    val fontSize = 21.sp
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        OutlinedTextField(
            value = question,
            onValueChange = onQuestionChange,
            label = { Text("무엇이든 물어보세요", fontSize = fontSize) },
            textStyle = LocalTextStyle.current.copy(fontSize = fontSize),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onDone = {
                if (question.isNotBlank()) {
                    onSendClick()
                }
            })
        )
        IconButton(
            onClick = { if (question.isNotBlank()) onSendClick() },
            modifier = Modifier.size(48.dp)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.social_send_now),
                contentDescription = "Next",
                colorFilter = ColorFilter.tint(Color(0xffff602e)) ,
                modifier = Modifier.size(36.dp)
            )
        }

    }
}