package com.example.capstoneapp.data.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneapp.data.Repository.Problem
import com.example.capstoneapp.data.Repository.ProblemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val problemRepository: ProblemRepository) : ViewModel() {

        private var problem: Problem? = null

        fun createRandomProblem() {
            viewModelScope.launch {
                problem = problemRepository.createProblem()
            }
        }

        fun getProblem(): Problem? {
            return problem
        }
    }