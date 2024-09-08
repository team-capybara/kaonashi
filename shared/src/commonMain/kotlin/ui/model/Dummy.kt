package ui.model

import kotlinx.datetime.LocalDateTime

val dummyFriends = List(10) {
    Friend(
        1,
        "닉네임",
        "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM",
        LocalDateTime(2024, 12, 12, 12, 1, 2)
    )
}
val dummyUsers = listOf(
    User(
        1,
        "1",
        "라이언",
        "email",
        ProviderType.Apple,
        "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM"
    ),
    User(
        2,
        "1",
        "라이언",
        "email",
        ProviderType.Apple,
        "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM"
    ),
    User(
        3,
        "1",
        "라이언",
        "email",
        ProviderType.Apple,
        "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM"
    )
)

val dummyParticipants = listOf(
    Participant(
        1,
        "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM"
    ),
    Participant(
        1,
        "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM"
    ),
    Participant(
        1,
        "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM"
    )
)

val dummyMeetings = listOf(
    Meeting(
        1,
        "반포 한강 공원 따릉이 종주",
        LocalDateTime.parse("2024-07-03T00:27:10"),
        LocalDateTime.parse("2024-07-03T00:27:10"),
        Meeting.Status.Finished,
        Location("반포 한강 공원", 0f, 0f),
        listOf(dummyParticipants[0]),
        "https://plus.unsplash.com/premium_photo-1658526960888-3e3e62cd19de?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    )
)
