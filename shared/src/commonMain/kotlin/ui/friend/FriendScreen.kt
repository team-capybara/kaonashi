package ui.friend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import team.capybara.moime.SharedRes
import ui.theme.Gray50
import ui.theme.Gray700

data class FriendScreen(val myCode: String) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val density = LocalDensity.current
        val friendScreenModel = rememberScreenModel { FriendScreenModel() }
        val friendState by friendScreenModel.state.collectAsState()

        Column(
            modifier = Modifier
                .background(color = Gray700)
                .padding(top = with(density) {
                    WindowInsets.statusBars.getTop(this).toDp()
                })
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            FriendTopAppBar(
                onClose = { navigator.pop() },
                onMore = {}
            )
            FriendTitle()
            Spacer(Modifier.height(36.dp))
            FriendInvitation()
            Spacer(Modifier.height(30.dp))
            FriendSearchContent(
                myCode = myCode,
                onSearch = {}
            )
            Spacer(Modifier.height(28.dp))
            FriendTabContent(
                state = friendState,
                onSearch = {}
            )
        }
    }
}

@Composable
private fun FriendTopAppBar(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onMore: () -> Unit
) {
    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
        ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
            IconButton(onClick = onClose) {
                Icon(
                    painterResource(SharedRes.images.ic_close),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Gray50
                )
            }
        }
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
            IconButton(onClick = onMore) {
                Icon(
                    painterResource(SharedRes.images.ic_more),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Gray50
                )
            }
        }
    }
}

@Composable
private fun FriendTitle(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(SharedRes.strings.add_friend),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_bold),
            fontSize = 24.sp,
            color = Gray50
        )
        Text(
            text = stringResource(SharedRes.strings.add_friend_desc),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_medium),
            fontSize = 14.sp,
            color = Gray50
        )
    }
}

@Composable
private fun FriendInvitation(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(SharedRes.strings.invite_app),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
            fontSize = 16.sp,
            color = Gray50
        )
        Spacer(Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            InvitationType.entries.forEach {
                FriendInvitationButton(
                    imageRes = it.imageResource,
                    textRes = it.stringResource
                )
            }
        }
    }
}

@Composable
private fun FriendInvitationButton(
    modifier: Modifier = Modifier,
    imageRes: ImageResource,
    textRes: StringResource
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.then(Modifier.width(56.dp).clickable { })
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(CircleShape)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(textRes),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_medium),
            fontSize = 11.sp,
            color = Gray50
        )
    }
}
