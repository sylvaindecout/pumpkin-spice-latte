package io.shodo.pumpkin.monolith.catalogue.infra.driven.database

import io.shodo.pumpkin.monolith.TestApp
import io.shodo.pumpkin.monolith.catalogue.domain.Drink
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.joda.money.CurrencyUnit.EUR
import org.joda.money.Money
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.AutoConfigureJooq
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD
import org.testcontainers.containers.PostgreSQLContainer

@Tag("Integration")
@AutoConfigureJooq
@SpringBootTest(classes = [TestApp::class], properties = ["application.ordering.infra.catalogue.base-url=-"])
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = ["/data/init_catalogue.sql"])
@Sql(executionPhase = AFTER_TEST_METHOD, scripts = ["/data/clean_catalogue.sql"])
class JdbcDrinkRepositoryTest {

    companion object {
        private val DB by lazy { PostgreSQLContainer("postgres:15.3") }

        @JvmStatic
        @DynamicPropertySource
        fun registerDbProperties(registry: DynamicPropertyRegistry) {
            DB.start()
            registry.apply {
                with(DB) {
                    add("spring.datasource.url") { jdbcUrl }
                    add("spring.datasource.username") { username }
                    add("spring.datasource.password") { password }
                }
            }
        }
    }

    @Autowired
    private lateinit var repository: JdbcDrinkRepository

    @Test
    fun should_find_all_drinks() {
        val drink = repository.findAll()

        assertThat(drink).containsExactly(
            Drink(
                name = DrinkName("Latte"),
                unitPrice = Money.of(EUR, 5.00),
                recipe = Recipe.from(
                    Ingredient("Coffee beans") to grams(7),
                    Ingredient("Milk") to centiliters(5)
                )
            ), Drink(
                name = DrinkName("Espresso"),
                unitPrice = Money.of(EUR, 3.00),
                recipe = Recipe.from(
                    Ingredient("Coffee beans") to grams(10),
                )
            )
        )
    }

    @Test
    fun should_find_existing_drink() {
        val name = DrinkName("Latte")

        val drink = repository.find(name)

        assertThat(drink).isEqualTo(
            Drink(
                name = DrinkName("Latte"),
                unitPrice = Money.of(EUR, 5.00),
                recipe = Recipe.from(
                    Ingredient("Coffee beans") to grams(7),
                    Ingredient("Milk") to centiliters(5)
                )
            )
        )
    }

    @Test
    fun should_fail_to_find_unknown_drink() {
        val name = DrinkName("unknown")

        val drink = repository.find(name)

        assertThat(drink).isNull()
    }

    @Test
    fun should_update_existing_ingredient() {
        val name = DrinkName("Latte")
        val drink = Drink(
            name = name,
            unitPrice = Money.of(EUR, 5.00),
            recipe = Recipe.from(
                Ingredient("Coffee beans") to grams(7),
                Ingredient("Milk") to centiliters(5)
            )
        )

        repository.upsert(drink)

        assertThat(repository.find(name)).isEqualTo(drink)
    }

    @Test
    fun should_add_unknown_ingredient() {
        val name = DrinkName("unknown")
        val drink = Drink(
            name = name,
            unitPrice = Money.of(EUR, 5.00),
            recipe = Recipe.from(
                Ingredient("Marshmallow") to Quantity.pieces(3)
            )
        )

        repository.upsert(drink)

        assertThat(repository.find(name)).isEqualTo(drink)
    }

}
