package com.ilein.thecocktailapp.ui.command

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> LifecycleOwner.observe(liveData: LiveData<T>, crossinline block: (T) -> Unit) {
    liveData.observe(this) { block.invoke(it) }
}

inline fun <T : Any> LifecycleOwner.observe(liveData: CommandsLiveData<T>, crossinline block: (T) -> Unit) {
    liveData.observe(this) { commands ->
        val iterator = commands.iterator()
        while (iterator.hasNext()) {
            block.invoke(iterator.next())
            iterator.remove()
        }
    }
}
