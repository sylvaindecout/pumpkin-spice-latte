package io.shodo.pumpkin.monolith.ordering.infra.driven.preparation.client

import io.shodo.pumpkin.monolith.ordering.domain.Customer
import io.shodo.pumpkin.monolith.ordering.domain.preparation.Drink
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MappingTest {

    private val validDrink = Drink(DrinkName("XXX"), Recipe(mapOf(Ingredient("XXX") to grams(1))), Customer("XXX"))

    @Test
    fun should_map_Drink_to_DTO_with_name() {
        val domainObject = validDrink.copy(name = DrinkName("LATTE"))

        val dto = domainObject.toDto()

        assertThat(dto.name).isEqualTo("LATTE")
    }

    @Test
    fun should_map_Drink_to_DTO_with_ingredients() {
        val domainObject = validDrink.copy(
            recipe = Recipe.from(
                Ingredient("Coffee beans") to grams(7),
                Ingredient("Milk") to centiliters(5)
            )
        )

        val dto = domainObject.toDto()

        assertThat(dto.ingredients).isEqualTo(
            mapOf(
                "Coffee beans" to QuantityField(7_000, "mg"),
                "Milk" to QuantityField(50, "mL")
            )
        )
    }

    @Test
    fun should_map_Drink_to_DTO_with_customer() {
        val domainObject = validDrink.copy(customer = Customer("Vincent"))

        val dto = domainObject.toDto()

        assertThat(dto.customer).isEqualTo("Vincent")
    }

}
