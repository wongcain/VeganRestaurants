package cainwong.vegan.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Place::class], version = 1)
abstract class PlacesDatabase : RoomDatabase() {
    abstract fun placesDao(): PlacesDao
}
