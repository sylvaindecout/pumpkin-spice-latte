package io.shodo.pumpkin.monolith.menu.domain

import io.shodo.pumpkin.monolith.shared.domain.DrinkName

interface DrinkRepository {
    fun findAll(): Collection<Drink>
    fun find(name: DrinkName): Drink?
    fun upsert(drink: Drink)
}
