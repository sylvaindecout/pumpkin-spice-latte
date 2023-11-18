package io.shodo.pumpkin.monolith.ordering.domain

class StockAccessFailureException(cause: String) : RuntimeException("Stock access failed - $cause")
