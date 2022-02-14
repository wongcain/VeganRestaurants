package cainwong.vegan.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cainwong.vegan.data.Place
import cainwong.vegan.ui.theme.dimens
import coil.annotation.ExperimentalCoilApi

@ExperimentalCoilApi
@Composable
fun SearchResultsList(
    searchState: SearchState?,
    onResultClicked: (Place) -> Unit,
) {
    if (searchState is SearchState.Success && searchState.searchResults.isNotEmpty()) {
        LazyColumn {
            items(searchState.searchResults) {
                SearchResultItem(
                    place = it,
                    onResultClicked = onResultClicked,
                )
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun SearchResultItem(
    place: Place,
    onResultClicked: (Place) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.dimens.medium,
                end = MaterialTheme.dimens.medium,
                top = MaterialTheme.dimens.small,
                bottom = MaterialTheme.dimens.small
            )
            .clickable { onResultClicked(place) },
        elevation = MaterialTheme.dimens.small
    ) {
        SearchResultContent(place)
    }
}
