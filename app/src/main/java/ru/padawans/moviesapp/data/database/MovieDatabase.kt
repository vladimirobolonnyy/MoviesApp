package ru.padawans.moviesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.padawans.moviesapp.data.model.main.db.MovieGeneralInfoEntity
import ru.padawans.moviesapp.data.model.main.db.UpcomingMoviesEntity

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