package ru.padawans.moviesapp.ui.movie.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.movie_info_view.view.*
import kotlinx.android.synthetic.main.movie_info_view.view.release_date
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.data.model.movie.details.MovieDetails
import ru.padawans.moviesapp.data.model.movie.details.ProductionCountriesItem


class InfoView @JvmOverloads constructor(context: Context,attrs: AttributeSet? = null,
                                         defAttributeSet: Int = 0) : LinearLayout(
    context,attrs,defAttributeSet
) {
    private val TAG: String = "InfoView"

    init {
        inflate(context, R.layout.movie_info_view, this)
    }

    fun createView(movieDetails: MovieDetails) {
        title.isAllCaps = true
        title.text = movieDetails.title
        release_date.text = movieDetails.releaseDate
        country.text = getCountry(movieDetails.productionCountries)
        runtime.text = movieDetails.runtime.toString() + " " + resources.getString(R.string.min)
        language.text = movieDetails.originalLanguage

        if (movieDetails.budget == 0) {
            budget.visibility = GONE
            budget_img.visibility = GONE
        } else {
            budget.text = movieDetails.budget.toString()
        }

        if (movieDetails.tagline != "") {
            taglineTextView.text = movieDetails.tagline
        } else {
            taglineTextView.visibility = GONE
        }

        if (movieDetails.overview != "") {
            infoTextView.text = movieDetails.overview
        } else {
            infoTextView.visibility = GONE
        }

        if (movieDetails.tagline == "" && movieDetails.overview == "") {
            overviewTitle.visibility = GONE
        }

        val genres: List<String> = movieDetails.genres.map { it.name }
        if (genres.size > 0 && genres.get(0) != "") {
            genreTags.setTags(genres)

            if (genres.size == 1) {
                genreTitle.text = resources.getText(R.string.genre)
            } else {
                genreTitle.text = resources.getText(R.string.genres)
            }
        } else {
            genreTitle.visibility = GONE
            genreTags.visibility = GONE
        }
    }

    private fun getCountry(productionCountries: List<ProductionCountriesItem>): String {
        val countriesList = StringBuilder()
        productionCountries.forEach {
            if (it.countryCode != "") {
                countriesList.append(it.countryCode + " ")
            }
        }
        return countriesList.toString()
    }

}