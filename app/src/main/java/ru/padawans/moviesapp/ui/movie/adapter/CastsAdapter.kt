package ru.padawans.moviesapp.ui.movie.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.credit_item.view.*
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.data.model.movie.cast.CastItem
import ru.padawans.moviesapp.data.model.movie.cast.CastsModel
import ru.padawans.moviesapp.data.model.movie.cast.CrewItem

class CastsAdapter : RecyclerView.Adapter<CastsAdapter.CastsViewHolder>() {


    private val casts = mutableListOf<CastAdapterItems>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastsViewHolder {
        return CastsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CastsViewHolder, position: Int) {
        val item = casts[position]

        when {
            item is CastAdapterItems.CastAdapterItem -> holder.bindCast(item.castItem)
            item is CastAdapterItems.CrewAdapterItem -> holder.bindCrew(item.crewItem)
        }
    }

    override fun getItemCount(): Int {
        return casts.size
    }

    fun addData(castsModel: CastsModel) {
        val data = mutableListOf<CastAdapterItems>()
        castsModel.casts.forEach {
            data.add(CastAdapterItems.CastAdapterItem(it))
        }
        castsModel.crews.forEach {
            data.add(CastAdapterItems.CrewAdapterItem(it))
        }

        casts.addAll(data)
        notifyDataSetChanged()
    }

    fun removeAll(){
        casts.clear()
        notifyDataSetChanged()
    }

    class CastsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindCast(castItem: CastItem) {
            itemView.creditName.text = castItem.name
            itemView.character.text = itemView.resources.getString(R.string.character)
            itemView.creditCharacter.text = castItem.character
            itemView.genderImg.setImageDrawable(getGenderImg(castItem.gender, itemView))
            val avatarPath = castItem.profilePath
            insertProfileImg(avatarPath)
        }

        fun bindCrew(crewItem: CrewItem) {
            itemView.creditName.text = crewItem.name
            itemView.character.text = itemView.resources.getString(R.string.job)
            itemView.creditCharacter.text = crewItem.job
            itemView.genderImg.setImageDrawable(getGenderImg(crewItem.gender, itemView))
            val avatarPath = crewItem.profilePath
            insertProfileImg(avatarPath)
        }

        private fun insertProfileImg(avatarPath: String) {
            if (avatarPath.isNotEmpty() && avatarPath != "") {
                itemView.creditAvatar.visibility = VISIBLE
                Picasso.get()
                    .load(avatarPath)
                    .placeholder(R.drawable.ic_block)
                    .error(R.drawable.ic_block)
                    .centerCrop()
                    .resize(700, 700)
                    .transform(
                        RoundedCornersTransformation(
                            15,
                            0,
                            RoundedCornersTransformation.CornerType.TOP
                        )
                    )
                    .into(itemView.creditAvatar)
            } else {
                itemView.creditAvatar.visibility = GONE
            }
        }

        private fun getGenderImg(gender: Int, view: View): Drawable {
            val theme = view.context.theme
            if (gender == 1) {
                return view.resources.getDrawable(R.drawable.ic_girl_24_accent, theme)
            } else if (gender == 2) {
                return view.resources.getDrawable(R.drawable.ic_man_24_accent, theme)
            } else {
                return view.resources.getDrawable(R.drawable.ic_toilet_24_accent, theme)
                // it's a trap
            }
        }

        companion object {
            fun create(parent: ViewGroup): CastsViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.credit_item, parent, false)

                return CastsViewHolder(view)
            }
        }
    }

    sealed class CastAdapterItems {

        data class CrewAdapterItem(
            val crewItem: CrewItem
        ) : CastAdapterItems()

        class CastAdapterItem(
            val castItem: CastItem
        ) : CastAdapterItems()
    }
}