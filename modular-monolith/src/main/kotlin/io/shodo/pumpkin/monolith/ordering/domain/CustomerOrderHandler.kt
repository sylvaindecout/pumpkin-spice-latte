package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVING

@HexagonalArchitecture.Port(DRIVING)
interface CustomerOrderHandler {
    fun process(order: Order): Invoice
}
