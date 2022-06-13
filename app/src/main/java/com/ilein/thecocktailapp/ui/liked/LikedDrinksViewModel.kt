package com.ilein.thecocktailapp.ui.liked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilein.thecocktailapp.db.DrinkLikeDao
import com.ilein.thecocktailapp.db.DrinkLikeEntity
import com.ilein.thecocktailapp.ui.state.DrinksLikeState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LikedDrinksViewModel(private val drinkLikeDao: DrinkLikeDao) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _liveData: MutableLiveData<DrinksLikeState> = MutableLiveData(DrinksLikeState())

    val state: LiveData<DrinksLikeState> = _liveData

    init {
        initData()
    }

    private fun initData() {
        compositeDisposable.add(
            drinkLikeDao.observeDrinkLikeDesc()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .doOnSubscribe { _liveData.postValue(DrinksLikeState(
                    loading = true
                )) }
                .subscribe { drinksList ->
                    _liveData.postValue(DrinksLikeState(
                        items = drinksList,
                        loading = false
                    ))
                }
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    /**
     * ACTIONS
     */

    fun onDelClick(drink: DrinkLikeEntity) {
        compositeDisposable.add(
            drinkLikeDao.deleteDrinkLike(drink.id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe {
                    initData()
                }
        )
    }
}