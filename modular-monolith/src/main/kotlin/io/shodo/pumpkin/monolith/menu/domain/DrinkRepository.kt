package io.shodo.pumpkin.monolith.menu.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign
import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVEN
import io.shodo.pumpkin.monolith.shared.domain.DrinkName

@HexagonalArchitecture.Port(DRIVEN)
@DomainDrivenDesign.Repository(Drink::class)
interface DrinkRepository {
    fun findAll(): Collection<Drink>
    fun find(name: DrinkName): Drink?
    fun upsert(drink: Drink)
}
