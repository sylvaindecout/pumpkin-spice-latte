package io.shodo.pumpkin.monolith.preparation.domain

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVING

@HexagonalArchitecture.Port(DRIVING)
fun interface PreparationQueueSupply {
    fun add(drink: Drink)
}
