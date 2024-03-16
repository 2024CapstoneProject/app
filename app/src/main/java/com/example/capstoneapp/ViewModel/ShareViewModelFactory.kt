package com.example.capstoneapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneapp.Repository.ProblemRepository

class SharedViewModelFactory(private val problemRepository: ProblemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(problemRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
