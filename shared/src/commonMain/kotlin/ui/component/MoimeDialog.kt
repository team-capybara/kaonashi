package ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.Gray200
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray700

@Composable
fun MoimeDialog(
    request: DialogRequest,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            modifier = modifier.then(Modifier.size(WIDTH, HEIGHT)),
            shape = RoundedCornerShape(12.dp),
            color = Gray50
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = request.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Gray700
                )
                Text(
                    text = request.subtitle,
                    fontWeight = FontWeight.Normal,
                    color = Gray400,
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    request.subActionRes?.let {
                        Button(
                            onClick = { request.onSubAction?.let { it() } },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Gray700,
                                containerColor = Gray200
                            ),
                            shape = RoundedCornerShape(30.dp),
                            contentPadding = PaddingValues(vertical = 16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(it),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                        }
                        Spacer(Modifier.width(4.dp))
                    }
                    Button(
                        onClick = request.onAction,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Gray50,
                            containerColor = Gray700
                        ),
                        shape = RoundedCornerShape(30.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(request.actionRes),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

data class DialogRequest(
    val title: String,
    val subtitle: String,
    val actionRes: StringResource,
    val subActionRes: StringResource? = null,
    val onAction: () -> Unit,
    val onSubAction: (() -> Unit)? = null,
)

private val WIDTH = 314.dp
private val HEIGHT = 144.dp
