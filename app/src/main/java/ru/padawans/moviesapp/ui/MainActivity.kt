package ru.padawans.moviesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import ru.padawans.moviesapp.MovieApplication
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.ui.view.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment:MainFragment = MainFragment()
        val fragmentTransaction:FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container,mainFragment)
        fragmentTransaction.commit()
    }
}