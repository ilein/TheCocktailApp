package com.ilein.thecocktailapp.ui.utils

import androidx.recyclerview.widget.DiffUtil
import com.ilein.thecocktailapp.ui.state.DrinkData

class DiffCallback(private val oldList: List<DrinkData>, private val newList: List<DrinkData>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].drink.id == newList[newItemPosition].drink.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}