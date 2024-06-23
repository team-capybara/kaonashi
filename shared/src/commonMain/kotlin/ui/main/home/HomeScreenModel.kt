package ui.main.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import ui.model.Meeting
import ui.model.User

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State.Loading) {

    sealed interface State {
        data object Loading : State
        data class Result(val meetings: List<Meeting>) : State
    }

    val users = listOf(
        User(
            1,
            "라이언",
            "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM"
        ),
        User(
            2,
            "네오",
            "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM"
        ),
        User(
            3,
            "어피치",
            "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM"
        )
    )

    val meetings = listOf(
        Meeting(
            1,
            "반포 한강 공원 따릉이 종주",
            LocalDateTime.parse("2024-07-25T22:18:44"),
            "반포 한강 공원",
            listOf(users[0]),
            "https://media.licdn.com/dms/image/D5616AQGVkDHfR6oI-A/profile-displaybackgroundimage-shrink_350_1400/0/1710403112580?e=1724284800&v=beta&t=ehi-uOQBnIEAo0AD0nslywSRmw7DC42Ss36rFwax_2w"
        ),
        Meeting(
            2,
            "에버랜드 번개 팟",
            LocalDateTime.parse("2024-07-01T06:03:21"),
            "용인 에버랜드",
            listOf(users[0], users[1])
        ),
        Meeting(
            3,
            "호남 향우회 술 라쓰고",
            LocalDateTime.parse("2024-06-23T16:04:24"),
            "을지로 3가",
            listOf(users[0], users[1], users[2]),
            "https://plus.unsplash.com/premium_photo-1658526960888-3e3e62cd19de?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ),
        Meeting(
            4,
            "카피바라 판교 모각작",
            LocalDateTime.parse("2024-06-01T00:00:03"),
            "판교 카페",
            listOf(users[0], users[1], users[2], users[0], users[1]),
            "https://media.licdn.com/dms/image/D5616AQGVkDHfR6oI-A/profile-displaybackgroundimage-shrink_350_1400/0/1710403112580?e=1724284800&v=beta&t=ehi-uOQBnIEAo0AD0nslywSRmw7DC42Ss36rFwax_2w"
        ),
        Meeting(
            5,
            "카리나랑 성수동 데이트",
            LocalDateTime.parse("2024-06-22T12:00:03"),
            "성수동",
            listOf(users[0], users[1], users[2], users[0])
        ),
        Meeting(
            1,
            "반포 한강 공원 따릉이 종주",
            LocalDateTime.parse("2024-05-25T22:18:44"),
            "반포 한강 공원",
            listOf(users[0]),
            "https://media.licdn.com/dms/image/D5616AQGVkDHfR6oI-A/profile-displaybackgroundimage-shrink_350_1400/0/1710403112580?e=1724284800&v=beta&t=ehi-uOQBnIEAo0AD0nslywSRmw7DC42Ss36rFwax_2w"
        ),
        Meeting(
            2,
            "에버랜드 번개 팟",
            LocalDateTime.parse("2024-05-01T06:03:21"),
            "용인 에버랜드",
            listOf(users[0], users[1])
        ),
        Meeting(
            3,
            "호남 향우회 술 라쓰고",
            LocalDateTime.parse("2024-02-23T16:04:24"),
            "을지로 3가",
            listOf(users[0], users[1], users[2])
        ),
        Meeting(
            4,
            "카피바라 판교 모각작",
            LocalDateTime.parse("2024-04-01T00:00:03"),
            "판교 카페",
            listOf(users[0], users[1], users[2], users[0], users[1]),
            "https://media.licdn.com/dms/image/D5616AQGVkDHfR6oI-A/profile-displaybackgroundimage-shrink_350_1400/0/1710403112580?e=1724284800&v=beta&t=ehi-uOQBnIEAo0AD0nslywSRmw7DC42Ss36rFwax_2w"
        ),
        Meeting(
            5,
            "카리나랑 성수동 데이트",
            LocalDateTime.parse("2024-12-22T12:00:03"),
            "성수동",
            listOf(users[0], users[1], users[2], users[0])
        )
    )

    init {
        loadMeetings()
    }

    fun loadMeetings() {
        screenModelScope.launch {
            mutableState.value = State.Loading
            delay(1000L)
            mutableState.value =
                State.Result(meetings = meetings.sortedByDescending { it.date })
        }
    }
}
