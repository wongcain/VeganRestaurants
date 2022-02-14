package cainwong.vegan.ui.search

import androidx.annotation.StringRes
import cainwong.vegan.data.Location
import cainwong.vegan.data.Place

sealed class SearchState(
    val location: Location? = null,
    val searchQuery: String? = null,
    val searchResults: List<Place> = emptyList(),
) {
    class Waiting : SearchState()

    class Loading(
        location: Location,
        searchQuery: String?,
    ) : SearchState(location, searchQuery)

    class Success(
        location: Location,
        searchQuery: String?,
        searchResults: List<Place>,
    ) : SearchState(location, searchQuery, searchResults)

    class Error(
        @StringRes val message: Int,
        location: Location? = null,
        searchQuery: String? = null,
        searchResults: List<Place> = emptyList(),
    ) : SearchState()
}
