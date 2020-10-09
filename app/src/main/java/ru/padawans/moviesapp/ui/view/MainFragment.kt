package ru.padawans.moviesapp.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.data.model.InteractorModel
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies
import ru.padawans.moviesapp.ui.adapter.NowPlayingMoviesAdapter
import ru.padawans.moviesapp.ui.adapter.TrendingMoviesAdapter
import ru.padawans.moviesapp.ui.adapter.UpcomingMoviesAdapter
import ru.padawans.moviesapp.ui.viewmodel.MainFragmentViewModel

class MainFragment : Fragment(R.layout.main_fragment) {
    private val TAG: String = "MainFragment"
    private val mUpcomingAdapter = UpcomingMoviesAdapter()
    private val mNowPlayingMoviesAdapter = NowPlayingMoviesAdapter()
    private val mTrendingMoviesAdapter = TrendingMoviesAdapter()
    private lateinit var mMainFragmentViewModel: MainFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mMainFragmentViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                MainFragmentViewModel::class.java
            )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerUpcomingMovies.apply {
            adapter = mUpcomingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        recyclerNowPlayingMovies.apply {
            adapter = mNowPlayingMoviesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        recyclerTrendingMovies.apply {
            adapter = mTrendingMoviesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mMainFragmentViewModel.viewState.observe(this,
            Observer<InteractorModel> { r ->
                Log.d(TAG, "onViewCreated: ")
                if (r != null) {
                    mUpcomingAdapter.addData(r.upcomingMovies.results)
                    mNowPlayingMoviesAdapter.addData(r.nowPlayingMovies.results)
                    mTrendingMoviesAdapter.addData(r.trendingMovies.results)

                }
            })
        mMainFragmentViewModel.error.observe(this, Observer {
            Snackbar.make(view,it,Snackbar.LENGTH_INDEFINITE).show()
        })
    }
}