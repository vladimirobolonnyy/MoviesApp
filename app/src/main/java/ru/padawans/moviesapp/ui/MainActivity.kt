package ru.padawans.moviesapp.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.ui.main.view.MainFragment
import ru.padawans.moviesapp.ui.search.view.SearchFragment



class MainActivity : AppCompatActivity(), ToolbarActivity {

    private val TAG = "MainActivity"

    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavView: BottomNavigationView
    private val mainFragment = MainFragment()
    private val searchFragment = SearchFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMainFragment()
        initBottomNavView()
    }

    fun initBottomNavView() {
        bottomNavView = bottom_nav_view
        bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Main -> {
                    replaceFragment(mainFragment)
                    true
                }
                R.id.Search -> {
                    replaceFragment(searchFragment)
                    true
                }
                R.id.Account -> {
//replaceAccountFragment
                    true
                }
                else -> {
                    false
                }
            }
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is SearchFragment) {
            if (currentFragment?.isVisible == true && currentFragment !is SearchFragment) {
                if (currentFragment !is MainFragment) {
                    Log.d(TAG, "replaceFragment11: ")
                    supportFragmentManager.beginTransaction()
                        .remove(currentFragment).commit()
                }
            }

            if (currentFragment !is SearchFragment) {
                Log.d(TAG, "add search: ")
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .hide(mainFragment)
                    .commit()
            }
        } else if (fragment is MainFragment) {
            if (currentFragment?.isVisible == true && currentFragment !is MainFragment) {
                Log.d(TAG, "replaceFragment: ")
                supportFragmentManager.beginTransaction().remove(currentFragment).commit()
            }

            if (currentFragment !is MainFragment) {
                supportFragmentManager.beginTransaction()
                    .show(fragment)
                    .commit()
            }
        }
    }

    private fun initMainFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, mainFragment)
            .commit()
    }

    override fun showToolbar() {
        toolbar = main_toolBar
        toolbar.visibility = View.VISIBLE
        setSupportActionBar(toolbar)
    }

    override fun hideToolbar() {
        supportActionBar?.hide()
    }



}
