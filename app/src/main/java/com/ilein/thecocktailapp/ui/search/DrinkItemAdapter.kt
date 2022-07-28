package com.ilein.thecocktailapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ilein.thecocktailapp.R
import com.ilein.thecocktailapp.domain.model.Drink
import com.ilein.thecocktailapp.ui.state.DrinkData
import com.ilein.thecocktailapp.ui.utils.DiffCallback

internal class DrinkItemAdapter(private val onItemClick: (Drink) -> Unit,
                                private val onLikeClick: (Drink, Boolean) -> Unit) :
    RecyclerView.Adapter<DrinkItemAdapter.MyViewHolder>() {

    private val items: MutableList<DrinkData> = mutableListOf()

    fun setItems(newItems: Map<Int, DrinkData>) {
        DiffUtil.calculateDiff(DiffCallback(items, newItems.values.toList())).also { result ->
            items.clear()
            items.addAll(newItems.values.toList())
            result.dispatchUpdatesTo(this)
        }
    }


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.itemTitle)
        private val image: ImageView = view.findViewById(R.id.image)
        private val like: CheckBox = view.findViewById(R.id.cbLike)
        fun onBind(item: DrinkData, onItemClick: (Drink) -> Unit, onLikeClick: (Drink, Boolean) -> Unit) {
            itemView.setOnClickListener { onItemClick(item.drink) }
            like.setOnCheckedChangeListener { _, isChecked ->
                onLikeClick(item.drink, isChecked)
            }
            title.text = item.drink.name
            like.isChecked = item.like ?: false
            image.load("${item.drink.image}/preview") {
                transformations(RoundedCornersTransformation(16f))
            }
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_drink, parent, false)
        return MyViewHolder(view)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.onBind(items[position], onItemClick, onLikeClick)
    }

    override fun getItemCount(): Int = items.size
}