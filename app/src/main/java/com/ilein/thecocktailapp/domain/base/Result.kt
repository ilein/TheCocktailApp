package com.ilein.thecocktailapp.domain.base

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
}

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

val <T> Result<T>.error: Throwable?
    get() = (this as? Result.Error)?.throwable