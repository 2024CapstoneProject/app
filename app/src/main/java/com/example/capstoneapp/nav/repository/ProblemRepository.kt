package com.example.capstoneapp.nav.repository

import com.example.capstoneapp.R
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
        "아메리카노", "카페라떼", "바닐라라떼", "녹차", "캐모마일"
    )

    private var c_placeList = listOf(
        "매장에서 먹기", "포장하기"
    )

    private var c_isPoint = listOf(
        "O", "X"
    )

    private var c_payList = listOf(
        "카드 결제", "현금 결제"
    )
    //kakao
    private var personList = listOf(
        "아들","딸","남편","손녀"
    )

    private var type = listOf("photo","simple")

    private var simpleProblemList = listOf(
        Pair("\'뭐하니?\'라고 문자 보내기","뭐하니"),
        Pair("\'경로당에 있다고 문자 보내기","경로당"),
        Pair("전화번호 보내기\n(전화번호는 010-1234-5678입니다)","010-1234-5678")
    )

    private var photoProblemList = listOf(
        Pair("하얀색 티셔츠 사진 보내기","0"),
        Pair("검은색 티셔츠 사진 보내기","1"),
        Pair("커피 사진 보내기","2")
    )

    private var photoIdList = listOf(
        R.drawable.sample_1,
        R.drawable.sample_2,
        R.drawable.sample_3
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

        return Problem(
            menu = menuList[randomMenuIndex],
            place = placeList[randomPlaceIndex],
            point = isPoint[randomPointIndex],
            pay = payList[randomPayIndex],
            c_menu = c_menuList[randomCMenuIndex],
            c_place = c_placeList[randomCPlaceIndex],
            c_point = c_isPoint[randomCPointIndex],
            c_pay = c_payList[randomCPayIndex],
            order = ""
        )
    }

    fun createKakaotalkProblem(): KakaotalkProblem{
        val randomPerson = personList[Random.nextInt(personList.size)]
        val randomType = type[Random.nextInt(type.size)]
        val problemPair = if(randomType.equals("simple")) {simpleProblemList[Random.nextInt(simpleProblemList.size)]}
        else {photoProblemList[Random.nextInt(photoProblemList.size)]}

        return KakaotalkProblem(
            type = randomType,
            person = randomPerson,
            content = problemPair.first,
            photoId = if(randomType.equals("simple")) 0 else photoIdList[Integer.parseInt(problemPair.second)],
            answer = if(randomType.equals("simple")) problemPair.second else "0"
        )
    }

}

data class Problem(
    val menu: String,
    var place: String,
    val point: String,
    val pay: String,
    val c_menu: String,
    var c_place: String,
    val c_point: String,
    val c_pay: String,
    var order: String
)

data class KakaotalkProblem(
    val type: String,
    var person: String,
    var content: String,
    var photoId: Int = 0,
    var answer: String
)