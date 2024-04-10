package com.example.capstoneapp.nav.repository

import kotlin.random.Random

object ProblemRepository {
    //fastfood
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
    //cafe
    private var c_menuList = listOf(
        "아이스 아메리카노"
    )

    private var c_placeList = listOf(
        "매장에서 먹기"
    )

    private var c_isPoint = listOf(
        "O"
    )

    private var c_payList = listOf(
        "카드 결제"
    )
    //kakao
    private var personList = listOf(
        "아들"
    )

    private var orderList = listOf(
        "메세지 보내기"
    )

    fun createProblem(): Problem {
        val randomMenuIndex = Random.nextInt(menuList.size)
        val randomPlaceIndex = Random.nextInt(placeList.size)
        val randomPointIndex = Random.nextInt(isPoint.size)
        val randomPayIndex = Random.nextInt(payList.size)
        val randomCMenuIndex = Random.nextInt(c_menuList.size)
        val randomCPlaceIndex = Random.nextInt(c_placeList.size)
        val randomCPointIndex = Random.nextInt(c_isPoint.size)
        val randomCPayIndex = Random.nextInt(c_payList.size)
        val randomPersonIndex = Random.nextInt(personList.size)
        val randomOrderIndex = Random.nextInt(orderList.size)

        return Problem(
            menu = menuList[randomMenuIndex],
            place = placeList[randomPlaceIndex],
            point = isPoint[randomPointIndex],
            pay = payList[randomPayIndex],
            c_menu = c_menuList[randomCMenuIndex],
            c_place = c_placeList[randomCPlaceIndex],
            c_point = c_isPoint[randomCPointIndex],
            c_pay = c_payList[randomCPayIndex],
            person = personList[randomPersonIndex],
            order = orderList[randomOrderIndex]
        )
    }
}

data class Problem(
    val menu: String,
    var place: String,
    val point: String,
    val pay: String,
    val person: String,
    var order: String,
    val c_menu: String,
    var c_place: String,
    val c_point: String,
    val c_pay: String,
)