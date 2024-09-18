package ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PaginationColumn(
    enablePaging: Boolean,
    onPaging: () -> Unit,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    val lastVisibleItems = state.layoutInfo.visibleItemsInfo.lastOrNull()
    LaunchedEffect(lastVisibleItems) {
        if (lastVisibleItems?.index == state.layoutInfo.totalItemsCount - 1 && enablePaging) {
            onPaging()
        }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        content = content
    )
}
