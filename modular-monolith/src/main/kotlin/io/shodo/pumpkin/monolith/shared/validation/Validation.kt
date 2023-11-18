package io.shodo.pumpkin.monolith.shared.validation

import jakarta.validation.ConstraintViolation

fun <T> Set<ConstraintViolation<T>>.format(): String = this
    .sortedBy { it.propertyPath.toString() }
    .joinToString(", ") { it.format() }

private fun <T> ConstraintViolation<T>.format() = "$propertyPath ($invalidValue) $message"
