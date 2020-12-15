package ru.padawans.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.padawans.database.model.main.MovieGeneralInfoEntity
import ru.padawans.database.model.main.UpcomingMoviesEntity

@Database(
    entities = arrayOf(
        UpcomingMoviesEntity::class,
        MovieGeneralInfoEntity::class
    ), version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieForMain(): MovieDao

}