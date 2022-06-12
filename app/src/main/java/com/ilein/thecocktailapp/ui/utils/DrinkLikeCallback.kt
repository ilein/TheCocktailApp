package com.ilein.thecocktailapp.ui.utils

import androidx.recyclerview.widget.DiffUtil
import com.ilein.thecocktailapp.db.DrinkLikeEntity

class DiffLikeCallback(private val oldList: List<DrinkLikeEntity>, private val newList: List<DrinkLikeEntity>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}