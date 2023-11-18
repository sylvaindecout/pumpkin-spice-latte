package io.shodo.pumpkin.monolith.preparation.domain

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
