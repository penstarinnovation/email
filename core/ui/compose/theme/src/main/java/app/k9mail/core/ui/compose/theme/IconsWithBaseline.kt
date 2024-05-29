package app.k9mail.core.ui.compose.theme

import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.unit.dp
import app.k9mail.core.ui.compose.common.image.ImageWithBaseline
import androidx.compose.material.icons.Icons as MaterialIcons

// We're using "by lazy" so not all icons are loaded into memory as soon as a nested object is accessed. But once a
// property is accessed we want to retain the `ImageWithBaseline` instance.
object IconsWithBaseline {
    object Filled {
        val warning: ImageWithBaseline by lazy {
            ImageWithBaseline(image = MaterialIcons.Filled.Warning, baseline = 21.dp)
        }
    }
}
