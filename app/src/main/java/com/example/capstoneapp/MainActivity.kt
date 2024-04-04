package com.example.capstoneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capstoneapp.fastfood.data.model.ProblemViewModel
import com.example.capstoneapp.fastfood.data.model.ProblemViewModelFactory
import com.example.capstoneapp.fastfood.data.repository.ProblemRepository
import com.example.capstoneapp.fastfood.ui.components.AppNavigation
import com.example.capstoneapp.fastfood.ui.theme.CapstoneAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapstoneAppTheme {
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


