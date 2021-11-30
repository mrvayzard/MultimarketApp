package com.vayzard.feature.enrollment.domain.validator

import com.vayzard.feature.enrollment.domain.exception.FirstNameException

interface FirstNameValidator {
  fun validate(value: String): Exception?
}

class FirstNameValidatorImpl : FirstNameValidator {
  override fun validate(value: String): Exception? = when {
    value.isBlank() || value.any(Char::isDigit) -> FirstNameException()
    else -> null
  }
}
