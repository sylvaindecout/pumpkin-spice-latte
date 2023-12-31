package io.shodo.pumpkin.monolith.stock.infra.driving.rest

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.stock.domain.Stock
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stock")
@HexagonalArchitecture.LeftAdapter
class StockController(private val stock: Stock) {

    @GetMapping("/ingredients/{name}")
    fun find(@PathVariable name: String): StockItemResponseBody? = stock.find(Ingredient(name))
        ?.toDto()

    @PostMapping("/use")
    @ResponseStatus(NO_CONTENT)
    fun use(@Valid @RequestBody update: StockUpdateRequestBody) {
        stock.use(Ingredient(update.ingredient!!), update.usedQuantity!!.toDomain())
    }

    @PutMapping("/ingredients/{name}")
    @ResponseStatus(NO_CONTENT)
    fun upsert(@PathVariable name: String, @Valid @RequestBody update: StockItemUpdateRequestBody) {
        stock.upsert(Ingredient(name), update.currentQuantity!!.toDomain())
    }

}
