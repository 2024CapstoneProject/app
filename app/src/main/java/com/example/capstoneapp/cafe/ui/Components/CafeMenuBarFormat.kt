package com.example.capstoneapp.cafe.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.nav.repository.ProblemRepository

@Composable
fun CafeMenuBarFormat(bar:@Composable () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth(
            ) // Fill the width of the parent
            .padding() // Padding from the left and right
            // Height to wrap content
            .height(100.dp) // Fill 50% of the height of the parent
            .background(
                color = Color(0xffE7E7E7), // Change this color to your desired background color
                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp
                ) // Rounded corners
            )
            .border(0.dp, Color.Transparent, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) ,// Border
        contentAlignment = Alignment.Center
    ){
        bar()
    }
}

@Preview
@Composable
fun CafeMenuBarPreview(){
    val navController = rememberNavController()

    var selectedMenu by remember { mutableStateOf("커피(HOT)") }
    val menuCategory = listOf("커피(HOT)", "커피(ICE)", "티(TEA)")
    CafeMenuBarFormat {
        CafeMenuBar(
            menuItems = menuCategory,
            selectedMenu = selectedMenu,
            onMenuItemClick = { menuItem ->
                if (menuItem.equals("HOME")) {
                    navController.navigate("touchToStartCafe")
                } else {
                    selectedMenu = menuItem
                }
            }, true, ProblemRepository.createProblem()
        )
    }
}