package app.k9mail.core.ui.compose.designsystem.atom

import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import app.k9mail.core.ui.compose.theme.Icons
import app.k9mail.core.ui.compose.theme.PreviewWithThemes
import androidx.compose.material.Icon as MaterialIcon

@Composable
fun Icon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
) {
    MaterialIcon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = modifier,
        tint = tint,
    )
}

@Preview(showBackground = true)
@Composable
internal fun IconPreview() {
    PreviewWithThemes {
        Icon(
            imageVector = Icons.Filled.error,
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun IconTintedPreview() {
    PreviewWithThemes {
        Icon(
            imageVector = Icons.Filled.error,
            tint = Color.Magenta,
        )
    }
}
