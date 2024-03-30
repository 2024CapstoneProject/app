package com.example.capstoneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.fastfood.data.model.PreviewOrderViewModel
import com.example.capstoneapp.fastfood.data.model.ProblemViewModel
import com.example.capstoneapp.fastfood.data.model.ProblemViewModelFactory
import com.example.capstoneapp.fastfood.data.repository.ProblemRepository
import com.example.capstoneapp.fastfood.ui.components.AppNavigation
import com.example.capstoneapp.fastfood.ui.frame.NotificationScreen
import com.example.capstoneapp.fastfood.ui.screens.GuideImage
import com.example.capstoneapp.fastfood.ui.screens.OrderScreen
import com.example.capstoneapp.fastfood.ui.screens.guideText
import com.example.capstoneapp.fastfood.ui.theme.CapstoneAppTheme

import androidx.navigation.compose.rememberNavController
import com.example.capstoneapp.cafe.ui.Navigation.SetUpNavGraph
import com.example.capstoneapp.cafe.ui.theme.CapstoneAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapstoneAppTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val problemViewModelFactory = ProblemViewModelFactory(ProblemRepository)
                    val problemViewModel: ProblemViewModel = viewModel(factory = problemViewModelFactory)
                    AppNavigation(problemViewModel)
                }
            }
        }
    }
}

//                 val navController = rememberNavController()
//                 SetUpNavGraph(navController = navController)
//             }
//         }
//     }
// }

