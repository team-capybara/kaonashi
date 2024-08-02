package ui.model

import kotlinx.datetime.LocalDateTime


val dummyUsers = listOf(
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

val dummyMeetings = listOf(
    Meeting(
        1,
        "반포 한강 공원 따릉이 종주",
        LocalDateTime.parse("2024-07-03T00:27:10"),
        "반포 한강 공원",
        listOf(dummyUsers[0]),
        "https://plus.unsplash.com/premium_photo-1658526960888-3e3e62cd19de?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Meeting(
        2,
        "에버랜드 번개 팟",
        LocalDateTime.parse("2024-07-21T21:26:00"),
        "용인 에버랜드",
        listOf(dummyUsers[0], dummyUsers[1]),
        "https://plus.unsplash.com/premium_photo-1658526960888-3e3e62cd19de?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Meeting(
        3,
        "호남 향우회 술 라쓰고",
        LocalDateTime.parse("2024-07-03T18:04:24"),
        "을지로 3가",
        listOf(dummyUsers[0], dummyUsers[1], dummyUsers[2]),
        "https://plus.unsplash.com/premium_photo-1658526960888-3e3e62cd19de?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Meeting(
        4,
        "카피바라 판교 모각작",
        LocalDateTime.parse("2024-06-01T00:00:03"),
        "판교 카페",
        listOf(dummyUsers[0], dummyUsers[1], dummyUsers[2], dummyUsers[0], dummyUsers[1]),
        "https://media.licdn.com/dms/image/D5616AQGVkDHfR6oI-A/profile-displaybackgroundimage-shrink_350_1400/0/1710403112580?e=1724284800&v=beta&t=ehi-uOQBnIEAo0AD0nslywSRmw7DC42Ss36rFwax_2w"
    ),
    Meeting(
        5,
        "카리나랑 성수동 데이트",
        LocalDateTime.parse("2024-08-02T01:05:00"),
        "성수동",
        listOf(dummyUsers[0], dummyUsers[1], dummyUsers[2], dummyUsers[0]),
        "https://media.licdn.com/dms/image/D5616AQGVkDHfR6oI-A/profile-displaybackgroundimage-shrink_350_1400/0/1710403112580?e=1724284800&v=beta&t=ehi-uOQBnIEAo0AD0nslywSRmw7DC42Ss36rFwax_2w"

    ),
    Meeting(
        1,
        "반포 한강 공원 따릉이 종주",
        LocalDateTime.parse("2024-07-10T22:18:44"),
        "반포 한강 공원",
        listOf(dummyUsers[0]),
        "https://media.licdn.com/dms/image/D5616AQGVkDHfR6oI-A/profile-displaybackgroundimage-shrink_350_1400/0/1710403112580?e=1724284800&v=beta&t=ehi-uOQBnIEAo0AD0nslywSRmw7DC42Ss36rFwax_2w"
    ),
    Meeting(
        2,
        "에버랜드 번개 팟",
        LocalDateTime.parse("2024-05-01T06:03:21"),
        "용인 에버랜드",
        listOf(dummyUsers[0], dummyUsers[1])
    ),
    Meeting(
        3,
        "호남 향우회 술 라쓰고",
        LocalDateTime.parse("2024-02-23T16:04:24"),
        "을지로 3가",
        listOf(dummyUsers[0], dummyUsers[1], dummyUsers[2])
    ),
    Meeting(
        4,
        "카피바라 판교 모각작",
        LocalDateTime.parse("2024-04-01T00:00:03"),
        "판교 카페",
        listOf(dummyUsers[0], dummyUsers[1], dummyUsers[2], dummyUsers[0], dummyUsers[1]),
        "https://media.licdn.com/dms/image/D5616AQGVkDHfR6oI-A/profile-displaybackgroundimage-shrink_350_1400/0/1710403112580?e=1724284800&v=beta&t=ehi-uOQBnIEAo0AD0nslywSRmw7DC42Ss36rFwax_2w"
    ),
    Meeting(
        5,
        "카리나랑 성수동 데이트",
        LocalDateTime.parse("2024-12-30T12:00:03"),
        "성수동",
        listOf(dummyUsers[0], dummyUsers[1], dummyUsers[2], dummyUsers[0])
    )
)
