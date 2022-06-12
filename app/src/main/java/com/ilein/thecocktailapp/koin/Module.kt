package com.ilein.thecocktailapp.koin

import com.ilein.thecocktailapp.db.DrinkLikeDao
import com.ilein.thecocktailapp.db.DrinkLikeDb
import com.ilein.thecocktailapp.network.DrinksReq
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
}

val vmModule = module {
    viewModel {
        SearchDrinksViewModel(get() as DrinkLikeDao, get() as DrinksReq)
    }
    viewModel {
        LikedDrinksViewModel(get() as DrinkLikeDao)
    }
    viewModel {
        DrinkItemDetailViewModel(get() as DrinkLikeDao, get() as DrinksReq)
    }
    viewModel {
        DrinkLikeItemDetailViewModel(get() as DrinkLikeDao, get() as DrinksReq)
    }
}