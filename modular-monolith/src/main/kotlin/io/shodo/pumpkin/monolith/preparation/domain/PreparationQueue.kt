package io.shodo.pumpkin.monolith.preparation.domain

interface PreparationQueue {

    val content: List<Drink>

    fun add(drink: Drink)

    fun takeNext(): Drink?

}
