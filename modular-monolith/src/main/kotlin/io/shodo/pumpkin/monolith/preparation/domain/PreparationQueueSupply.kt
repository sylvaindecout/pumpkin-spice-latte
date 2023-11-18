package io.shodo.pumpkin.monolith.preparation.domain

fun interface PreparationQueueSupply {
    fun add(drink: Drink)
}
