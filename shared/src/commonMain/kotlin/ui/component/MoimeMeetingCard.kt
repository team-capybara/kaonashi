package ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime
import moime.shared.generated.resources.Res
import moime.shared.generated.resources.from_start
import moime.shared.generated.resources.to_start
import org.jetbrains.compose.resources.stringResource
import ui.LocalScreenSize
import ui.model.Meeting
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray500
import ui.theme.Gray700
import ui.theme.Gray800
import ui.theme.MoimeGreen
import ui.theme.PPObjectSansFontFamily
import ui.util.DateUtil.getDdayString
import ui.util.DateUtil.getMonthDayString
import ui.util.DateUtil.getPeriodString
import ui.util.DateUtil.getTimeString
import ui.util.DateUtil.isNotYet
import ui.util.DateUtil.isToday

@Composable
fun MoimeMeetingCard(
    meeting: Meeting,
    onClick: () -> Unit,
    isAnotherTodayMeetingCardFocusing: Boolean,
    forceDefaultHeightStyle: Boolean = false,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current

    val isToday = meeting.startDateTime.isToday()
    var isMeetingStarted by remember { mutableStateOf(isToday && meeting.startDateTime.isNotYet()) }

    val todayTopPadding =
        with(density) {
            WindowInsets.statusBars.getTop(this).toDp()
        } + HOME_TOP_APP_BAR_HEIGHT + 40.dp
    val todayBottomPadding =
        with(density) {
            WindowInsets.navigationBars.getBottom(this).toDp()
        } + BOTTOM_NAV_BAR_HEIGHT + 42.dp
    val todayHeight =
        with(density) { LocalScreenSize.current.height.toDp() } - todayTopPadding - todayBottomPadding

    val animatedHeight = animateDpAsState(
        if (isToday && !forceDefaultHeightStyle) todayHeight else MOIME_CARD_HEIGHT,
        animationSpec = tween(durationMillis = 1000)
    )
    val animatedSubtextColor = animateColorAsState(
        if (isMeetingStarted) Gray50 else Gray400,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )

    AnimatedContent(
        targetState = isToday,
        transitionSpec = { fadeIn(tween(delayMillis = 100)).togetherWith(fadeOut()) }
    ) {
        if (it.not() && isAnotherTodayMeetingCardFocusing) {
            Box(
                modifier = modifier.then(
                    Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .height(animatedHeight.value)
                        .background(Color.Transparent)
                )
            )
        } else {
            Card(
                modifier = modifier.then(
                    Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .height(animatedHeight.value)
                ),
                onClick = onClick,
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Gray500, contentColor = Gray50)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    meeting.thumbnailUrl?.let {
                        AnimatedContent(
                            targetState = isMeetingStarted,
                            transitionSpec = {
                                fadeIn(
                                    tween(
                                        durationMillis = 1000,
                                        easing = LinearEasing
                                    )
                                ).togetherWith(
                                    fadeOut(tween(durationMillis = 1000))
                                )
                            }
                        ) { targetState ->
                            if (targetState) {
                                CoilImage(
                                    imageModel = { it },
                                    imageOptions = ImageOptions(
                                        contentScale = ContentScale.Crop,
                                        alignment = Alignment.Center
                                    ),
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                CoilImage(
                                    imageModel = { it },
                                    imageOptions = ImageOptions(
                                        contentScale = ContentScale.Crop,
                                        colorFilter = ColorFilter.colorMatrix(GrayScaleColorMatrix),
                                        alignment = Alignment.Center
                                    ),
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                    androidx.compose.animation.AnimatedVisibility(
                        visible = isMeetingStarted,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color(0x80000000),
                                            Color.Transparent
                                        )
                                    )
                                )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 15.dp, horizontal = 11.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MoimeProfileImageStack(meeting.participants.map { user -> user.profileImageUrl })
                        Column(
                            modifier = Modifier.weight(1f).height(MOIME_CARD_HEIGHT - 30.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = meeting.title,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                lineHeight = 30.sp,
                                color = Gray50,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "${meeting.startDateTime.getMonthDayString()} | ${meeting.startDateTime.getTimeString()} | ${meeting.location.name}",
                                fontWeight = FontWeight.Normal,
                                color = animatedSubtextColor.value,
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Gray700,
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 5.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = meeting.startDateTime.getDdayString(),
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = Gray50
                                )
                            }
                        }
                    }
                    Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = isToday,
                            enter = fadeIn(tween(durationMillis = 1000, delayMillis = 1000)),
                            exit = fadeOut(tween(durationMillis = 1000, delayMillis = 1000))
                        ) {
                            if (!forceDefaultHeightStyle) {
                                TimerButton(
                                    meetingDateTime = meeting.startDateTime,
                                    isMeetingStarted = isMeetingStarted,
                                    onMeetingStarted = { isMeetingStarted = true },
                                    modifier = Modifier
                                        .padding(vertical = 16.dp, horizontal = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TimerButton(
    meetingDateTime: LocalDateTime,
    isMeetingStarted: Boolean,
    onMeetingStarted: () -> Unit,
    modifier: Modifier = Modifier
) {
    var timeString: String by remember { mutableStateOf("00:00:00") }
    val animatedButtonColor = animateColorAsState(
        if (!isMeetingStarted) Gray50 else MoimeGreen,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )
    val animatedTextColor = animateColorAsState(
        if (!isMeetingStarted) Gray800 else Gray700,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )
    LaunchedEffect(Unit) {
        while (true) {
            if (!isMeetingStarted && meetingDateTime.isNotYet()) {
                onMeetingStarted()
            }
            timeString = meetingDateTime.getPeriodString()
            delay(500L)
        }
    }

    Surface(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(TIMER_BUTTON_HEIGHT)
        ),
        shape = RoundedCornerShape(40.dp),
        color = animatedButtonColor.value
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = isMeetingStarted,
                transitionSpec = { fadeIn(tween(1000)).togetherWith(fadeOut(tween(1000))) }
            ) {
                if (it) {
                    Text(
                        text = stringResource(Res.string.from_start),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = animatedTextColor.value
                    )
                } else {
                    Text(
                        text = stringResource(Res.string.to_start),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = animatedTextColor.value
                    )
                }
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = timeString,
                fontFamily = PPObjectSansFontFamily(),
                fontSize = 16.sp,
                color = animatedTextColor.value
            )
        }
    }
}

val MOIME_CARD_HEIGHT = 128.dp
private val TIMER_BUTTON_HEIGHT = 56.dp