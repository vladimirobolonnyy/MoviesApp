package ru.padawans.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.ui.view.MainFragment
import ru.padawans.moviesapp.ui.view.SearchFragment

class MainActivity : AppCompatActivity(),ToolbarActivity {

    private val MAIN_FRAGMENT_TAG = "mainFragment"

    lateinit var toolbar: Toolbar
    lateinit var bottomNavView: BottomNavigationView
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
        if (fragment is SearchFragment){
            val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
            if (currentFragment?.isVisible == true && currentFragment !is SearchFragment) {
                if (currentFragment !is MainFragment) {
                    Log.d("Activity", "replaceFragment11: ")
                    supportFragmentManager.beginTransaction()
                        .remove(supportFragmentManager.findFragmentById(R.id.container)!!).commit()
                }
            }

            if(currentFragment !is SearchFragment){
                Log.d("Activity", "add search: ")
                supportFragmentManager.beginTransaction()
                    .add(R.id.container,fragment)
                    .hide(mainFragment)
                    .commit()
            }


        }else if(fragment is MainFragment){
            val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
            if (currentFragment?.isVisible == true && currentFragment !is MainFragment ){
                Log.d("Activity", "replaceFragment: ")
                supportFragmentManager.beginTransaction().remove( currentFragment!! ).commit()
            }

            if(currentFragment !is MainFragment){
                supportFragmentManager.beginTransaction()
                    .show(fragment)
                    .commit()
            }

        }
    }
    private fun initMainFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.container,mainFragment)
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
