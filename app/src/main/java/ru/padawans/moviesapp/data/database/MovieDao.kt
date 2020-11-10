package ru.padawans.moviesapp.data.database

import androidx.room.*
import ru.padawans.moviesapp.data.model.upcoming.db.MovieGeneralInfoEntity
import ru.padawans.moviesapp.data.model.upcoming.db.UpcomingMoviesEntity

@Dao
abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovieGeneralInfo(movieGeneralInfoEntitys: List<MovieGeneralInfoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUpcomingMovies(generalInfoEntity: UpcomingMoviesEntity)

    @Query("DELETE FROM upcomingmoviesentity WHERE contentType=:contentType AND page=:page")
    abstract suspend fun clearUpcomingMovies(contentType: String, page: Int)

    @Query("DELETE FROM moviegeneralinfodb WHERE movieId=:movieId")
    abstract suspend fun clearMovieGeneralInfo(movieId:Int)

    @Query("SELECT * FROM upcomingmoviesentity,moviegeneralinfodb WHERE contentType=:contentType AND page=:page AND moviesId = movieId ")
    abstract fun getUpcomingByTypeAndPage(contentType: String, page: Int): List<MovieGeneralInfoEntity>
}
