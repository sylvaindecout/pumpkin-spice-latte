package io.shodo.pumpkin.monolith.preparation.domain

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVEN

@HexagonalArchitecture.Port(DRIVEN)
interface PreparationQueue {

    val content: List<Drink>

    fun add(drink: Drink)

    fun takeNext(): Drink?

}
