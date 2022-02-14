package cainwong.vegan.data

import androidx.room.withTransaction
import cainwong.vegan.api.PlacesApi
import cainwong.vegan.util.Resource
import cainwong.vegan.util.logE
import cainwong.vegan.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PlacesRepository {
    fun getPlaces(
        location: Location,
        keyword: String? = null,
        type: PlaceType = PlaceType.Restaurant,
    ): Flow<Resource<List<Place>>>

    suspend fun getPlace(id: String): Place
}

class PlacesRepositoryImpl @Inject constructor(
    private val api: PlacesApi,
    private val db: PlacesDatabase,
) : PlacesRepository {

    private var lastLocation: Location? = null
    private var lastKeyword: String? = null
    private var lastType: PlaceType? = null

    // TODO: Unit test
    override fun getPlaces(
        location: Location,
        keyword: String?,
        type: PlaceType,
    ) = networkBoundResource(
        query = {
            db.placesDao().getAllPlaces()
        },
        fetch = {
            val result = api.getPlaces(
                location = location,
                query = keyword,
                type = type,
            )
            println("API Result: $result")
            result.results
        },
        saveFetchResult = { results ->
            val places = results.map { it.toPlace() }
            println("Saving places: $places")
            db.withTransaction {
                db.placesDao().deleteAllPlaces()
                db.placesDao().insertPlaces(places)
            }
        },
        shouldFetch = {
            lastLocation != location || lastKeyword != keyword || lastType != lastType
        },
        onError = {
            logE(it) { "Error getting places." }
        }
    )

    override suspend fun getPlace(id: String): Place {
        return db.placesDao().getPlace(id)
    }
}
