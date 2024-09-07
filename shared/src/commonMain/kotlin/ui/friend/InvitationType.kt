package ui.friend

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import team.capybara.moime.SharedRes

enum class InvitationType(
    val imageResource: ImageResource,
    val stringResource: StringResource
) {
    Instagram(
        SharedRes.images.img_instagram,
        SharedRes.strings.instagram
    ),
    Facebook(
        SharedRes.images.img_facebook,
        SharedRes.strings.facebook
    ),
    Kakaotalk(
        SharedRes.images.img_kakaotalk,
        SharedRes.strings.kakaotalk
    ),
    Etc(
        SharedRes.images.img_share_etc,
        SharedRes.strings.etc
    )
}
