package io.shodo.pumpkin.monolith.ordering.domain

interface CustomerOrderHandler {
    fun process(order: Order): Invoice
}
