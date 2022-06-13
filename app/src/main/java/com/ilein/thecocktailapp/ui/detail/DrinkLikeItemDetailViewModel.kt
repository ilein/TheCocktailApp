package com.ilein.thecocktailapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilein.thecocktailapp.db.DrinkLikeDao
import com.ilein.thecocktailapp.network.DrinksReq
import com.ilein.thecocktailapp.ui.state.DrinkItemLikeState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class DrinkLikeItemDetailViewModel(private val drinkLikeDao: DrinkLikeDao,
                                   private val drinksReq: DrinksReq): ViewModel() {
    val state = MutableLiveData<DrinkItemLikeState>()

    private val compositeDisposable = CompositeDisposable()

    fun init(drinkId: Int) {
        drinksReq.requestDrink(drinkId)
            .onStart { state.postValue(DrinkItemLikeState(null, null, true)) }
            .onEach { drinks ->
                run {
                    val drink = drinks.results[0]
                    drinkLikeDao.getDrinkLike(drinkId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.single())
                        .subscribe { drinkLike ->
                            state.postValue(DrinkItemLikeState(drink, drinkLike, false))
                        }

                }
            }
            .launchIn(viewModelScope)
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
            compositeDisposable.add(
                drinkLikeDao.insertDrinkLike(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe()
            )
         }
    }
}