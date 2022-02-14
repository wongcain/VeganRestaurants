package cainwong.vegan.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cainwong.vegan.R
import cainwong.vegan.data.LocationRepository
import cainwong.vegan.data.PlaceType
import cainwong.vegan.data.PlacesRepository
import cainwong.vegan.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    placesRepository: PlacesRepository,
    locationRepository: LocationRepository,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")

    fun setSearchText(text: String) {
        _searchText.value = text
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private val _searchState: Flow<SearchState> =
        locationRepository.location.flatMapLatest { location ->
            when (location) {
                is Resource.Loading -> flowOf(SearchState.Waiting())
                is Resource.Error -> flowOf(
                    SearchState.Error(
                        message = R.string.error_location,
                    )
                )
                is Resource.Success -> if (location.data == null) {
                    flowOf(
                        SearchState.Error(
                            message = R.string.error_location,
                        )
                    )
                } else {
                    _searchText.debounce(SEARCH_TEXT_DEBOUNCE_MS)
                        .distinctUntilChanged()
                        .flatMapLatest { searchQuery ->
                            placesRepository.getPlaces(
                                location = location.data,
                                keyword = searchQuery,
                                type = PlaceType.Restaurant,
                            ).map { searchResults ->
                                when (searchResults) {
                                    is Resource.Loading -> SearchState.Loading(
                                        location = location.data,
                                        searchQuery = searchQuery,
                                    )
                                    is Resource.Error -> SearchState.Error(
                                        message = R.string.error_search,
                                        location = location.data,
                                        searchQuery = searchQuery,
                                    )
                                    is Resource.Success -> SearchState.Success(
                                        location = location.data,
                                        searchQuery = searchQuery,
                                        searchResults = searchResults.data ?: emptyList()
                                    )
                                }
                            }
                        }
                }
            }
        }

    @FlowPreview
    @ExperimentalCoroutinesApi
    val searchScreenState = _searchState.asLiveData()

    companion object {
        internal const val SEARCH_TEXT_DEBOUNCE_MS = 1000L
    }
}
