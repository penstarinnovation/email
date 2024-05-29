package app.k9mail.ui.catalog.ui.molecule

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.k9mail.ui.catalog.ui.common.PagedContent
import app.k9mail.ui.catalog.ui.molecule.CatalogMoleculePage.INPUT
import app.k9mail.ui.catalog.ui.molecule.CatalogMoleculePage.STATE
import app.k9mail.ui.catalog.ui.molecule.items.inputItems
import app.k9mail.ui.catalog.ui.molecule.items.stateItems
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CatalogMoleculeContent(
    pages: ImmutableList<CatalogMoleculePage>,
    initialPage: CatalogMoleculePage,
    modifier: Modifier = Modifier,
) {
    PagedContent(
        pages = pages,
        initialPage = initialPage,
        modifier = modifier,
    ) {
        when (it) {
            INPUT -> inputItems()
            STATE -> stateItems()
        }
    }
}
