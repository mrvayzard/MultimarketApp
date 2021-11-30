package com.vayzard.feature.enrollment.domain.validator

import com.vayzard.feature.enrollment.domain.exception.LastNameException

interface LastNameValidator {
  fun validate(value: String): Exception?
}

class LastNameValidatorImpl : LastNameValidator {
  override fun validate(value: String): Exception? = when {
    value.isBlank() || value.any(Char::isDigit) -> LastNameException()
    else -> null
  }
}
