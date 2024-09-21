package ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import moime.shared.generated.resources.Res
import moime.shared.generated.resources.ppobjectsans_bold
import moime.shared.generated.resources.ppobjectsans_regular
import moime.shared.generated.resources.pretendard_black
import moime.shared.generated.resources.pretendard_bold
import moime.shared.generated.resources.pretendard_extrabold
import moime.shared.generated.resources.pretendard_extralight
import moime.shared.generated.resources.pretendard_light
import moime.shared.generated.resources.pretendard_medium
import moime.shared.generated.resources.pretendard_regular
import moime.shared.generated.resources.pretendard_semibold
import moime.shared.generated.resources.pretendard_thin
import org.jetbrains.compose.resources.Font

@Composable
fun PretendardFontFamily() = FontFamily(
    Font(Res.font.pretendard_black, weight = FontWeight.Black),
    Font(Res.font.pretendard_extrabold, weight = FontWeight.ExtraBold),
    Font(Res.font.pretendard_bold, weight = FontWeight.Bold),
    Font(Res.font.pretendard_semibold, weight = FontWeight.SemiBold),
    Font(Res.font.pretendard_medium, weight = FontWeight.Medium),
    Font(Res.font.pretendard_regular, weight = FontWeight.Normal),
    Font(Res.font.pretendard_light, weight = FontWeight.Light),
    Font(Res.font.pretendard_extralight, weight = FontWeight.ExtraLight),
    Font(Res.font.pretendard_thin, weight = FontWeight.Thin),
)

@Composable
fun PPObjectSansFontFamily() = FontFamily(
    Font(Res.font.ppobjectsans_regular, weight = FontWeight.Normal),
    Font(Res.font.ppobjectsans_bold, weight = FontWeight.Bold)
)

@Composable
fun MoimeTypography() = Typography().run {
    val fontFamily = PretendardFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}
