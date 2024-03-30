package com.example.capstoneapp.fastfood.data.repository

import kotlin.random.Random

object ProblemRepository {
    private var menuList = listOf(
        "불고기버거, 콜라, 감자튀김"
    )

    private var placeList = listOf(
        "매장에서 먹기"
    )

    private var isPoint = listOf(
        "X"
    )

    private var payList = listOf(
        "카드 결제"
    )

    fun createProblem(): Problem {
        val randomMenuIndex = Random.nextInt(menuList.size)
        val randomPlaceIndex = Random.nextInt(placeList.size)
        val randomPointIndex = Random.nextInt(isPoint.size)
        val randomPayIndex = Random.nextInt(payList.size)

        return Problem(
            menu = menuList[randomMenuIndex],
            place = placeList[randomPlaceIndex],
            point = isPoint[randomPointIndex],
            pay = payList[randomPayIndex]
        )
    }
}

data class Problem(
    val menu: String,
    var place: String,
    val point: String,
    val pay: String,
)