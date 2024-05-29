package app.k9mail.ui.catalog.ui.organism

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.k9mail.ui.catalog.ui.common.PagedContent
import app.k9mail.ui.catalog.ui.organism.CatalogOrganismPage.APP_BAR
import app.k9mail.ui.catalog.ui.organism.items.appBarItems
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CatalogOrganismContent(
    pages: ImmutableList<CatalogOrganismPage>,
    initialPage: CatalogOrganismPage,
    modifier: Modifier = Modifier,
) {
    PagedContent(
        pages = pages,
        initialPage = initialPage,
        modifier = modifier,
    ) {
        when (it) {
            APP_BAR -> appBarItems()
        }
    }
}
