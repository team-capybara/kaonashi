package ui.friend

import moime.shared.generated.resources.Res
import moime.shared.generated.resources.etc
import moime.shared.generated.resources.facebook
import moime.shared.generated.resources.img_facebook
import moime.shared.generated.resources.img_instagram
import moime.shared.generated.resources.img_kakaotalk
import moime.shared.generated.resources.img_share_etc
import moime.shared.generated.resources.instagram
import moime.shared.generated.resources.kakaotalk
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class InvitationType(
    val imageResource: DrawableResource,
    val stringResource: StringResource
) {
    Instagram(
        Res.drawable.img_instagram,
        Res.string.instagram
    ),
    Facebook(
        Res.drawable.img_facebook,
        Res.string.facebook
    ),
    Kakaotalk(
        Res.drawable.img_kakaotalk,
        Res.string.kakaotalk
    ),
    Etc(
        Res.drawable.img_share_etc,
        Res.string.etc
    )
}
