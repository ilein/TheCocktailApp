package com.ilein.thecocktailapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable

@Dao
abstract class DrinkLikeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDrinkLike(drinkLike: DrinkLikeEntity): Completable

    @Query("SELECT * FROM drink_like")
    abstract fun observeDrinkLike(): Observable<List<DrinkLikeEntity>>

    @Query("SELECT * FROM drink_like order by update_date desc")
    abstract fun observeDrinkLikeDesc(): Observable<List<DrinkLikeEntity>>

    @Query("SELECT * FROM drink_like WHERE id = :drinkId")
    abstract fun getDrinkLike(drinkId: Int): Maybe<DrinkLikeEntity>

    @Query("DELETE FROM drink_like WHERE id = :drinkId")
    abstract fun deleteDrinkLike(drinkId: Int): Completable
}