package io.shodo.pumpkin.monolith.shared.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign
import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure.*

@DomainDrivenDesign.ValueObject
sealed class Quantity {

    abstract val amount: Int
    abstract val unitOfMeasure: UnitOfMeasure?

    abstract fun isError(): Boolean

    abstract operator fun times(factor: Int): Quantity
    abstract operator fun minus(other: Quantity): Quantity
    abstract operator fun compareTo(other: Quantity): Int

    val isZero: Boolean by lazy { amount == 0 }

    companion object {

        fun error(): Quantity = Error

        fun milligrams(amountInMilligrams: Int): Quantity = Valid(amountInMilligrams, MILLIGRAMS)
        fun grams(amountInGrams: Int): Quantity = milligrams(amountInGrams * 1000)

        fun milliliters(amountInMilliliters: Int): Quantity = Valid(amountInMilliliters, MILLILITERS)
        fun centiliters(amountInCentiliters: Int): Quantity = milliliters(amountInCentiliters * 10)

        fun pieces(number: Int): Quantity = Valid(number, unitOfMeasure = null)

        fun of(amount: Int, unitOfMeasure: UnitOfMeasure?): Quantity = when (unitOfMeasure) {
            MILLILITERS -> milliliters(amount)
            CENTILITERS -> centiliters(amount)
            MILLIGRAMS -> milligrams(amount)
            GRAMS -> grams(amount)
            null -> pieces(amount)
        }

    }

    private object Error : Quantity() {
        override val amount get() = throw IllegalStateException("Invalid instance")
        override val unitOfMeasure get() = throw IllegalStateException("Invalid instance")
        override fun isError() = true
        override operator fun times(factor: Int) = Error
        override operator fun minus(other: Quantity) = Error
        override operator fun compareTo(other: Quantity): Int = throw IllegalStateException("Invalid instance")
        override fun toString() = "(error)"
    }

    private data class Valid(override val amount: Int, override val unitOfMeasure: UnitOfMeasure?) : Quantity() {

        init {
            if (amount < 0) {
                throw IllegalArgumentException("Quantity must not be negative")
            }
        }

        override fun isError() = false

        override operator fun times(factor: Int) = of(amount * factor, unitOfMeasure)

        override operator fun minus(other: Quantity) = other.takeUnless { it.isError() }
            ?.takeIf { it.unitOfMeasure == this.unitOfMeasure }
            ?.let { this.amount - it.amount }
            ?.let { if (it > 0) it else 0 }
            ?.let { of(it, this.unitOfMeasure) }
            ?: error()

        override operator fun compareTo(other: Quantity): Int = other.takeUnless { it.isError() }
            ?.takeIf { it.unitOfMeasure == this.unitOfMeasure }
            ?.let { this.amount.compareTo(it.amount) }
            ?: throw IllegalArgumentException("Operations do not apply to quantities with inconsistent units of measure (${this.unitOfMeasure} / ${other.unitOfMeasure})")

        override fun toString() = "$amount${unitOfMeasure?.abbreviation ?: " units"}"

    }

}
