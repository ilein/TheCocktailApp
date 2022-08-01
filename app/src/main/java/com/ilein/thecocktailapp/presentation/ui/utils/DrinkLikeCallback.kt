package com.ilein.thecocktailapp.presentation.ui.utils

import androidx.recyclerview.widget.DiffUtil
import com.ilein.thecocktailapp.domain.FavoriteDrink

class DiffLikeCallback(private val oldList: List<FavoriteDrink>, private val newList: List<FavoriteDrink>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}