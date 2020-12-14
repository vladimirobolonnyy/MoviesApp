package ru.padawans.domain.model.movie.details

class MovieDetails(
    val originalLanguage: String,
    val imdbId: String,
    val video: Boolean,
    val title: String,
    val backdropPath: String,
    val revenue: Long,
    val genres: List<GenresItem>,
    val popularity: Double,
    val productionCountries: List<ProductionCountriesItem>,
    val id: Int,
    val voteCount: Int,
    val budget: Int,
    val overview: String,
    val originalTitle: String,
    val runtime: Int,
    val posterPath: String,
    val spokenLanguages: List<SpokenLanguagesItem>,
    val productionCompanies: List<ProductionCompaniesItem>,
    val releaseDate: String,
    val voteAverage: Double,
    val belongsToCollection: BelongsToCollection,
    val tagline: String,
    val adult: Boolean,
    val homepage: String,
    val status: String
)



