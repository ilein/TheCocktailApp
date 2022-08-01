package com.ilein.thecocktailapp.presentation.ui.search

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilein.thecocktailapp.data.network.NetworkUtil
import com.ilein.thecocktailapp.domain.Drink
import com.ilein.thecocktailapp.domain.FavoriteDrink
import com.ilein.thecocktailapp.domain.base.Result
import com.ilein.thecocktailapp.domain.usecase.DeleteDrinkUseCase
import com.ilein.thecocktailapp.domain.usecase.GetDrinkFavoritesUseCase
import com.ilein.thecocktailapp.domain.usecase.InsertDrinkUseCase
import com.ilein.thecocktailapp.domain.usecase.SearchDrinkUseCase
import com.ilein.thecocktailapp.presentation.ui.state.DrinkData
import com.ilein.thecocktailapp.presentation.ui.state.DrinksSearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Collections.emptyList

@OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
class SearchDrinksViewModel(private val searchDrinksUseCase: SearchDrinkUseCase,
                            private val getDrinkFavoritesUseCase: GetDrinkFavoritesUseCase,
                            private val insertDrinkUseCase: InsertDrinkUseCase,
                            private val deleteDrinkUseCase: DeleteDrinkUseCase,
                            private val networkUtils: NetworkUtil) : ViewModel() {

    private val _liveData: MutableLiveData<DrinksSearchState> = MutableLiveData(DrinksSearchState())

    val state: LiveData<DrinksSearchState> = _liveData

    private val sharedFlow = MutableSharedFlow<String>(1, 1)

    private val drinkLikes = mutableListOf<Int>()

    init {
        init()
    }

    private fun init() {
        viewModelScope.launch {
            when (val result = getDrinkFavoritesUseCase("")) {
                is Result.Success -> {
                    result.data.forEach {
                        if (it.id !in drinkLikes) {
                            drinkLikes.add(it.id)
                        }
                    }
                }
                is Result.Error -> {
                    result.throwable
                }
            }
        }

        viewModelScope.launch {
            sharedFlow
                .filter { it.isNotBlank() }
                .debounce(500)
                .flatMapLatest { query ->
                    if (query.isBlank()) flowOf(emptyList()) else
                        if (networkUtils.isOnline()) {

                            when (val result = searchDrinksUseCase(query)) {
                                is Result.Success -> {
                                    result.data
                                        .onStart {
                                            updateState {
                                                it.copy(loading = true)
                                            }
                                        }
                                        .onEmpty {
                                            updateState {
                                                it.copy(loading = false)
                                            }
                                        }
                                        .onCompletion {
                                            updateState {
                                                it.copy(loading = false)
                                            }
                                        }
                                    return@flatMapLatest result.data
                                }
                                is Result.Error -> {
                                    result.throwable
                                    flowOf(emptyList())
                                }
                            }
                        } else {
                            updateState {
                                it.copy(loading = false, isOnline = false)
                            }
                            flowOf(emptyList())
                        }
                }
                .flowOn(Dispatchers.IO)
                .onEach(::onResponse)
                .launchIn(viewModelScope)
        }
    }


    private fun updateState(update: (DrinksSearchState) -> DrinksSearchState) {
        Handler(Looper.getMainLooper()).post {
            _liveData.value?.also {
                _liveData.value = update(it)
            }
        }
    }

    private fun onResponse(drinks: List<Drink>) {
        updateState { it ->
            it.copy(
                itemsMap = drinks.associate {
                    it.id to DrinkData(
                        it,
                        drinkLikes.contains(it.id)
                    )
                },
                loading = false
            )
        }
    }

    /**
     * ACTIONS
     */

    fun onTextInput(text: String) {
        viewModelScope.launch {
            sharedFlow.emit(text)
        }
    }

    fun onRefresh() {
        init()
    }


    fun onLikeClick(drink: Drink, isLike: Boolean) {
        if (!isLike) {
            viewModelScope.launch {
                when(val result = deleteDrinkUseCase(drink.id)) {
                    is Result.Success -> {
                        drinkLikes.remove(drink.id)
                        updateState {
                            val copyVal = _liveData.value?.copy()
                            if (copyVal != null) {
                                copyVal.itemsMap[drink.id]?.like = false
                            }
                            return@updateState copyVal!!
                        }
                    }
                    is Result.Error -> {
                        result.throwable
                    }
                }
            }
        } else {
            viewModelScope.launch {
                val favoriteDrink = FavoriteDrink(
                    id = drink.id,
                    name = drink.name,
                    image = drink.image,
                    createDate = LocalDateTime.now(),
                    note = null
                )
                when(val result = insertDrinkUseCase(favoriteDrink)) {
                    is Result.Success -> {
                        if (drink.id !in drinkLikes) {drinkLikes.add(drink.id)}
                        updateState {
                            val copyVal = _liveData.value?.copy()
                            if (copyVal != null) {
                                copyVal.itemsMap[drink.id]?.like = true
                            }
                            return@updateState copyVal!!
                        }
                    }
                    is Result.Error -> {
                        result.throwable
                    }
                }
            }
        }
    }
}