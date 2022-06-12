package com.ilein.thecocktailapp.ui.command

sealed class NavigationCommand {

    object Back: NavigationCommand()
    data class ToDrinkLikeDetail(val drinkId: Int?): NavigationCommand()
    data class ToDrinkDetail(val drinkId: Int?): NavigationCommand()
}