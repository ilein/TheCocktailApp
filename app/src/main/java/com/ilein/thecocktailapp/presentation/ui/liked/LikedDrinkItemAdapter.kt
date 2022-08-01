package com.ilein.thecocktailapp.presentation.ui.liked

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ilein.thecocktailapp.R
import com.ilein.thecocktailapp.domain.FavoriteDrink
import com.ilein.thecocktailapp.presentation.ui.utils.DiffLikeCallback
import java.time.format.DateTimeFormatter

internal class LikedDrinkItemAdapter (private val onItemClick: (FavoriteDrink) -> Unit,
                                      private val onDelClick: (FavoriteDrink) -> Unit) :
    RecyclerView.Adapter<LikedDrinkItemAdapter.MyViewHolder>() {

    private val items: MutableList<FavoriteDrink> = mutableListOf()

    fun setItems(newItems: List<FavoriteDrink>) {
        DiffUtil.calculateDiff(DiffLikeCallback(items, newItems)).also { result ->
            items.clear()
            items.addAll(newItems)
            result.dispatchUpdatesTo(this)
        }
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.itemTitleLiked)
        private val image: ImageView = view.findViewById(R.id.imageLiked)
        private val updateDate: TextView = view.findViewById(R.id.tvUpdateDate)
        private val del: ImageButton = view.findViewById(R.id.ibDelete)

        @SuppressLint("SetTextI18n")
        fun onBind(item: FavoriteDrink, onItemClick: (FavoriteDrink) -> Unit, onDelClick: (FavoriteDrink) -> Unit) {
            itemView.setOnClickListener { onItemClick(item) }
            del.setOnClickListener {
                onDelClick(item)
            }
            title.text = item.name
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            updateDate.text = "Added: ${item.createDate?.format(formatter) ?: ""}"
            image.load("${item.image}/preview") {
                transformations(RoundedCornersTransformation(16f))
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LikedDrinkItemAdapter.MyViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_liked_drink, parent, false)
        return MyViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(items[position], onItemClick, onDelClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}