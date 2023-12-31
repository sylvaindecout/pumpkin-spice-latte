package io.shodo.pumpkin.monolith.ordering.domain.menu

import io.shodo.pumpkin.annotations.DomainDrivenDesign
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.joda.money.Money

@DomainDrivenDesign.Entity
data class MenuItem(
    @DomainDrivenDesign.Entity.Id val name: DrinkName,
    val unitPrice: Money,
    val recipe: Recipe
)
