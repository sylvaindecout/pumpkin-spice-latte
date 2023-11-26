package io.shodo.pumpkin.monolith.stock.infra.driven.database

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.jooq.tables.StockItem.Companion.STOCK_ITEM
import io.shodo.pumpkin.jooq.tables.records.StockItemRecord
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity
import io.shodo.pumpkin.monolith.stock.domain.StockItem
import io.shodo.pumpkin.monolith.stock.domain.StockRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Component

@Component
@HexagonalArchitecture.RightAdapter
class JdbcStockRepository(private val dsl: DSLContext) : StockRepository {

    override fun find(ingredient: Ingredient): StockItem? = dsl.select()
        .from(STOCK_ITEM)
        .where(STOCK_ITEM.INGREDIENT.eq(ingredient))
        .fetchOne { record -> (record as StockItemRecord).toDomain() }

    override fun upsert(stockItem: StockItem) {
        stockItem.toRecord().let {
            dsl.insertInto(STOCK_ITEM).set(it)
                .onDuplicateKeyUpdate().set(it)
                .execute()
        }
    }

    override fun delete(ingredient: Ingredient) {
        dsl.deleteFrom(STOCK_ITEM)
            .where(STOCK_ITEM.INGREDIENT.eq(ingredient))
            .execute()
    }

    private fun StockItemRecord.toDomain() = StockItem(
        ingredient = this.ingredient!!,
        currentQuantity = Quantity.of(this.amount!!, this.unitOfMeasure)
    )

    private fun StockItem.toRecord() = StockItemRecord(
        ingredient = this.ingredient,
        amount = this.currentQuantity.amount,
        unitOfMeasure = this.currentQuantity.unitOfMeasure
    )
}
