package com.ilein.thecocktailapp.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilein.thecocktailapp.data.network.NetworkUtil
import com.ilein.thecocktailapp.domain.base.Result
import com.ilein.thecocktailapp.domain.usecase.GetDrinkFavoriteUseCase
import com.ilein.thecocktailapp.domain.usecase.GetDrinkUseCase
import com.ilein.thecocktailapp.domain.usecase.InsertDrinkUseCase
import com.ilein.thecocktailapp.presentation.ui.state.DrinkItemLikeState
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DrinkLikeItemDetailViewModel(private val insertDrinkUseCase: InsertDrinkUseCase,
                                   private val getDrinkFavoriteUseCase: GetDrinkFavoriteUseCase,
                                   private val getDrinkUseCase: GetDrinkUseCase,
                                   private val networkUtil: NetworkUtil
): ViewModel() {
    val state = MutableLiveData<DrinkItemLikeState>()

    fun init(drinkId: Int) {
        if (networkUtil.isOnline()) {
            viewModelScope.launch {
                when(val result = getDrinkFavoriteUseCase(drinkId)) {
                    is Result.Success -> {
                        viewModelScope.launch {
                            when(val result2 = getDrinkUseCase(drinkId)) {
                                is Result.Success -> {
                                    result2.data.onEach {
                                        state.postValue(DrinkItemLikeState(it, result.data, false))
                                    }
                                }
                                is Result.Error -> {
                                    result2.throwable
                                }
                            }
                        }
                    }
                    is Result.Error -> {
                        result.throwable
                    }
                }
            }
        } else {
            state.postValue(DrinkItemLikeState(null, null, false, isOnline = false))
        }
    }

    fun onRefresh(drinkId: Int) {
        init(drinkId)
    }

    fun onNoteChange(note: String) {
        if (state.value!!.drinkLike?.note != note) {
            state.postValue(state.value!!.copy(
                drinkLike = state.value!!.drinkLike?.copy(note = note)
            ))
        }
    }

    fun onSaveClick() {
        state.value?.drinkLike?.let {
            viewModelScope.launch {
                when (val result = insertDrinkUseCase(it)) {
                    is Result.Success -> {

                    }
                    is Result.Error -> {
                        result.throwable
                    }
                }
            }
         }
    }
}