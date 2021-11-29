package com.vayzard.feature.enrollment.domain.validator

import com.vayzard.feature.enrollment.domain.exception.LastNameException

class LastNameValidator {
  fun validate(value: String): Exception? = when {
    value.isBlank() || value.any(Char::isDigit) -> LastNameException()
    else -> null
  }
}
