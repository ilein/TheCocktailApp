package com.ilein.thecocktailapp.di

import com.ilein.thecocktailapp.data.DrinkRepositoryImpl
import com.ilein.thecocktailapp.data.db.DrinkFavoriteDao
import com.ilein.thecocktailapp.data.db.DrinkFavoriteDb
import com.ilein.thecocktailapp.data.db.LocalDataSource
import com.ilein.thecocktailapp.data.db.LocalDataSourceImpl
import com.ilein.thecocktailapp.data.network.ClientProvider
import com.ilein.thecocktailapp.data.network.DrinksReq
import com.ilein.thecocktailapp.data.network.NetworkUtil
import com.ilein.thecocktailapp.data.network.RemoteDataSource
import com.ilein.thecocktailapp.data.network.RemoteDataSourceImpl
import com.ilein.thecocktailapp.domain.DrinkRepository
import com.ilein.thecocktailapp.domain.usecase.DeleteDrinkUseCase
import com.ilein.thecocktailapp.domain.usecase.GetDrinkFavoriteUseCase
import com.ilein.thecocktailapp.domain.usecase.GetDrinkFavoritesUseCase
import com.ilein.thecocktailapp.domain.usecase.GetDrinkUseCase
import com.ilein.thecocktailapp.domain.usecase.GetOneDrinkUseCase
import com.ilein.thecocktailapp.domain.usecase.InsertDrinkUseCase
import com.ilein.thecocktailapp.domain.usecase.SearchDrinkUseCase
import com.ilein.thecocktailapp.presentation.ui.detail.DrinkItemDetailViewModel
import com.ilein.thecocktailapp.presentation.ui.detail.DrinkLikeItemDetailViewModel
import com.ilein.thecocktailapp.presentation.ui.liked.LikedDrinksViewModel
import com.ilein.thecocktailapp.presentation.ui.search.SearchDrinksViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single { DrinkFavoriteDb.createDatabaseInstance(context = androidContext()) }

    single { get<DrinkFavoriteDb>().drinkFavoriteDao() }
    single<LocalDataSource> { LocalDataSourceImpl(get(), Dispatchers.IO) }

    single { ClientProvider() }
    single<RemoteDataSource> { RemoteDataSourceImpl(get(), Dispatchers.IO) }

    single <DrinkRepository> { DrinkRepositoryImpl(get() as LocalDataSource, get() as RemoteDataSource) }
}

val useCaseModule = module {
    single { InsertDrinkUseCase(get() as DrinkRepository, Dispatchers.IO) }
    single { DeleteDrinkUseCase(get() as DrinkRepository, Dispatchers.IO) }
    single { SearchDrinkUseCase(get() as DrinkRepository, Dispatchers.IO) }
    single { GetDrinkFavoritesUseCase(get() as DrinkRepository, Dispatchers.IO) }
    single { GetDrinkFavoriteUseCase(get() as DrinkRepository, Dispatchers.IO) }
    single { GetDrinkUseCase(get() as DrinkRepository, Dispatchers.IO) }
    single { GetOneDrinkUseCase(get() as DrinkRepository, Dispatchers.IO) }

}

val networkModule = module {
    single { DrinksReq() }

    single { NetworkUtil(context = androidContext()) }

    single { get<NetworkUtil>().isOnline() }
}

val vmModule = module {
    viewModel {
        SearchDrinksViewModel(get() as SearchDrinkUseCase, get() as GetDrinkFavoritesUseCase,
            get() as InsertDrinkUseCase, get() as DeleteDrinkUseCase, get() as NetworkUtil)
    }
    viewModel {
        LikedDrinksViewModel(get() as DeleteDrinkUseCase, get() as GetDrinkFavoritesUseCase)
    }
    viewModel {
        DrinkItemDetailViewModel(get() as DrinkFavoriteDao, get() as DrinksReq, get() as NetworkUtil)
    }
    viewModel {
        DrinkLikeItemDetailViewModel(get() as InsertDrinkUseCase, get() as GetDrinkFavoriteUseCase,
            get() as GetDrinkUseCase, get() as GetOneDrinkUseCase, get() as NetworkUtil)
    }
}