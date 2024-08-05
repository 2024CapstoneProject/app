package com.example.capstoneapp.cafe.ui.Frame

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun GuidePopup(
    isPopupVisible: Boolean,
    onDismiss: () -> Unit,
    title: String,
    message: String,
    confirmButtonText: String = "확인",
    onConfirm: () -> Unit = {}
) {
    if (isPopupVisible) {
        // Using a Box to intercept clicks outside of the popup and prevent closing
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            Popup(
                alignment = Alignment.Center,
                properties = PopupProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 350.dp) // Set a maximum width for the popup
                        .background(Color.White, RoundedCornerShape(16.dp)) // Rounded corners
                        .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)) // Border around the popup
                        .padding(16.dp)
                        .wrapContentSize()
                ) {
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(onClick = {
                                onConfirm()
                                onDismiss()
                            }) {
                                Text(
                                    text = confirmButtonText,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}