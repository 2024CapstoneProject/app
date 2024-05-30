package com.example.capstoneapp.fastfood.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capstoneapp.fastfood.ui.components.CustomizedNavigationBar
import com.example.capstoneapp.fastfood.ui.components.ItemList
import com.example.capstoneapp.nav.repository.MenuItem
import com.example.capstoneapp.nav.repository.Problem

@Composable
fun SelectSetDessertScreen(
    selectedDessert: MenuItem?,
    selectedDrink: MenuItem?,
    onDessertSelected: (MenuItem) -> Unit,
    onDrinkSelected: (MenuItem) -> Unit,
    showBorder: Boolean,
    problem: Problem
) {
    // 네비게이션 카테고리 선택
    var selectedMenu by remember { mutableStateOf("세트 디저트") } // 초기값 설정
    val myMenuItems = listOf("세트 디저트", "세트 드링크")

    Column(
        modifier = Modifier.padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 네비게이션 bar
        Box {
            CustomizedNavigationBar(
                menuItems = myMenuItems,
                selectedMenuItem = selectedMenu,
                onMenuItemClick = { menuItem ->
                    selectedMenu = menuItem // 메뉴 항목 클릭 시 선택된 메뉴 업데이트
                }
            )
        }

        Spacer(Modifier.padding(top = 16.dp))
        Text(
            text = if (selectedMenu == "세트 디저트") {
                "세트 디저트 1개를 선택해주세요."
            } else {
                "세트 드링크 1개를 선택해주세요."
            },
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default
            )
        )

        // 이미 선택된 항목을 표시
        if (selectedDessert != null && selectedMenu == "세트 디저트") {
            Text(
                text = "선택된 디저트: ${selectedDessert.name}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Default,
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (selectedDrink != null && selectedMenu == "세트 드링크") {
            Text(
                text = "선택된 드링크: ${selectedDrink.name}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Default,
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // 메뉴 목록 표시
        ItemList(
            selectedMenu = selectedMenu,
            selectedItem = if (selectedMenu == "세트 디저트") selectedDessert else selectedDrink,
            onItemClicked = { selectedItem ->
                if (selectedMenu == "세트 디저트" && selectedDessert == null) {
                    onDessertSelected(selectedItem)
                } else if (selectedMenu == "세트 드링크" && selectedDrink == null) {
                    onDrinkSelected(selectedItem)
                }
            }, showBorder = showBorder, problem = problem
        )
    }
}
