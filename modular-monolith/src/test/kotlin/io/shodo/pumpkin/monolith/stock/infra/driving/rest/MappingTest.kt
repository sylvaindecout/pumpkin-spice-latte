package io.shodo.pumpkin.monolith.stock.infra.driving.rest

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure.GRAMS
import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure.MILLILITERS
import io.shodo.pumpkin.monolith.stock.domain.StockItem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MappingTest {

    private val validStockItem = StockItem(Ingredient("x"), Quantity.of(1, GRAMS))

    @Test
    fun should_map_QuantityField_to_domain_with_amount() {
        val dto = QuantityField(50, "g")

        val domainObject = dto.toDomain()

        assertThat(domainObject.amount).isEqualTo(50_000)
    }

    @Test
    fun should_map_QuantityField_to_domain_with_unit_of_measure() {
        val dto = QuantityField(1, "cL")

        val domainObject = dto.toDomain()

        assertThat(domainObject.unitOfMeasure).isEqualTo(MILLILITERS)
    }

    @Test
    fun should_map_QuantityField_to_domain_with_no_unit_of_measure_if_missing() {
        val dto = QuantityField(1, unitOfMeasure = null)

        val domainObject = dto.toDomain()

        assertThat(domainObject.unitOfMeasure).isNull()
    }

    @Test
    fun should_map_StockItem_to_DTO_with_ingredient() {
        val domainObject = validStockItem.copy(ingredient = Ingredient("Coffee beans"))

        val dto = domainObject.toDto()

        assertThat(dto.ingredient).isEqualTo("Coffee beans")
    }

    @Test
    fun should_map_StockItem_to_DTO_with_current_quantity() {
        val domainObject = validStockItem.copy(currentQuantity = centiliters(50))

        val dto = domainObject.toDto()

        assertThat(dto.currentQuantity).usingRecursiveComparison().isEqualTo(QuantityField(500, "mL"))
    }

}
