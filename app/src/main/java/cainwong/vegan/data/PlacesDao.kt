package cainwong.vegan.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacesDao {
    @Query("SELECT * FROM Place")
    fun getAllPlaces(): Flow<List<Place>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(places: List<Place>)

    @Query("DELETE FROM Place")
    suspend fun deleteAllPlaces()

    @Query("SELECT * FROM Place WHERE id=:id ")
    suspend fun getPlace(id: String): Place
}
