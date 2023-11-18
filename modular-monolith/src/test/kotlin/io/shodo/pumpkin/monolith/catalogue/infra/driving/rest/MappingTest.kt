package io.shodo.pumpkin.monolith.catalogue.infra.driving.rest

import io.shodo.pumpkin.monolith.catalogue.domain.Drink
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.pieces
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.joda.money.CurrencyUnit.EUR
import org.joda.money.Money
import org.joda.money.Money.zero
import org.junit.jupiter.api.Test

class MappingTest {

    private val validDrink = Drink(DrinkName("XXX"), zero(EUR), Recipe(mapOf(Ingredient("XXX") to grams(1))))

    @Test
    fun should_map_Money_to_domain() {
        val dto = MoneyField(500, "EUR", 2)

        val domainObject = dto.toDomain()

        assertThat(domainObject).isEqualTo(Money.ofMinor(EUR, 500))
    }

    @Test
    fun should_map_recipe_to_domain() {
        val dto = mapOf(
            "Coffee beans" to QuantityField(7, "g"),
            "Milk" to QuantityField(50, "mL"),
            "Marshmallow" to QuantityField(1, null)
        )

        val domainObject = dto.toDomain()

        assertThat(domainObject).isEqualTo(
            Recipe.from(
                Ingredient("Coffee beans") to grams(7),
                Ingredient("Milk") to centiliters(5),
                Ingredient("Marshmallow") to pieces(1)
            )
        )
    }

    @Test
    fun should_map_Drink_to_DTO_with_name() {
        val domainObject = validDrink.copy(name = DrinkName("LATTE"))

        val dto = domainObject.toDto()

        assertThat(dto.name).isEqualTo("LATTE")
    }

    @Test
    fun should_map_Drink_to_DTO_with_unit_price() {
        val domainObject = validDrink.copy(unitPrice = Money.of(EUR, 99.99))

        val dto = domainObject.toDto()

        assertThat(dto.unitPrice).isEqualTo(
            MoneyField(
                amountMinor = 99_99,
                currencyUnit = "EUR",
                scale = 2
            )
        )
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

}
