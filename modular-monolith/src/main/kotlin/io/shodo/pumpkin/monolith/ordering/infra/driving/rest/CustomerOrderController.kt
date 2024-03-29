package io.shodo.pumpkin.monolith.ordering.infra.driving.rest

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.monolith.ordering.domain.CustomerOrderHandler
import io.shodo.pumpkin.monolith.ordering.domain.StockAccessFailureException
import io.shodo.pumpkin.monolith.ordering.domain.UnavailableIngredientException
import io.shodo.pumpkin.monolith.ordering.domain.UnknownDrinkException
import io.shodo.pumpkin.monolith.ordering.domain.menu.MenuAccessFailureException
import io.shodo.pumpkin.monolith.ordering.domain.preparation.PreparationFailureException
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
@HexagonalArchitecture.LeftAdapter
class CustomerOrderController(
    private val orderHandler: CustomerOrderHandler
) {

    @PostMapping
    fun processOrder(@Valid @RequestBody order: OrderRequestBody): InvoiceResponseBody? = orderHandler
        .process(order.toDomain())
        .toDto()

    @ExceptionHandler(UnknownDrinkException::class)
    fun handleUnknownDrinkException(ex: UnknownDrinkException) = ResponseEntity(ex.message, NOT_FOUND)

    @ExceptionHandler(UnavailableIngredientException::class)
    fun handleUnavailableIngredientException(ex: UnavailableIngredientException) = ResponseEntity(ex.message, CONFLICT)

    @ExceptionHandler(MenuAccessFailureException::class)
    fun handleMenuAccessFailureException(ex: MenuAccessFailureException) = ResponseEntity(ex.message, SERVICE_UNAVAILABLE)

    @ExceptionHandler(PreparationFailureException::class)
    fun handlePreparationFailureException(ex: PreparationFailureException) = ResponseEntity(ex.message, SERVICE_UNAVAILABLE)

    @ExceptionHandler(StockAccessFailureException::class)
    fun handleStockAccessFailureException(ex: StockAccessFailureException) = ResponseEntity(ex.message, SERVICE_UNAVAILABLE)

}

