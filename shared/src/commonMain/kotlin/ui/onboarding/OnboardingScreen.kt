package ui.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import moime.shared.generated.resources.Res
import moime.shared.generated.resources.start
import org.jetbrains.compose.resources.stringResource
import ui.component.SafeAreaColumn
import ui.main.MainScreen
import ui.theme.Gray50
import ui.theme.Gray500
import ui.theme.Gray700
import ui.theme.Gray800
import ui.theme.MoimeGreen

class OnboardingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val pagerState = rememberPagerState(pageCount = { OnboardingStep.entries.size })
        SafeAreaColumn(
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Spacer(Modifier.weight(115f))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(458f).fillMaxWidth()
            ) { page ->
                OnboardingPage(step = OnboardingStep.entries[page])
            }
            Spacer(Modifier.weight(55f))
            HorizontalPagerIndicator(pagerState)
            Spacer(Modifier.weight(59f))
            Row(
                modifier = Modifier.weight(56f).fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                StartButton(
                    onClick = { navigator.replace(MainScreen()) },
                    enabled = pagerState.currentPage == OnboardingStep.entries.lastIndex,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun HorizontalPagerIndicator(
    pagerState: PagerState,
    activeColor: Color = Gray50,
    inactiveColor: Color = Gray500,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.then(Modifier.fillMaxWidth()),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        OnboardingStep.entries.forEach { step ->
            val animatedColor = animateColorAsState(
                if (pagerState.currentPage == step.ordinal) activeColor else inactiveColor
            )
            Surface(
                modifier = Modifier.size(4.dp),
                color = animatedColor.value,
                shape = CircleShape
            ) { }
        }
    }
}

@Composable
private fun StartButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedContainerColor = animateColorAsState(
        if (enabled) MoimeGreen else Gray800
    )
    val animatedContentColor = animateColorAsState(
        if (enabled) Gray700 else Gray500
    )
    Box(
        modifier = modifier.then(
            Modifier
                .background(
                    color = animatedContainerColor.value,
                    shape = RoundedCornerShape(40.dp)
                )
                .clip(RoundedCornerShape(40.dp))
        ).then(if (enabled) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(Res.string.start),
            color = animatedContentColor.value,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}
