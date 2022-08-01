package com.ilein.thecocktailapp.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCaseBase<in P, out R>(private val dispatcher: CoroutineDispatcher) {

    operator fun invoke(arg: P): Flow<Result<R>> = execute(arg)
        .catch { e -> emit(Result.Error(e)) }
        .flowOn(dispatcher)

    protected abstract fun execute(arg: P): Flow<Result<R>>
}