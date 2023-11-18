package io.shodo.pumpkin.monolith.preparation.infra.driving.rest

import io.shodo.pumpkin.monolith.preparation.domain.PreparationQueueConsumption
import io.shodo.pumpkin.monolith.preparation.domain.PreparationQueueMonitoring
import io.shodo.pumpkin.monolith.preparation.domain.PreparationQueueSupply
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/preparation")
class PreparationController(
    private val supplier: PreparationQueueSupply,
    private val consumer: PreparationQueueConsumption,
    private val monitor: PreparationQueueMonitoring,
) {

    @GetMapping("/drinks")
    fun getContent() = monitor.content.map { it.toDto() }

    @PostMapping("/drinks")
    @ResponseStatus(CREATED)
    fun queue(@Valid @RequestBody drink: DrinkRequestBody) = supplier.add(drink.toDomain())

    @PostMapping("/drinks/take")
    @ResponseStatus(NO_CONTENT)
    fun take() = consumer.processNext()

}
