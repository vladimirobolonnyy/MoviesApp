package ru.padawans.database.model.main

import androidx.room.*
import ru.padawans.database.Converters
import ru.padawans.database.TableNames
import ru.padawans.domain.model.main.MovieGeneralInfo




@Entity(
    tableName = TableNames.GENERAL_INFO
)
@TypeConverters(Converters::class)
data class MovieGeneralInfoEntity(

    @ColumnInfo(name = "popularity")
    val popularity: Double,
    @ColumnInfo(name = "voteCount")
    val voteCount: Int,
    @ColumnInfo(name = "video")
    val video: Boolean,
    @ColumnInfo(name = "posterPath")
    val posterPath: String,
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val id: Int,
    @ColumnInfo(name = "adult")
    val adult: Boolean,
    @ColumnInfo(name = "backdropPath")
    val backdropPath: String,
    @ColumnInfo(name = "originalLanguage")
    val originalLanguage: String,
    @ColumnInfo(name = "originalTitle")
    val originalTitle: String,

    val genreIds: List<Int>,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,
    ) {

    constructor(movieGeneralInfo: MovieGeneralInfo):this(
        movieGeneralInfo.popularity,
        movieGeneralInfo.voteCount,
        movieGeneralInfo.video,
        movieGeneralInfo.posterPath,
        movieGeneralInfo.id,
        movieGeneralInfo.adult,
        movieGeneralInfo.backdropPath,
        movieGeneralInfo.originalLanguage,
        movieGeneralInfo.originalTitle,
        movieGeneralInfo.genreIds,
        movieGeneralInfo.title,
        movieGeneralInfo.voteAverage,
        movieGeneralInfo.overview,
        movieGeneralInfo.releaseDate
    )

    fun convert(): MovieGeneralInfo {
        return MovieGeneralInfo(
            popularity,
            voteCount,
            video,
            posterPath,
            id,
            adult,
            backdropPath,
            originalLanguage,
            originalTitle,
            genreIds,
            title,
            voteAverage,
            overview,
            releaseDate
        )
    }


}