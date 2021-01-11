package ru.padawans.features.main.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.main_fragment.*
import ru.padawans.domain.ContentTypes
import ru.padawans.domain.di.MainDependencyProvider
import ru.padawans.features.R
import ru.padawans.features.ToolbarActivity
import ru.padawans.features.main.adapter.MainMoviesAdapter
import ru.padawans.features.main.adapter.ViewPagerAdapter
import ru.padawans.features.main.viewmodel.MainViewModel
import ru.padawans.features.movie.view.MovieFragment
import ru.padawans.features.utils.viewModels


class MainFragment : Fragment(R.layout.main_fragment), SwipeRefreshLayout.OnRefreshListener {
    private val TAG: String = "MainFragment"
    private val mUpcomingAdapter = MainMoviesAdapter(::onItemClick)
    private val mNowPlayingMoviesAdapter = MainMoviesAdapter(::onItemClick)
    private val mTrendingMoviesAdapter = MainMoviesAdapter(::onItemClick)


    private val provider by lazy { (this.activity?.application as MainDependencyProvider).getDataProvider }
    private val mMainViewModel: MainViewModel by viewModels { MainViewModel(provider) }


    var errorDialog: AlertDialog? = null

    private lateinit var swipeRefresh: SwipeRefreshLayout


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? ToolbarActivity)?.hideToolbar()
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


        swipeRefresh = refresher
        swipeRefresh.setOnRefreshListener(this)

        openMovieFragment()

        upcomingRecyclerInit()
        nowPlayingRecyclerInit()
        trendingRecyclerInit()
        showError()
        hideRefresh()
    }


    private fun upcomingRecyclerInit() {
        mMainViewModel.upcomingData.observe(
            viewLifecycleOwner,
            Observer {
                    mUpcomingAdapter.updateData(it)
            })
    }

    private fun nowPlayingRecyclerInit() {
        mMainViewModel.nowPlayingData.observe(
            viewLifecycleOwner,
            Observer {
                    mNowPlayingMoviesAdapter.updateData(it)
            })
    }

    private fun trendingRecyclerInit() {
        mMainViewModel.trendingData.observe(
            viewLifecycleOwner,
            Observer {
                    mTrendingMoviesAdapter.updateData(it)
            })
    }


    private fun onItemClick(movieId: Int) {
        mMainViewModel.onCardClick(movieId)
    }

    private fun openMovieFragment() {
        mMainViewModel.movieId.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onItemClicked: " + it)
            val current = parentFragmentManager.findFragmentById(R.id.container)
            parentFragmentManager.beginTransaction()
                .add(R.id.container, MovieFragment.newInstance(movieId = it))
                .addToBackStack(TAG)
                .hide(current!!)
                .commit()
        })
    }

    private fun recyclerScrollListener(contentType: String): RecyclerView.OnScrollListener {
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
                    mMainViewModel.loadOnScroll(
                        pastVisiblesItems,
                        visibleItemCount,
                        totalItemCount,
                        contentType
                    )
                }
            }
        }
        return scrollListener
    }

    private fun hideRefresh() {
        mMainViewModel.refreshState.observe(viewLifecycleOwner, Observer {
            swipeRefresh.post { swipeRefresh.isRefreshing = it }
        })
    }

    private fun showError() {
        mMainViewModel.error.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "showError: ")
            if (errorDialog == null) {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setTitle("Error")
                    .setMessage(resources.getString(it))
                    .setPositiveButton(
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


    override fun onRefresh() {
        mUpcomingAdapter.removeAll()
        mTrendingMoviesAdapter.removeAll()
        mNowPlayingMoviesAdapter.removeAll()
        mMainViewModel.refreshAll()
    }
}