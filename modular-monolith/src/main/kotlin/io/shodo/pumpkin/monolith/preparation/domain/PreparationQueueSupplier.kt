package io.shodo.pumpkin.monolith.preparation.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign

@DomainDrivenDesign.Service
class PreparationQueueSupplier(private val queue: PreparationQueue) : PreparationQueueSupply {

    override fun add(drink: Drink) {
        queue.add(drink)
    }

}
