package io.shodo.pumpkin.monolith.preparation.domain

import io.shodo.pumpkin.monolith.preparation.infra.driven.queue.InMemoryPreparationQueue
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PreparationQueueConsumerTest {

    private val stockUpdates = mutableListOf<Pair<Ingredient, Quantity>>()
    private val stock: Stock = Stock { ingredient, quantity -> stockUpdates.add(ingredient to quantity) }
    private val queue: PreparationQueue = InMemoryPreparationQueue()
    private val consumer: PreparationQueueConsumer = PreparationQueueConsumer(queue, stock)

    @Test
    fun should_process_next_if_there_is_one() {
        queue.add(
            Drink(
                name = DrinkName("LATTE"),
                recipe = Recipe.from(
                    Ingredient("Coffee beans") to grams(7),
                    Ingredient("Milk") to centiliters(5)
                ),
                customer = Customer("Vincent")
            )
        )

        consumer.processNext()

        assertThat(stockUpdates).containsExactly(
            Ingredient("Coffee beans") to grams(7),
            Ingredient("Milk") to centiliters(5)
        )
    }

    @Test
    fun should_not_process_next_if_there_is_none() {
        consumer.processNext()

        assertThat(stockUpdates).isEmpty()
    }


}
