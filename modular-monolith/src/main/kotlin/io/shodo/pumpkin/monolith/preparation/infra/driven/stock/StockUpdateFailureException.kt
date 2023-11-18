package io.shodo.pumpkin.monolith.preparation.infra.driven.stock

class StockUpdateFailureException(cause: String) : RuntimeException("Stock update failed - $cause")
