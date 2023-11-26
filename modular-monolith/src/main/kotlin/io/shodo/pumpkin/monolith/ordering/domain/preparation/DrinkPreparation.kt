package io.shodo.pumpkin.monolith.ordering.domain.preparation

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVEN

@HexagonalArchitecture.Port(DRIVEN)
fun interface DrinkPreparation {
    fun prepare(drink: Drink)
}
