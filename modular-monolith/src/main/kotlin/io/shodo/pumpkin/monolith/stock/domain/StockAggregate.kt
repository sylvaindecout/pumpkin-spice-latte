package io.shodo.pumpkin.monolith.stock.domain

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity

class StockAggregate(private val repository: StockRepository) : Stock {

    override fun find(ingredient: Ingredient): StockItem? = repository.find(ingredient)

    override fun use(ingredient: Ingredient, usedQuantity: Quantity) {
        repository.find(ingredient)
            ?.let { formerStockItem -> StockItem(ingredient, formerStockItem.currentQuantity - usedQuantity) }
            ?.let { if (it.currentQuantity.isZero) repository.delete(ingredient) else repository.upsert(it) }
    }

    override fun upsert(ingredient: Ingredient, currentQuantity: Quantity) {
        repository.upsert(StockItem(ingredient, currentQuantity))
    }

}
