package app.k9mail.ui.catalog.ui.atom.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import app.k9mail.core.ui.compose.designsystem.atom.Icon
import app.k9mail.core.ui.compose.designsystem.atom.text.TextCaption
import app.k9mail.core.ui.compose.theme.Icons
import app.k9mail.core.ui.compose.theme.MainTheme
import app.k9mail.ui.catalog.ui.common.list.itemDefaultPadding
import app.k9mail.ui.catalog.ui.common.list.sectionHeaderItem

fun LazyGridScope.iconItems() {
    sectionHeaderItem(text = "Filled")
    getIconsFor(Icons.Filled)
    sectionHeaderItem(text = "Outlined")
    getIconsFor(Icons.Outlined)
}

private inline fun <reified T> LazyGridScope.getIconsFor(icons: T) {
    for (method in T::class.java.methods) {
        if (exclusions.contains(method.name)) {
            continue
        } else if (method.name.startsWith("get")) {
            item {
                method.isAccessible = true
                val imageVector = method.invoke(icons) as ImageVector
                IconItem(
                    name = method.name.replaceFirst("get", ""),
                    imageVector = imageVector,
                )
            }
        }
    }
}

private val exclusions = listOf("getClass")

@Composable
private fun IconItem(
    name: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .itemDefaultPadding()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MainTheme.spacings.default),
    ) {
        Row {
            Icon(
                imageVector = imageVector,
            )
            Icon(
                imageVector = imageVector,
                tint = Color.Magenta,
            )
        }
        TextCaption(text = name)
    }
}
