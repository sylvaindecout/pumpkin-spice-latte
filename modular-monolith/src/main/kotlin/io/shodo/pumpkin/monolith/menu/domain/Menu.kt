package io.shodo.pumpkin.monolith.menu.domain

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVING
import io.shodo.pumpkin.monolith.shared.domain.DrinkName

@HexagonalArchitecture.Port(DRIVING)
interface Menu {
    fun findAll(): Collection<Drink>
    fun find(drinkName: DrinkName): Drink?
    fun upsert(drink: Drink)
}
