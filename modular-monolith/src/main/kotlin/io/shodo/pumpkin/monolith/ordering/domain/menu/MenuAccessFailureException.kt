package io.shodo.pumpkin.monolith.ordering.domain.menu

class MenuAccessFailureException(cause: String) : RuntimeException("Menu access failed - $cause")
