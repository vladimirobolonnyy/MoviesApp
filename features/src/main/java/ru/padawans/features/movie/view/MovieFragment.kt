package ru.padawans.features.movie.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_fragment.*
import ru.padawans.domain.di.MainDependencyProvider
import ru.padawans.features.R
import ru.padawans.features.ToolbarActivity
import ru.padawans.features.main.adapter.MainMoviesAdapter
import ru.padawans.features.main.viewmodel.MainViewModel
import ru.padawans.features.movie.adapter.MoviePagerAdapter
import ru.padawans.features.movie.adapter.ReviewsAdapter
import ru.padawans.features.movie.adapter.TrailersAdapter
import ru.padawans.features.movie.viewmodel.MovieFragmentViewModel
import ru.padawans.features.utils.viewModels


class MovieFragment : Fragment(R.layout.movie_fragment) {
    private val TAG: String = "MovieFragment"
    private var movieId: Int = -1


    private lateinit var mTrailersAdapter: TrailersAdapter
    private val mReviewsAdapter = ReviewsAdapter()
    private val mSimilarAdapter = MainMoviesAdapter(::onItemClick)

    private lateinit var castsView: CastsView

    var errorDialog: AlertDialog? = null

    private val provider by lazy { (this.activity?.application as MainDependencyProvider).getDataProvider }
    private val mMovieFragmentViewModel: MovieFragmentViewModel by viewModels { MovieFragmentViewModel(provider,movieId) }


    companion object {
        private const val MOVIE_ID: String = "MovieId"
        const val REVIEWS = "REVIEWS"
        const val SIMILAR = "SIMILAR"

        fun newInstance(movieId: Int): MovieFragment {
            val fragment = MovieFragment()
            val bundle = Bundle()
            bundle.putInt(MOVIE_ID, movieId)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? ToolbarActivity)?.hideToolbar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments?.getInt(MOVIE_ID) != null) {
            movieId = arguments!!.getInt(MOVIE_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTrailersAdapter = TrailersAdapter(lifecycle)
        trailers_recycler.apply {
            adapter = mTrailersAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        val infoView = InfoView(requireContext())
        val reviewsView = RecyclerView(requireContext())
        initReviewRecycler(reviewsView)

        castsView = CastsView(requireContext())

        val similarView = RecyclerView(requireContext())
        initSimilarRecycler(similarView)

        val views: MutableList<View> = mutableListOf()
        views.add(infoView)
        views.add(reviewsView)
        views.add(castsView)
        views.add(similarView)
        viewPagerInit(views)
        tabLayoutInit()

        setInfo(infoView)
        setTrailers(trailers_recycler)
        setReviews()
        setSimilar()
        setCasts(castsView)
        openMovieFragment()
        showError()
    }

    private fun viewPagerInit(views: List<View>) {
        val pagerAdapter = MoviePagerAdapter(views)
        detailsViewPager.adapter = pagerAdapter
    }

    private fun tabLayoutInit() {
        detailsTabLayout.setupWithViewPager(detailsViewPager)
        detailsTabLayout.getTabAt(0)?.text = resources.getString(R.string.info)
        detailsTabLayout.getTabAt(1)?.text = resources.getString(R.string.reviews)
        detailsTabLayout.getTabAt(2)?.text = resources.getString(R.string.casts)
        detailsTabLayout.getTabAt(3)?.text = resources.getString(R.string.similar)
        detailsTabLayout.tabTextColors =
            ContextCompat.getColorStateList(requireContext(), R.color.colorText)
        detailsTabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.colorAccentText))
    }

    private fun setInfo(infoView: InfoView) {
        mMovieFragmentViewModel.infoData.observe(viewLifecycleOwner, Observer {
            infoView.createView(it)
        })
    }

    private fun setTrailers(recyclerTrailers: RecyclerView) {
        mMovieFragmentViewModel.trailersData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                recyclerTrailers.visibility = GONE
            }
            mTrailersAdapter.addTrailers(it)
        })
    }

    private fun initReviewRecycler(reviewsView: RecyclerView) {
        reviewsView.apply {
            adapter = mReviewsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        reviewsView.addOnScrollListener(recyclerScrollListener(REVIEWS))
    }

    private fun setReviews() {
        mMovieFragmentViewModel.reviewsData.observe(viewLifecycleOwner, Observer {
            mReviewsAdapter.addData(it)
        })
    }

    private fun initSimilarRecycler(similarView: RecyclerView) {
        similarView.apply {
            adapter = mSimilarAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
        similarView.addOnScrollListener(recyclerScrollListener(SIMILAR))
    }

    private fun setSimilar() {
        mMovieFragmentViewModel.similarData.observe(viewLifecycleOwner, Observer {
            mSimilarAdapter.updateData(it)
        })
    }

    private fun setCasts(castsView: CastsView) {
        mMovieFragmentViewModel.castData.observe(viewLifecycleOwner, Observer {
            castsView.createView(it)
        })
    }

    private fun openMovieFragment() {
        mMovieFragmentViewModel.similarMovieId.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onItemClicked: " + it)
            val current = parentFragmentManager.findFragmentById(R.id.container)
            parentFragmentManager.beginTransaction()
                .add(R.id.container, newInstance(movieId = it))
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
                    pastVisiblesItems = if (contentType == SIMILAR) {
                        (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    } else {
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    }
                    mMovieFragmentViewModel.loadOnScroll(
                        pastVisiblesItems,
                        visibleItemCount,
                        totalItemCount,
                        contentType,
                        movieId
                    )

                }
            }
        }
        return scrollListener
    }

    private fun showError() {
        mMovieFragmentViewModel.error.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "showError: ")
            if (errorDialog == null) {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setTitle("Error")
                    .setMessage(resources.getString(it))
                    .setNeutralButton("Refresh"){
                            alertDialog, id ->
                        errorDialog = null
                        onRefresh()
                        alertDialog.cancel()
                    }
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

     private fun onRefresh() {
        mReviewsAdapter.removeAll()
        mSimilarAdapter.removeAll()
        mTrailersAdapter.removeAll()
        castsView.clearAdapter()
        mMovieFragmentViewModel.refreshAll(movieId)
    }
    private fun onItemClick(movieId: Int) {
        mMovieFragmentViewModel.onSimilarItemClick(movieId)
    }
}