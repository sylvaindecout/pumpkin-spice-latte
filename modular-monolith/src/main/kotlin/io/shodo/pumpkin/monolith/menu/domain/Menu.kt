package io.shodo.pumpkin.monolith.menu.domain

import io.shodo.pumpkin.monolith.shared.domain.DrinkName

interface Menu {
    fun findAll(): Collection<Drink>
    fun find(drinkName: DrinkName): Drink?
    fun upsert(drink: Drink)
}
