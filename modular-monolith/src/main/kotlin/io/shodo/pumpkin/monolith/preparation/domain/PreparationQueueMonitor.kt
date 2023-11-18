package io.shodo.pumpkin.monolith.preparation.domain

class PreparationQueueMonitor(private val queue: PreparationQueue) : PreparationQueueMonitoring {

    override val content get(): List<Drink> = queue.content

}
