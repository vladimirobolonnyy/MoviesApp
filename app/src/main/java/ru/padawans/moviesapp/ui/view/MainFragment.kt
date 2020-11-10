package ru.padawans.moviesapp.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_fragment.*
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.data.model.upcoming.MovieGeneralInfo
import ru.padawans.moviesapp.data.repository.mainFragment.ContentTypes
import ru.padawans.moviesapp.ui.ToolbarActivity
import ru.padawans.moviesapp.ui.adapter.UpcomingMoviesAdapter
import ru.padawans.moviesapp.ui.adapter.ViewPagerAdapter
import ru.padawans.moviesapp.ui.viewmodel.MainFragmentViewModel


class MainFragment : Fragment(R.layout.main_fragment) {
    private val TAG: String = "MainFragment"
    private val mUpcomingAdapter = UpcomingMoviesAdapter(::onItemClick)
    private val mNowPlayingMoviesAdapter = UpcomingMoviesAdapter(::onItemClick)
    private val mTrendingMoviesAdapter = UpcomingMoviesAdapter(::onItemClick)
    private lateinit var mMainFragmentViewModel: MainFragmentViewModel

    private var upcomingPage: Int = 1
    private var nowPlayingPage: Int = 1
    private var trendingPage: Int = 1

    var errorDialog: AlertDialog? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? ToolbarActivity)?.hideToolbar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mMainFragmentViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                MainFragmentViewModel::class.java
            )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerUpcomingMovies: RecyclerView = RecyclerView(requireContext())
        val recyclerNowPlayingMovies: RecyclerView = RecyclerView(requireContext())
        val recyclerTrendingMovies: RecyclerView = RecyclerView(requireContext())

        val recyclers: MutableList<RecyclerView> = mutableListOf()
        recyclers.add(recyclerUpcomingMovies)
        recyclers.add(recyclerNowPlayingMovies)
        recyclers.add(recyclerTrendingMovies)
        val pagerAdapter: ViewPagerAdapter = ViewPagerAdapter(recyclers)
        viewPager.adapter = pagerAdapter

        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.text = resources.getString(R.string.upcoming_movies)
        tabLayout.getTabAt(1)?.text = resources.getString(R.string.now_playing_movies)
        tabLayout.getTabAt(2)?.text = resources.getString(R.string.trending_movies)
        tabLayout.tabTextColors =
            ContextCompat.getColorStateList(requireContext(), R.color.colorText)
        tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.colorAccentText))


        recyclerUpcomingMovies.apply {
            adapter = mUpcomingAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        recyclerNowPlayingMovies.apply {
            adapter = mNowPlayingMoviesAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        recyclerTrendingMovies.apply {
            adapter = mTrendingMoviesAdapter
            layoutManager = GridLayoutManager(context, 2)
        }


        recyclerUpcomingMovies.addOnScrollListener(recyclerScrollListener(ContentTypes.UPCOMING))
        recyclerNowPlayingMovies.addOnScrollListener(recyclerScrollListener(ContentTypes.NOW_PLAYING))
        recyclerTrendingMovies.addOnScrollListener(recyclerScrollListener(ContentTypes.TRENDING))

        openMovieFragment()

        upcomingRecyclerInit()
        nowPlayingRecyclerInit()
        trendingRecyclerInit()
        updateTrendingRecycler()
        showError()
    }

    private fun upcomingRecyclerInit() {
        mMainFragmentViewModel.loadUpcomingMovies(upcomingPage)
        mMainFragmentViewModel.upcomingData.observe(
            viewLifecycleOwner,
            Observer<List<MovieGeneralInfo>> { upcomingData ->
                if (upcomingData != null) {
                    mUpcomingAdapter.addData(upcomingData)
                }
            })

        mMainFragmentViewModel.upcomingUpdateData.observe(viewLifecycleOwner,
            Observer<List<MovieGeneralInfo>> {
                mUpcomingAdapter.updateData(it)
            }
        )
    }

    private fun nowPlayingRecyclerInit() {
        mMainFragmentViewModel.loadNowPlayingMovies(nowPlayingPage)
        mMainFragmentViewModel.nowPlayingData.observe(
            viewLifecycleOwner,
            Observer<List<MovieGeneralInfo>> { nowPlayingData ->
                if (nowPlayingData != null) {
                    mNowPlayingMoviesAdapter.addData(nowPlayingData)
                }
            })
        mMainFragmentViewModel.nowPlayingUpdateData.observe(viewLifecycleOwner,
            Observer<List<MovieGeneralInfo>> {
                mUpcomingAdapter.updateData(it)
            }
        )
    }

    private fun trendingRecyclerInit() {
        mMainFragmentViewModel.loadTrendingMovies(trendingPage)
        mMainFragmentViewModel.trendingData.observe(
            viewLifecycleOwner,
            Observer<List<MovieGeneralInfo>> { trendingData ->
                if (trendingData != null) {
                    mTrendingMoviesAdapter.addData(trendingData)
                }
            })
    }

    private fun updateTrendingRecycler() {
        mMainFragmentViewModel.trendingUpdateData.observe(viewLifecycleOwner,
            Observer<List<MovieGeneralInfo>> {
                mTrendingMoviesAdapter.updateData(it)
            }
        )
    }


    private fun onItemClick(movieId: Int) {
        mMainFragmentViewModel.onItemClick(movieId)
    }

    private fun openMovieFragment() {
        mMainFragmentViewModel.moviedId.observe(viewLifecycleOwner, Observer {
            Log.d("Main", "onItemClicked: " + it)
            val args: Bundle = Bundle()
            args.putInt("movieId", it)
            this.arguments = args
        })
    }

    private fun recyclerScrollListener(contentType: String): RecyclerView.OnScrollListener {
        var loading = true;
        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleItemCount = recyclerView.layoutManager?.childCount!!
                    totalItemCount = recyclerView.layoutManager?.itemCount!!
                    pastVisiblesItems =
                        (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            loadMoreData(contentType)
                            loading = true;
                        }
                    }
                }
            }
        }
        return scrollListener
    }

    private fun loadMoreData(contentType: String){
        when (contentType) {
            "UPCOMING" -> {
                upcomingPage++
                mMainFragmentViewModel.loadUpcomingMovies(upcomingPage)
            }
            "NOWPLAYING" -> {
                nowPlayingPage++
                mMainFragmentViewModel.loadNowPlayingMovies(nowPlayingPage)
            }
            "TRENDING" -> {
                trendingPage++
                mMainFragmentViewModel.loadTrendingMovies(trendingPage)
            }
        }
    }

    private fun showError() {

        mMainFragmentViewModel.error.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "showError: ")
            if (errorDialog == null) {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setTitle("Error").setMessage(it).setPositiveButton(
                    "Ok"
                ) { alertDialog, id ->
                    errorDialog = null
                    alertDialog.cancel()
                }.setOnDismissListener {
                    errorDialog = null
                }
                errorDialog = alertDialog.create()
                errorDialog!!.show()
            }
        })
    }

}