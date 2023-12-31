package io.shodo.pumpkin.monolith.preparation.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign

@DomainDrivenDesign.Service
class PreparationQueueMonitor(private val queue: PreparationQueue) : PreparationQueueMonitoring {

    override val content get(): List<Drink> = queue.content

}
