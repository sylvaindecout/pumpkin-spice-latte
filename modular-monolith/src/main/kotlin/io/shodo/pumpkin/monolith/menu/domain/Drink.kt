package io.shodo.pumpkin.monolith.menu.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.joda.money.Money

@DomainDrivenDesign.AggregateRoot
data class Drink(
    @DomainDrivenDesign.Entity.Id val name: DrinkName,
    val unitPrice: Money,
    val recipe: Recipe
)
