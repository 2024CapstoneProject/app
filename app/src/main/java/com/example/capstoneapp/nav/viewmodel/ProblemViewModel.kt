package com.example.capstoneapp.nav.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneapp.nav.repository.KakaotalkProblem
import com.example.capstoneapp.nav.repository.Problem
import com.example.capstoneapp.nav.repository.ProblemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProblemViewModel @Inject constructor(private val problemRepository: ProblemRepository) :
    ViewModel() {

    private val _problem = MutableLiveData<Problem>()
    private val _kakaotalkproblem = MutableLiveData<KakaotalkProblem>()
    val problem: LiveData<Problem> = _problem
    val kakaotalkproblem: LiveData<KakaotalkProblem> = _kakaotalkproblem

    init {
        if (_problem.value == null) {
            _problem.value = problemRepository.createProblem()
        }
        if(_kakaotalkproblem.value == null){
            _kakaotalkproblem.value = problemRepository.createKakaotalkProblem()
        }
        _problem.value!!.order = _kakaotalkproblem.value!!.person+"에게 "+_kakaotalkproblem.value!!.content
    }

    fun createProblem() {
        var problem = problemRepository.createProblem()
        _problem.value = problem
    }

    fun createKakaotalkProblem(){
        val problem = problemRepository.createKakaotalkProblem()
        _kakaotalkproblem.value = problem
        _problem.value!!.order = problem.person+"에게 "+problem.content
    }

    fun getProblemValue(): Problem? {
        return _problem.value
    }

    fun getKakaotalkProblemValue(): KakaotalkProblem?{
        return _kakaotalkproblem.value
    }
}