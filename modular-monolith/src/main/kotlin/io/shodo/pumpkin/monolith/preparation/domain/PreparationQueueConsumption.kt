package io.shodo.pumpkin.monolith.preparation.domain

fun interface PreparationQueueConsumption {
    fun processNext()
}
