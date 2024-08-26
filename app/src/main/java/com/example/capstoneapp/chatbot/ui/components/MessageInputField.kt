package com.example.capstoneapp.chatbot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.R
import com.example.capstoneapp.cafe.ui.theme.LightYellow

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
            textStyle = LocalTextStyle.current.copy(fontSize = fontSize),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .shadow(
                    elevation = 6.dp, // Slightly higher elevation for a wider shadow
                    shape = RoundedCornerShape(16.dp), // Rounded corners
                //    ambientColor = Color.Black.copy(alpha = 1f), // Lighter gray shadow
                    spotColor = Color.Black.copy(alpha = 1f) // Lighter gray shadow
                )
                .clip(RoundedCornerShape(16.dp)) // Clip the corners to be rounded
                .background(Color.White), // Background color (if needed)
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
            modifier = Modifier
                .size(52.dp)
                .background(
                    color = LightYellow, // Yellow background
                    shape = RoundedCornerShape(4.dp) // Rectangular shape with slight rounding
                )
                .padding(6.dp) // Padding inside the button
        ) {
            Image(
                painter = painterResource(id = R.mipmap.social_send_now),
                contentDescription = "Next",
                colorFilter = ColorFilter.tint(Color(0xffffffff)) ,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MessageInputFieldPreview() {
    // Sample state for the question text
    var question by remember { mutableStateOf("무엇이든 물어보세요") }

    // Call the MessageInputField composable with sample data
    MessageInputField(
        question = question,
        onQuestionChange = { newText -> question = newText },
        onSendClick = {
            // Handle send button click event for preview
            println("Send button clicked with question: $question")
        }
    )
}
