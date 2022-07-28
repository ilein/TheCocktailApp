package com.ilein.thecocktailapp.data.koin

import com.ilein.thecocktailapp.data.db.DrinkLikeDao
import com.ilein.thecocktailapp.data.db.DrinkLikeDb
import com.ilein.thecocktailapp.data.network.DrinksReq
import com.ilein.thecocktailapp.data.network.NetworkUtil
import com.ilein.thecocktailapp.domain.usecase.GetDrinkUseCase
import com.ilein.thecocktailapp.domain.usecase.GetDrinksUseCase
import com.ilein.thecocktailapp.ui.detail.DrinkItemDetailViewModel
import com.ilein.thecocktailapp.ui.detail.DrinkLikeItemDetailViewModel
import com.ilein.thecocktailapp.ui.liked.LikedDrinksViewModel
import com.ilein.thecocktailapp.ui.search.SearchDrinksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single { DrinkLikeDb.createDatabaseInstance(context = androidContext()) }

    single { get<DrinkLikeDb>().drinkLikeDao() }
}

val networkModule = module {
    single { DrinksReq() }

    single { GetDrinksUseCase(get() as DrinksReq) }

    single { GetDrinkUseCase(get() as DrinksReq) }

    single { NetworkUtil(context = androidContext()) }

    single { get<NetworkUtil>().isOnline() }
}

val vmModule = module {
    viewModel {
        SearchDrinksViewModel(get() as DrinkLikeDao, get() as NetworkUtil, get() as GetDrinksUseCase)
    }
    viewModel {
        LikedDrinksViewModel(get() as DrinkLikeDao)
    }
    viewModel {
        DrinkItemDetailViewModel(get() as NetworkUtil, get() as GetDrinkUseCase)
    }
    viewModel {
        DrinkLikeItemDetailViewModel(get() as DrinkLikeDao, get() as NetworkUtil, get() as GetDrinkUseCase)
    }
}