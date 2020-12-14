package ru.padawans.moviesapp.ui.movie.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnTouchListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_casts_view.view.*
import ru.padawans.moviesapp.R
import ru.padawans.domain.model.movie.cast.CastsModel
import ru.padawans.moviesapp.ui.movie.adapter.CastsAdapter


class CastsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defAttributeSet: Int = 0
) : LinearLayout(
    context, attrs, defAttributeSet
) {
    private val TAG: String = "CastsView"

    val creditsAdapter = CastsAdapter()

    init {
        inflate(context, R.layout.movie_casts_view, this)
    }

    fun createView(castsModel: CastsModel) {
        var spinnerTouched = false
        val spinnerAdapter = ArrayAdapter.createFromResource(
            context, R.array.credit_spinner, R.layout.credites_spinner
        )

        creditsAdapter.addData(castsModel)

        credits_recycler.apply {
            adapter = creditsAdapter
            layoutManager = GridLayoutManager(context, 2)
        }


        spinnerAdapter.setDropDownViewResource(R.layout.credites_spinner_dropdown_item)
        credits_spinner.adapter = spinnerAdapter

        credits_recycler.addOnScrollListener(recyclerScrollListener(castsModel))


        //Чтобы onItemSelected вызывался только при нажатии пользователя
        credits_spinner.setOnTouchListener(OnTouchListener { v, event ->
            spinnerTouched = true
            false
        })

        credits_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (spinnerTouched){
                    if (position == 0) {
                        credits_recycler.scrollToPosition(0)
                    } else if (position==1) {
                        if (creditsAdapter.itemCount>castsModel.casts.size+4) {
                            credits_recycler.scrollToPosition(castsModel.casts.size + 4)
                        }else{
                            credits_recycler.scrollToPosition(castsModel.casts.size - 1)
                        }
                    }
                    spinnerTouched  = false
                }
            }
        }
    }

    private fun recyclerScrollListener(castsModel: CastsModel): RecyclerView.OnScrollListener {
        var pastVisibleItem: Int
        var firstVisibleItem: Int
        val castSize = castsModel.casts.size
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    pastVisibleItem =
                        (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                    if (pastVisibleItem <= castSize - 1) {
                        credits_spinner.setSelection( 0)
                    } else {
                        credits_spinner.setSelection( 1)
                    }
                } else {
                    firstVisibleItem =
                        (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem <= castSize - 1) {
                        credits_spinner.setSelection( 0)
                    } else {
                        credits_spinner.setSelection( 1)
                    }
                }
            }
        }
        return scrollListener
    }

    fun clearAdapter(){
        creditsAdapter.removeAll()
    }
}