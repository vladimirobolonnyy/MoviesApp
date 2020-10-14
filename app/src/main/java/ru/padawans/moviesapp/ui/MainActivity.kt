package ru.padawans.moviesapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.ui.view.MainFragment
import ru.padawans.moviesapp.ui.view.SearchFragment

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initBottomNavView()
    }

    fun initBottomNavView() {
        bottomNavView = bottom_nav_view
        bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Main -> {
                    replaceFragment(MainFragment())
                    true
                }
                R.id.Search -> {
                    replaceFragment(SearchFragment())
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

    fun initToolbar() {
        toolbar = main_toolBar
        setSupportActionBar(toolbar)
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                fragment
            ).commit()
    }
}
