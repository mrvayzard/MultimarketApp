package com.vayzard.feature.enrollment.jpn.domain.validator

import com.vayzard.feature.enrollment.domain.exception.LastNameException
import com.vayzard.feature.enrollment.domain.validator.LastNameValidator

class LastNameValidatorJpn : LastNameValidator {
  override fun validate(value: String): Exception? = when {
    !JapanSymbolsValidator.isValid(value) -> LastNameException()
    else -> null
  }
}
