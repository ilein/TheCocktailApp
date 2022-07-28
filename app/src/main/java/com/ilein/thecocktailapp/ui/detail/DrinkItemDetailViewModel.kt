package com.ilein.thecocktailapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilein.thecocktailapp.data.network.NetworkUtil
import com.ilein.thecocktailapp.domain.usecase.GetDrinkUseCase
import com.ilein.thecocktailapp.ui.state.DrinkData
import com.ilein.thecocktailapp.ui.state.DrinkItemState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class DrinkItemDetailViewModel(private val networkUtils: NetworkUtil,
                               private val getDrinkUseCase: GetDrinkUseCase
): ViewModel() {

    val state = MutableLiveData<DrinkItemState>()
    suspend fun init(drinkId: Int, isLike: Boolean) {
        if (networkUtils.isOnline()) {
            getDrinkUseCase.invoke(drinkId)
                .onStart { state.postValue(DrinkItemState(null, true, isOnline = true)) }
                .onEach { drinks ->
                    run {
                        val drink = drinks.drinks.first()
                        state.postValue(DrinkItemState(DrinkData(drink, isLike), false, isOnline = true))
                    }
                }
                .launchIn(viewModelScope)
        } else {
            state.postValue(DrinkItemState(null, false, isOnline = true))
        }
    }

    suspend fun onRefresh(drinkId: Int, isLike: Boolean) {
        init(drinkId, isLike)
    }

}