package io.shodo.pumpkin.monolith.menu.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign
import io.shodo.pumpkin.monolith.shared.domain.DrinkName

@DomainDrivenDesign.Service
class MenuAggregate(private val repository: DrinkRepository) : Menu {
    override fun findAll() = repository.findAll()
    override fun find(drinkName: DrinkName) = repository.find(drinkName)
    override fun upsert(drink: Drink) = repository.upsert(drink)
}
