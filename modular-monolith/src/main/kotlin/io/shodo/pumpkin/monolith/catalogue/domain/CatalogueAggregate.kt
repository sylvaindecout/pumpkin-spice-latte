package io.shodo.pumpkin.monolith.catalogue.domain

import io.shodo.pumpkin.monolith.shared.domain.DrinkName

class CatalogueAggregate(private val repository: DrinkRepository) : Catalogue {
    override fun findAll() = repository.findAll()
    override fun find(drinkName: DrinkName) = repository.find(drinkName)
    override fun upsert(drink: Drink) = repository.upsert(drink)
}
