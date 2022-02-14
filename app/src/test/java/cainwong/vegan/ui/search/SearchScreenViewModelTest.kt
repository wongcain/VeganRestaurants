package cainwong.vegan.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cainwong.vegan.data.Location
import cainwong.vegan.data.LocationRepository
import cainwong.vegan.data.Place
import cainwong.vegan.data.PlaceType
import cainwong.vegan.data.PlacesRepository
import cainwong.vegan.ui.search.SearchScreenViewModel.Companion.SEARCH_TEXT_DEBOUNCE_MS
import cainwong.vegan.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchScreenViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `When location fails emit Error`() {
        val vm = SearchScreenViewModel(
            locationRepository = object : LocationRepository {
                override val location: Flow<Resource<Location>>
                    get() = flowOf(Resource.Error(IllegalStateException()))
            },
            placesRepository = object : PlacesRepository {
                override fun getPlaces(
                    location: Location,
                    keyword: String?,
                    type: PlaceType
                ): Flow<Resource<List<Place>>> = flowOf(Resource.Error(IllegalStateException()))

                override suspend fun getPlace(id: String): Place {
                    TODO("Not yet implemented")
                }
            },
        )
        vm.searchScreenState.observeForever { }
        assert(vm.searchScreenState.value is SearchState.Error)
    }

    @Test
    fun `When location is loading emit Waiting`() {
        val vm = SearchScreenViewModel(
            locationRepository = object : LocationRepository {
                override val location: Flow<Resource<Location>>
                    get() = flowOf(Resource.Loading())
            },
            placesRepository = object : PlacesRepository {
                override fun getPlaces(
                    location: Location,
                    keyword: String?,
                    type: PlaceType
                ): Flow<Resource<List<Place>>> = flowOf(Resource.Error(IllegalStateException()))

                override suspend fun getPlace(id: String): Place {
                    TODO("Not yet implemented")
                }
            },
        )
        vm.searchScreenState.observeForever { }
        assert(vm.searchScreenState.value is SearchState.Waiting)
    }

    @Test
    fun `Search waits for debounce`() {
        val vm = SearchScreenViewModel(
            locationRepository = object : LocationRepository {
                override val location: Flow<Resource<Location>>
                    get() = flowOf(Resource.Success(Location(0.0, 0.0)))
            },
            placesRepository = object : PlacesRepository {
                override fun getPlaces(
                    location: Location,
                    keyword: String?,
                    type: PlaceType
                ): Flow<Resource<List<Place>>> = flowOf(Resource.Error(IllegalStateException()))

                override suspend fun getPlace(id: String): Place {
                    TODO("Not yet implemented")
                }
            },
        )
        var count = 0
        vm.searchScreenState.observeForever { count++ }
        assert(count == 0)
        dispatcher.advanceTimeBy(SEARCH_TEXT_DEBOUNCE_MS + 1)
        assert(count == 1)
        dispatcher.advanceTimeBy(SEARCH_TEXT_DEBOUNCE_MS + 1)
        assert(count == 1)
        vm.setSearchText("test1")
        vm.setSearchText("test2")
        assert(count == 1)
        dispatcher.advanceTimeBy(SEARCH_TEXT_DEBOUNCE_MS + 1)
        assert(count == 2)
    }

    @Test
    fun `When search errors emit Error`() {
        val vm = SearchScreenViewModel(
            locationRepository = object : LocationRepository {
                override val location: Flow<Resource<Location>>
                    get() = flowOf(Resource.Success(Location(0.0, 0.0)))
            },
            placesRepository = object : PlacesRepository {
                override fun getPlaces(
                    location: Location,
                    keyword: String?,
                    type: PlaceType
                ): Flow<Resource<List<Place>>> = flowOf(Resource.Error(IllegalStateException()))

                override suspend fun getPlace(id: String): Place {
                    TODO("Not yet implemented")
                }
            },
        )
        vm.searchScreenState.observeForever { }
        dispatcher.advanceTimeBy(SEARCH_TEXT_DEBOUNCE_MS + 1)
        assert(vm.searchScreenState.value is SearchState.Error)
    }

    @Test
    fun `When search is loading emit Loading`() {
        val vm = SearchScreenViewModel(
            locationRepository = object : LocationRepository {
                override val location: Flow<Resource<Location>>
                    get() = flowOf(Resource.Success(Location(0.0, 0.0)))
            },
            placesRepository = object : PlacesRepository {
                override fun getPlaces(
                    location: Location,
                    keyword: String?,
                    type: PlaceType
                ): Flow<Resource<List<Place>>> = flowOf(Resource.Loading())

                override suspend fun getPlace(id: String): Place {
                    TODO("Not yet implemented")
                }
            },
        )
        vm.searchScreenState.observeForever { }
        dispatcher.advanceTimeBy(SEARCH_TEXT_DEBOUNCE_MS + 1)
        assert(vm.searchScreenState.value is SearchState.Loading)
    }

    @Test
    fun `When search is success emit Success with data`() {
        val vm = SearchScreenViewModel(
            locationRepository = object : LocationRepository {
                override val location: Flow<Resource<Location>>
                    get() = flowOf(Resource.Success(Location(0.0, 0.0)))
            },
            placesRepository = object : PlacesRepository {
                override fun getPlaces(
                    location: Location,
                    keyword: String?,
                    type: PlaceType
                ): Flow<Resource<List<Place>>> = flowOf(
                    Resource.Success(
                        (0 until 5).map { i ->
                            Place(
                                id = "id-$i",
                                name = "name-$i",
                                lat = i.toDouble(),
                                lng = i.toDouble(),
                                photoRef = "photoRef-$i",
                                priceLevel = i,
                                rating = i.toFloat(),
                                userRatingsTotal = i,
                                address = "address=$i",
                            )
                        }
                    )
                )

                override suspend fun getPlace(id: String): Place {
                    TODO("Not yet implemented")
                }
            },
        )
        vm.searchScreenState.observeForever { }
        dispatcher.advanceTimeBy(SEARCH_TEXT_DEBOUNCE_MS + 1)
        assert(vm.searchScreenState.value is SearchState.Success)
        assert((vm.searchScreenState.value as SearchState.Success).searchResults.size == 5)
    }
}
