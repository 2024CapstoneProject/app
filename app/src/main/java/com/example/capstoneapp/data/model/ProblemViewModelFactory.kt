package com.example.capstoneapp.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneapp.data.repository.ProblemRepository

class ProblemViewModelFactory(private val problemRepository: ProblemRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProblemViewModel::class.java)) {
            return ProblemViewModel(problemRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}