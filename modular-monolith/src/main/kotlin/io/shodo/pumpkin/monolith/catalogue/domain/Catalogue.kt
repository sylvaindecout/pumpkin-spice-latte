package io.shodo.pumpkin.monolith.catalogue.domain

import io.shodo.pumpkin.monolith.shared.domain.DrinkName

interface Catalogue {
    fun findAll(): Collection<Drink>
    fun find(drinkName: DrinkName): Drink?
    fun upsert(drink: Drink)
}
