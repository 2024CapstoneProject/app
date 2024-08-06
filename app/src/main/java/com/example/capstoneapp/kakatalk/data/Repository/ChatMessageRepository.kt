package com.example.capstoneapp.kakatalk.data.Repository

import com.example.capstoneapp.R

object ChatMessageRepository {

    private val sonSimpleChatMission = listOf(
        listOf(
            ChatMessage("m", "나", "아들 언제와?", 0, "4:00 PM"),
            ChatMessage("o", "아들", "곧이요.", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "아들", "어디에요?", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "아들", "전화번호 좀 알려주세요.", 0, "4:01 PM")
        )
    )

    private val daughterSimpleChatMission = listOf(
        listOf(
            ChatMessage("m", "나", "딸 조심히 오렴", 0, "4:00 PM"),
            ChatMessage("o", "딸", "네 알겠어요", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "딸", "어디에요?", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "딸", "전화번호 좀 알려주세요.", 0, "4:01 PM")
        )
    )

    private val husbandSimpleChatMission = listOf(
        listOf(
            ChatMessage("m", "나", "어디쯤이신가요?", 0, "4:00 PM"),
            ChatMessage("o", "남편", "10분 후에 도착해요", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "남편", "어디에요?", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "남편", "전화번호가 뭐였죠?", 0, "4:01 PM")
        )
    )

    private val grandDaughterSimpleChatMission = listOf(
        listOf(
            ChatMessage("o", "손녀", "건강하세요!", 0, "4:00 PM"),
            ChatMessage("m", "나", "고맙다", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "손녀", "할머니 어디에요?", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "손녀", "전화번호 알려주세요!", 0, "4:01 PM")
        )
    )

    private val sonPhotoChatMission = listOf(
        listOf(
            ChatMessage("m", "나", "저번에 찾던 옷 이 옷 아니니?", 0, "4:00 PM"),
            ChatMessage("o", "아들", "사진 보내주세요", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("m", "나", "이 검정색 옷 맞니?", 0, "4:00 PM"),
            ChatMessage("o", "아들", "사진 보내주세요", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "아들", "어떤 커피 마시고 계세요?", 0, "4:01 PM")
        )
    )

    private val daughterPhotoChatMission = listOf(
        listOf(
            ChatMessage("m", "나", "이 흰색 티셔츠 맞지?", 0, "4:00 PM"),
            ChatMessage("o", "딸", "사진 보내주세요", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("m", "나", "검정색 옷 찾았는데 너 꺼 맞니?", 0, "4:00 PM"),
            ChatMessage("o", "딸", "사진 보내주세요", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "딸", "어떤 커피 마시고 계세요?", 0, "4:01 PM")
        )
    )

    private val husbandPhotoChatMission = listOf(
        listOf(
            ChatMessage("m", "나", "찾으시는 옷이 이 흰색 옷 맞으세요?", 0, "4:00 PM"),
            ChatMessage("o", "남편", "사진 보내주세요", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("m", "나", "저번에 찾던 검정색 티셔츠 찾았어요", 0, "4:00 PM"),
            ChatMessage("o", "남편", "사진 보내주세요", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "남편", "어디에요?", 0, "4:01 PM")
        )
    )

    private val grandDaughterPhotoChatMission = listOf(
        listOf(
            ChatMessage("m", "나", "저번에 집에 흰색 옷 두고 갔다", 0, "4:00 PM"),
            ChatMessage("o", "손녀", "사진 보내주세요!!", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("m", "나", "검정색 옷 좋아하니? 사줄까?", 0, "4:00 PM"),
            ChatMessage("o", "손녀", "사진 보내주세요 할머니!", 0, "4:01 PM")
        ),
        listOf(
            ChatMessage("o", "손녀", "할머니 커피 좋아하세요?", 0, "4:01 PM")
        )
    )

    private val photoList = listOf(
        R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3
    )

    fun getSimpleChat(
        person: String,
        problemNumber: Int
    ): List<ChatMessage> {
        when (person) {
            "아들" -> {
                return sonSimpleChatMission[problemNumber]
            }

            "딸" -> {
                return daughterSimpleChatMission[problemNumber]
            }

            "손녀" -> {
                return grandDaughterSimpleChatMission[problemNumber]
            }

            "남편" -> {
                return husbandSimpleChatMission[problemNumber]
            }

            else -> {
                return sonSimpleChatMission[problemNumber]
            }
        }
    }

    fun getPhotoSend(
        person: String,
        problemNumber: Int
    ): List<ChatMessage> {
        when (person) {
            "아들" -> {
                return sonPhotoChatMission[problemNumber]
            }

            "딸" -> {
                return daughterPhotoChatMission[problemNumber]
            }

            "손녀" -> {
                return grandDaughterPhotoChatMission[problemNumber]
            }

            "남편" -> {
                return husbandPhotoChatMission[problemNumber]
            }

            else -> {
                return sonSimpleChatMission[problemNumber]
            }
        }
    }

    fun getPhotoList(): List<Int> {
        return photoList
    }
}

data class ChatMessage(
    val who: String,
    val name: String,
    val content: String,
    val photoId: Int,
    val timestamp: String,
)
