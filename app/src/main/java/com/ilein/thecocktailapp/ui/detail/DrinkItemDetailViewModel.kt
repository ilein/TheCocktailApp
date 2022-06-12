package com.ilein.thecocktailapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilein.thecocktailapp.db.DrinkLikeDao
import com.ilein.thecocktailapp.network.DrinksReq
import com.ilein.thecocktailapp.ui.state.DrinkData
import com.ilein.thecocktailapp.ui.state.DrinkItemState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class DrinkItemDetailViewModel(private val drinkLikeDao: DrinkLikeDao,
                               private val drinksReq: DrinksReq
): ViewModel() {

    val state = MutableLiveData<DrinkItemState>()
    fun init(drinkId: Int, isLike: Boolean) {
        drinksReq.requestDrink(drinkId)
            .onStart { state.postValue(DrinkItemState(null, true)) }
            .onEach { drinks ->
                run {
                    val drink = drinks.results[0]
                    state.postValue(DrinkItemState(DrinkData(drink, isLike), false))
                }
            }
            .launchIn(viewModelScope)
    }

}