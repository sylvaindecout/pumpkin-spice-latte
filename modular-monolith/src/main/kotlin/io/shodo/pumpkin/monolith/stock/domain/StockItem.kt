package io.shodo.pumpkin.monolith.stock.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity

@DomainDrivenDesign.AggregateRoot
data class StockItem(
    @DomainDrivenDesign.Entity.Id val ingredient: Ingredient,
    val currentQuantity: Quantity
)
