package ru.padawans.features.movie.adapter


import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class MoviePagerAdapter(private val views:List<View>):PagerAdapter() {


    override fun getCount(): Int {
        return views.size
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view:View = views[position]
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}