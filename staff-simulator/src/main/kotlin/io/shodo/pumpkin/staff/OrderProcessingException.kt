package io.shodo.pumpkin.staff

class OrderProcessingException(cause: String) : RuntimeException("Processing next order failed - $cause")
