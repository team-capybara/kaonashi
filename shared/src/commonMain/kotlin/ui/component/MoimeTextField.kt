package ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.rememberTextFieldState
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
import team.capybara.moime.SharedRes
import ui.theme.Gray50
import ui.theme.Gray500

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoimeTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onDone: (String) -> Unit,
    hintTextRes: StringResource
) {
    val state = rememberTextFieldState()

    BasicTextField2(
        state = state,
        enabled = enabled,
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone(state.text.toString()) }
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
                    contentAlignment = Alignment.Center
                ) {
                    if (state.text.isEmpty()) {
                        Text(
                            text = stringResource(hintTextRes),
                            color = Gray50.copy(alpha = 0.3f)
                        )
                    } else {
                        innerTextField()
                    }
                }
            }
        }
    )
}

private val MOIME_TEXT_FIELD_HEIGHT = 56.dp