package io.shodo.pumpkin.monolith.preparation.domain

class PreparationQueueSupplier(private val queue: PreparationQueue) : PreparationQueueSupply {

    override fun add(drink: Drink) {
        queue.add(drink)
    }

}
