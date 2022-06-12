package com.ilein.thecocktailapp.ui.command

import androidx.lifecycle.MutableLiveData
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class CommandsLiveData<T> : MutableLiveData<Queue<T>>() {

    fun onNext(value: T) {
        val commands = getValue() ?: ConcurrentLinkedQueue<T>()
        commands.add(value)
        setValue(commands)
    }
}
