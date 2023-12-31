package io.shodo.pumpkin.monolith.preparation.domain

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVING

@HexagonalArchitecture.Port(DRIVING)
interface PreparationQueueMonitoring {
    val content: List<Drink>
}
