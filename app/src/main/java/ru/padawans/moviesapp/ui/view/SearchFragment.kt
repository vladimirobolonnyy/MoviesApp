package ru.padawans.moviesapp.ui.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.ui.adapter.SearchMoviesAdapter
import ru.padawans.moviesapp.ui.viewmodel.SearchFragmentViewModel

class SearchFragment : Fragment(R.layout.fragment_search), SearchView.OnQueryTextListener {
    //searchView
    lateinit var searchView: SearchView

    //viewModel
    private val searchViewModel: SearchFragmentViewModel by lazy {
        ViewModelProvider(this).get(SearchFragmentViewModel::class.java)
    }

    //RecyclerView
    lateinit var adapter: SearchMoviesAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        adapter = SearchMoviesAdapter()
        recyclerView = search_films_rv
        viewManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = adapter
        searchViewModel.liveData.observe(this, {
            if (it.isEmpty()) {
                error_tv.visibility = View.VISIBLE
            } else {
                error_tv.visibility = View.INVISIBLE
            }
            val list = it
            adapter.setList(list)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchViewModel.movieListSearch(newText!!)
        return false
    }
}
