package ui.mypage

import kotlinx.serialization.Serializable

/** Javascript Interface callback which return whether notification permission is granted. */
@Serializable
data class MyPageJsCallback(
    val granted: Boolean
)
