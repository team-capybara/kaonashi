package ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource
import org.jetbrains.compose.resources.imageResource
import team.capybara.moime.SharedRes
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray500

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoimeTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    imeAction: ImeAction,
    onDone: ((String) -> Unit)?,
    onSearch: ((String) -> Unit)?,
    hintTextRes: StringResource
) {
    val state = rememberTextFieldState()

    BasicTextField2(
        state = state,
        enabled = enabled,
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone?.let { it(state.text.toString()) } },
            onSearch = { onSearch?.let { it(state.text.toString()) } }
        ),
        textStyle = TextStyle.Default.copy(
            color = Gray50,
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
            fontSize = 16.sp
        ),
        cursorBrush = SolidColor(Gray50),
        decorator = { innerTextField ->
            Surface(
                color = Gray500,
                shape = RoundedCornerShape(100.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MOIME_TEXT_FIELD_HEIGHT)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (state.text.isEmpty()) {
                        Text(
                            text = stringResource(hintTextRes),
                            color = Gray50.copy(alpha = 0.3f)
                        )
                    } else {
                        innerTextField()
                    }
                    onSearch?.let {
                        FilledIconButton(
                            onClick = { it(state.text.toString()) },
                            modifier = Modifier.align(Alignment.BottomStart),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                contentColor = Gray500,
                                containerColor = Gray400
                            )
                        ) {
                            Icon(
                                imageResource(SharedRes.images.ic_search),
                                modifier = Modifier.size(24.dp),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    )
}

private val MOIME_TEXT_FIELD_HEIGHT = 56.dp