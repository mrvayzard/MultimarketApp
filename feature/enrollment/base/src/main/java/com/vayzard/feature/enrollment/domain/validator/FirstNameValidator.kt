package com.vayzard.feature.enrollment.domain.validator

import com.vayzard.feature.enrollment.domain.exception.FirstNameException

class FirstNameValidator {
  fun validate(value: String): Exception? = when {
    value.isBlank() || value.any(Char::isDigit) -> FirstNameException()
    else -> null
  }
}
