package io.shodo.pumpkin.monolith.ordering.infra.driven.catalogue.client

import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.joda.money.CurrencyUnit.EUR
import org.joda.money.Money
import org.junit.jupiter.api.Test

class MappingTest {

    private val validCatalogueItemResponseBody =
        CatalogueItemResponseBody("XXX", MoneyField(0, "EUR", 2), mapOf("XXX" to QuantityField(1, "g")))

    @Test
    fun should_map_CatalogueItemResponseBody_to_domain_with_name() {
        val dto = validCatalogueItemResponseBody.copy(name = "LATTE")

        val domainObject = dto.toDomain()

        assertThat(domainObject.name).isEqualTo(DrinkName("LATTE"))
    }

    @Test
    fun should_map_CatalogueItemResponseBody_to_domain_with_unit_price() {
        val dto = validCatalogueItemResponseBody.copy(
            unitPrice = MoneyField(
                amountMinor = 99_99,
                currencyUnit = "EUR",
                scale = 2
            )
        )

        val domainObject = dto.toDomain()

        assertThat(domainObject.unitPrice).isEqualTo(Money.of(EUR, 99.99))
    }

    @Test
    fun should_map_CatalogueItemResponseBody_to_domain_with_recipe() {
        val dto = validCatalogueItemResponseBody.copy(
            ingredients = mapOf(
                "Coffee beans" to QuantityField(7, "g"),
                "Milk" to QuantityField(5, "cL")
            )
        )

        val domainObject = dto.toDomain()

        assertThat(domainObject.recipe).isEqualTo(
            Recipe.from(
                Ingredient("Coffee beans") to grams(7),
                Ingredient("Milk") to centiliters(5)
            )
        )
    }

}
