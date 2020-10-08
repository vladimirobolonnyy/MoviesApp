package ru.padawans.moviesapp.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_fragment.*
import ru.padawans.moviesapp.MovieApplication
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.data.model.upcoming.Dto.UpcomingMoviesDto
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies
import ru.padawans.moviesapp.ui.adapter.UpcomingMovieAdapter
import ru.padawans.moviesapp.ui.viewmodel.MainFragmentViewModel

class MainFragment : Fragment(R.layout.main_fragment) {
    private val TAG:String = "MainFragment"
  //  private lateinit var mUpcomingRecyclerView: RecyclerView
    private  val mUpcomingAdapter = UpcomingMovieAdapter()
    private lateinit var mMainFragmentViewModel: MainFragmentViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MovieApplication.appComponent.inject(this)
        mMainFragmentViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainFragmentViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // mUpcomingRecyclerView = view.findViewById(R.id.recycler_upcoming_movies)
        recyclerUpcomingMovies.apply {
            adapter = mUpcomingAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }

        mMainFragmentViewModel.viewState.observe(this,
            Observer<UpcomingMovies> { r ->
                Log.d(TAG, "onViewCreated: ")
                if (r != null) {
                    Log.d(TAG, "onCreate: " + r.total_results)
                    mUpcomingAdapter.addData(r.results)

                }
            })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }
}