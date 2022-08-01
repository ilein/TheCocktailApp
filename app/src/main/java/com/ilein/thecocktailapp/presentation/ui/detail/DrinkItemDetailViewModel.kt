package com.ilein.thecocktailapp.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilein.thecocktailapp.data.db.DrinkFavoriteDao
import com.ilein.thecocktailapp.data.network.DrinksReq
import com.ilein.thecocktailapp.data.network.NetworkUtil
import com.ilein.thecocktailapp.data.toDomain
import com.ilein.thecocktailapp.presentation.ui.state.DrinkData
import com.ilein.thecocktailapp.presentation.ui.state.DrinkItemState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class DrinkItemDetailViewModel(private val drinkFavoriteDao: DrinkFavoriteDao,
                               private val drinksReq: DrinksReq,
                               private val networkUtils: NetworkUtil
): ViewModel() {

    val state = MutableLiveData<DrinkItemState>()
    fun init(drinkId: Int, isLike: Boolean) {
        if (networkUtils.isOnline()) {
            drinksReq.requestDrink(drinkId)
                .onStart { state.postValue(DrinkItemState(null, true, isOnline = true)) }
                .onEach { drinks ->
                    run {
                        val drink = drinks.results.first()
                        state.postValue(DrinkItemState(DrinkData(drink.toDomain(), isLike), false, isOnline = true))
                    }
                }
                .launchIn(viewModelScope)
        } else {
            state.postValue(DrinkItemState(null, false, isOnline = true))
        }
    }

    fun onRefresh(drinkId: Int, isLike: Boolean) {
        init(drinkId, isLike)
    }

}