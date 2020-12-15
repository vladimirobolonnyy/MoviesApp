package ru.padawans.data.search

import androidx.paging.PagingSource
import retrofit2.HttpException
import ru.padawans.network.api.MovieApi
import ru.padawans.domain.model.search.Movie
import java.io.IOException

private const val MOVIE_SEARCH_STARTING_PAGE_INDEX = 1

class SearchMoviesPagingSource(
    private val service: MovieApi,
    private val query: String
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIE_SEARCH_STARTING_PAGE_INDEX
        return try {
            val response = service.searchMovies(query = query, page = position)
            val repos = response.convert().results
            LoadResult.Page(
                data = repos,
                prevKey = if (position == MOVIE_SEARCH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}