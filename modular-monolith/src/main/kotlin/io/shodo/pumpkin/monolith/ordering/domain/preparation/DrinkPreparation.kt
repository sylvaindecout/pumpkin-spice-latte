package io.shodo.pumpkin.monolith.ordering.domain.preparation

fun interface DrinkPreparation {
    fun prepare(drink: Drink)
}
