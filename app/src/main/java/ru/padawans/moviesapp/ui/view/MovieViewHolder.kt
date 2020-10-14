/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.padawans.moviesapp.ui.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_movie_item.view.*
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.data.model.search.Movie

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val movie_title: TextView = view.item_title
    val movie_poster: ImageView = view.item_poster
    val movie_vote: TextView = view.item_vote

    private var movie: Movie? = null

    init {
        view.setOnClickListener {
        }
    }

    fun bind(movie: Movie?) {
        if (movie != null) {
            showMovieData(movie)
        }
    }

    private fun showMovieData(movie: Movie) {
        this.movie = movie
        movie_title.text = movie.title
        movie_poster.downloadAndSetImage(movie.poster)
        movie_vote.text = movie.vote.toString()
    }

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_movie_item, parent, false)
            return MovieViewHolder(view)
        }
    }

    fun ImageView.downloadAndSetImage(path: String?) {
        val url = BuildConfig.BASE_IMG_URL + "w342" + path
        Picasso.get()
            .load(url)
            .resize(120, 180)
            .centerCrop()
            .placeholder(R.drawable.ic_block)
            .into(this)
    }

}
