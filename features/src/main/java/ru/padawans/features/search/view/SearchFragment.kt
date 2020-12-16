package ru.padawans.features.search.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import ru.padawans.domain.di.MainDependencyProvider
import ru.padawans.features.R
import ru.padawans.features.ToolbarActivity
import ru.padawans.features.search.adapter.SearchMoviesAdapter
import ru.padawans.features.search.viewmodel.SearchFragmentViewModel
import ru.padawans.features.search.adapter.SearchMovieLoadStateAdapter
import ru.padawans.features.utils.viewModels

class SearchFragment : Fragment(R.layout.fragment_search), SearchView.OnQueryTextListener {
    //searchView
    lateinit var searchView: SearchView

    private val provider by lazy { (this.activity?.application as MainDependencyProvider).getDataProvider }
    private val searchViewModel: SearchFragmentViewModel by viewModels { SearchFragmentViewModel(provider) }

    private val adapter = SearchMoviesAdapter()

    private var searchJob: Job? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? ToolbarActivity)?.showToolbar()
        setHasOptionsMenu(true)
        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        initSearch()
        retry_button.setOnClickListener { adapter.retry() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, lastSearchQuery)
    }

    private fun initAdapter() {
        search_films_rv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = SearchMovieLoadStateAdapter { adapter.retry() },
            footer = SearchMovieLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            search_films_rv.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            progress_bar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            retry_button.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    context,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun initSearch() {
        lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { search_films_rv.scrollToPosition(0) }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            lastSearchQuery = query
            search(query)

        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            searchViewModel.movieListSearch(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? ToolbarActivity)?.hideToolbar()
    }

    companion object {
        private var lastSearchQuery: String = ""
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = ""
    }
}
