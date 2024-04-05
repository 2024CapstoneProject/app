package com.example.capstoneapp.Screens

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import com.example.capstoneapp.Frame.TopAppBar
import com.example.capstoneapp.R
import com.google.android.gms.maps.MapView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProtectMapScreen(content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 56.dp)
                .padding(bottom = 128.dp)
                .padding(top = 184.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Fill the width of the parent
                    .padding(horizontal = 1.dp) // Padding from the left and right
                    .fillMaxHeight(0.3f) // Fill the height of the parent
                    .weight(1f)
                    .background(
                        color = Color.LightGray, // Change this color to your desired background color
                        shape = RoundedCornerShape(16.dp) // Rounded corners
                    )
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),// Border
                contentAlignment = Alignment.Center
            ) {
                //content()
            }
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Fill the width of the parent
                    .padding(horizontal = 1.dp) // Padding from the left and right
                    .fillMaxHeight(0.3f) // Fill the height of the parent
                    .weight(1f)
                    .background(
                        color = Color.LightGray, // Change this color to your desired background color
                        shape = RoundedCornerShape(16.dp) // Rounded corners
                    )
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),// Border
                contentAlignment = Alignment.Center
            ) {
                //content()
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Fill the width of the parent
                    .padding(horizontal = 1.dp) // Padding from the left and right
                    .fillMaxHeight(0.3f) // Fill the height of the parent
                    .weight(1f)
                    .background(
                        color = Color.LightGray, // Change this color to your desired background color
                        shape = RoundedCornerShape(16.dp) // Rounded corners
                    )
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),// Border
                contentAlignment = Alignment.Center
            ) {
                //content()
            }
        }
    }
}

@Preview
@Composable
fun ProtectMapScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Icon",
                    modifier = Modifier.size(48.dp)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text("이소윤", fontWeight = FontWeight.ExtraBold)
                    Text("본인", fontWeight = FontWeight.ExtraBold)
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth() // Fill the width of the parent
                .padding(horizontal = 1.dp) // Padding from the left and right
                .fillMaxHeight() // Fill the height of the parent
                .weight(1f)
                .background(
                    color = Color.LightGray, // Change this color to your desired background color
                    shape = RoundedCornerShape(16.dp) // Rounded corners
                )
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),// Border
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    MapView(context).apply {
                        // Initialize MapView
                        onCreate(null)
                        // You can customize MapView settings here
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
