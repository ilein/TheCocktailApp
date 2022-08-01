package com.ilein.thecocktailapp.presentation.ui.liked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilein.thecocktailapp.domain.FavoriteDrink
import com.ilein.thecocktailapp.domain.base.Result
import com.ilein.thecocktailapp.domain.usecase.DeleteDrinkUseCase
import com.ilein.thecocktailapp.domain.usecase.GetDrinkFavoritesUseCase
import com.ilein.thecocktailapp.presentation.ui.state.DrinksLikeState
import kotlinx.coroutines.launch

class LikedDrinksViewModel(private val deleteDrinkUseCase: DeleteDrinkUseCase,
                           private val getDrinkFavoritesUseCase: GetDrinkFavoritesUseCase) : ViewModel() {

    private val _liveData: MutableLiveData<DrinksLikeState> = MutableLiveData(DrinksLikeState())

    val state: LiveData<DrinksLikeState> = _liveData

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch {
            when(val result = getDrinkFavoritesUseCase.invoke("")){
                is Result.Success -> {
                    _liveData.postValue(DrinksLikeState(
                        items = result.data,
                        loading = false
                    ))
                }
                is Result.Error -> {
                    result.throwable
                }
            }
        }
    }

    /**
     * ACTIONS
     */

    fun onDelClick(drink: FavoriteDrink) {
        viewModelScope.launch {
            when(val result = deleteDrinkUseCase(drink.id)) {
                is Result.Success -> {
                    initData()
                }
                is Result.Error -> {
                    result.throwable
                }
            }
        }
    }
}