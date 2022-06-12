package com.ilein.thecocktailapp.ui.search

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilein.thecocktailapp.db.DrinkLikeDao
import com.ilein.thecocktailapp.db.DrinkLikeEntity
import com.ilein.thecocktailapp.network.Api
import com.ilein.thecocktailapp.network.DrinksReq
import com.ilein.thecocktailapp.network.model.Drink
import com.ilein.thecocktailapp.network.model.Drinks
import com.ilein.thecocktailapp.ui.state.DrinkData
import com.ilein.thecocktailapp.ui.state.DrinksSearchState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
class SearchDrinksViewModel(private val drinkLikeDao: DrinkLikeDao,
                            private val drinksReq: DrinksReq) : ViewModel() {

    private val _liveData: MutableLiveData<DrinksSearchState> = MutableLiveData(DrinksSearchState())

    val state: LiveData<DrinksSearchState> = _liveData

    private val sharedFlow = MutableSharedFlow<String>(1, 1)

    private val drinkLikes = mutableListOf<Int>()

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(
            drinkLikeDao.observeDrinkLike()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe { drinkList ->
                    drinkList.forEach { if (it.id !in drinkLikes) {drinkLikes.add(it.id)} }
                }
        )
        sharedFlow
            .filter { it.isNotBlank() }
            .debounce(500)
            .flatMapLatest { query ->
                if (query.isBlank()) flowOf(Drinks()) else
                    drinksReq.requestDrinks(query)
                        .onStart { updateState {
                            it.copy(loading = true) } }
                        .onEmpty { updateState {
                            it.copy(loading = false) } }
                        .onCompletion { updateState {
                            it.copy(loading = false) } }
            }
            .flowOn(Dispatchers.IO)
            .onEach(::onResponse)
            .launchIn(viewModelScope)
    }


    private fun updateState(update: (DrinksSearchState) -> DrinksSearchState) {
        Handler(Looper.getMainLooper()).post {
            _liveData.value?.also {
                _liveData.value = update(it)
            }
        }
    }

    private fun onResponse(drinks: Drinks) {
        updateState { it ->
            it.copy(
                itemsMap = drinks.results.associate {
                    it.idDrink to DrinkData(
                        it,
                        drinkLikes.contains(it.idDrink)
                    )
                },
                loading = false
            )
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    /**
     * ACTIONS
     */

    fun onTextInput(text: String) {
        viewModelScope.launch {
            sharedFlow.emit(text)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onLikeClick(drinkLike: Drink, isLike: Boolean) {
        if (!isLike) {
            compositeDisposable.add(
                drinkLikeDao.deleteDrinkLike(drinkLike.idDrink)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.single())
                    .subscribe {
                        drinkLikes.remove(drinkLike.idDrink)
                        updateState {
                            val copyVal = _liveData.value?.copy()
                            if (copyVal != null) {
                                copyVal.itemsMap[drinkLike.idDrink]?.like = false
                            }
                            return@updateState copyVal!!
                        }
                    }
            )

        } else {
            compositeDisposable.add(
                drinkLikeDao.insertDrinkLike(
                    DrinkLikeEntity(
                        id = drinkLike.idDrink,
                        name = drinkLike.strDrink,
                        image = drinkLike.strDrinkThumb,
                        createDate = LocalDateTime.now(),
                        note = null
                    )
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.single())
                    .subscribe {
                        if (drinkLike.idDrink !in drinkLikes) {drinkLikes.add(drinkLike.idDrink)}
                        updateState {
                            val copyVal = _liveData.value?.copy()
                            if (copyVal != null) {
                                copyVal.itemsMap[drinkLike.idDrink]?.like = true
                            }
                            return@updateState copyVal!!
                        }
                    }
            )
        }
    }
}