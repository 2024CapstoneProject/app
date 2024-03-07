package com.example.capstoneapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.Repository.MenuItem

@Composable
fun ShowMenu(navController: NavController){
    val orderItems = remember{
        mutableStateListOf<Pair<MenuItem,Int>>()
    }
    var selectedMenu by remember{ mutableStateOf("커피(HOT)") }
    val menuCategory = listOf("커피(HOT)","커피(ICE)","티(TEA)")



    @Composable
    fun SelectedMenuSpec(selectedMenuName:String,selectedMenuCount:Int){
        Box(
            modifier = Modifier
                .height(32.dp)
                .width(230.dp),
            contentAlignment = Alignment.Center
        ) {

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    modifier = Modifier.size(28.dp),
                    onClick = {
                        val targetPair = orderItems.firstOrNull(){it.first.name == selectedMenuName}
                        orderItems.remove(targetPair)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "clear",
                        modifier = Modifier
                            .size(28.dp)
                            .padding(4.dp)
                    )
                }
                Text(
                    text = selectedMenuName,
                    modifier = Modifier.width(120.dp),
                    textAlign = TextAlign.Center
                )
                IconButton(
                    modifier = Modifier.size(28.dp),
                    onClick = {
                        val targetPair = orderItems.firstOrNull(){it.first.name == selectedMenuName}

                        if(targetPair != null) {
                            val index = orderItems.indexOf(targetPair)
                            orderItems[index] = targetPair.copy(second = targetPair.second - 1)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear",
                        modifier = Modifier
                            .size(28.dp)
                            .padding(4.dp)
                    )
                }
                Text(
                    text = selectedMenuCount.toString(),
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = {
                        val targetPair = orderItems.firstOrNull(){it.first.name == selectedMenuName}

                        if(targetPair != null) {
                            val index = orderItems.indexOf(targetPair)
                            orderItems[index] = targetPair.copy(second = targetPair.second + 1)
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        modifier = Modifier
                            .size(32.dp)
                            .padding(4.dp)
                    )
                }
            }
        }

    }


    Column() {
        Column(//메뉴 리스트 Column
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CafeMenuBarFormat {
                CafeMenuBar(
                    menuItems = menuCategory,
                    onMenuItemClick = { menuItem ->
                        selectedMenu = menuItem
                    })
            }
            /*
            * 선택한 메뉴 종류에 따라 메뉴 리스트를 보여줌
            *
            * selectedMenu : 종류
            * selectedItem : 선택한 메뉴
            *  */
            CafeMenuList(selectedMenu = selectedMenu) { selectedItem ->
                val targetPair = orderItems.firstOrNull() { it.first.name == selectedItem.name }

                if (targetPair != null) {
                    val index = orderItems.indexOf(targetPair)
                    orderItems[index] = targetPair.copy(second = targetPair.second + 1)
                } else
                    orderItems.add(Pair(selectedItem, 1))
            }
        }

        Column( //빈공간
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(160.dp))
        }

        Row(//선택한 메뉴, 남은시간, 결제 버튼 공간
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .width(230.dp)
                    .fillMaxHeight()
            ) {
                Divider(
                    color = Color.Gray, // 선의 색상 지정
                    thickness = 2.dp, // 선의 두께 지정
                    modifier = Modifier
                        .padding()
                        .width(234.dp)
                )

                Box(//선택한 메뉴 보여줌
                    modifier = Modifier
                        .width(230.dp)
                        .fillMaxHeight()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {//선택한 메뉴 갯수에 따라 메뉴리스트 조회가능
                        items(orderItems.size) { it ->
                            SelectedMenuSpec(
                                selectedMenuName = orderItems.get(it).first.name,
                                selectedMenuCount = orderItems.get(it).second
                            )
                        }
                    }
                }
            }
            Divider(
                color = Color.Gray, // 선의 색상 지정
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
            )
            totalOrder(orderItems, navController = navController)
        }
    }
}
