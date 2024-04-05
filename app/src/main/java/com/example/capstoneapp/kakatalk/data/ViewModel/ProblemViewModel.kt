package com.example.capstoneapp.kakatalk.data.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneapp.cafe.data.Repository.Problem
import com.example.capstoneapp.cafe.data.Repository.ProblemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProblemViewModel @Inject constructor(private val problemRepository: ProblemRepository) :
    ViewModel() {

    private val _problem = MutableLiveData<Problem>()
    val problem: LiveData<Problem> = _problem

    init {
        if (_problem.value == null) {
            _problem.value = problemRepository.createProblem()
        }
    }

    fun createProblem() {
        val problem = problemRepository.createProblem()
        _problem.value = problem
    }

    fun getProblemValue(): Problem? {
        return _problem.value
    }
}