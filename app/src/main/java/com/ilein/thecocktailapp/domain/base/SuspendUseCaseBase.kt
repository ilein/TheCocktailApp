package com.ilein.thecocktailapp.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SuspendUseCaseBase<in P, out R>(private val dispatcher: CoroutineDispatcher) {

    /** Выполняет UseCase асинхронно и возвращает [Result] с типом [R] */
    suspend operator fun invoke(arg: P): Result<R> {
        return try {
            withContext(dispatcher) {
                execute(arg).let { Result.Success(it) }
            }
        } catch (throwable: Throwable) {
            Result.Error(throwable)
        }
    }

    /** Переопределить этод метод в наследнике для выполнения UseСase - а */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(arg: P): R
}