package com.example.capstoneapp.kakatalk.data.Repository


import kotlin.random.Random


object ProblemRepository {

    private var menuList = listOf(
        "아메리카노", "카페라떼", "바닐라라떼", "녹차", "캐모마일"
    )

    private var placeList = listOf(
        "매장에서 먹기", "포장하기"
    )

    private var isPoint = listOf(
        "O", "X"
    )

    private var payList = listOf(
        "카드 결제", "현금 결제"
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