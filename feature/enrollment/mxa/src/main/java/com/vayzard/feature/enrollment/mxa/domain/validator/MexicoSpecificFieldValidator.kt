package com.vayzard.feature.enrollment.mxa.domain.validator

import com.vayzard.feature.enrollment.mxa.domain.exception.MexicoSpecificFieldException

class MexicoSpecificFieldValidator {
  fun validate(value: String): Exception? = when {
    value.isBlank() -> MexicoSpecificFieldException()
    else -> null
  }
}