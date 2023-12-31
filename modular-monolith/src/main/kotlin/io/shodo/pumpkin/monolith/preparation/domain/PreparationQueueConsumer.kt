package io.shodo.pumpkin.monolith.preparation.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign

@DomainDrivenDesign.Service
class PreparationQueueConsumer(
    private val queue: PreparationQueue,
    private val stock: Stock
) : PreparationQueueConsumption {

    override fun processNext() {
        queue.takeNext()?.let {
            it.recipe.asMap().forEach { (ingredient, quantity) ->
                stock.notifyConsumption(ingredient, quantity)
            }
        }
    }

}
