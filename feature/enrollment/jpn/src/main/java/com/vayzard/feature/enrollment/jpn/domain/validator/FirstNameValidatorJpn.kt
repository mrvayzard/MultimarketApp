package com.vayzard.feature.enrollment.jpn.domain.validator

import com.vayzard.feature.enrollment.domain.exception.FirstNameException
import com.vayzard.feature.enrollment.domain.validator.FirstNameValidator

class FirstNameValidatorJpn : FirstNameValidator {
  override fun validate(value: String): Exception? = when {
    !JapanSymbolsValidator.isValid(value) -> FirstNameException()
    else -> null
  }
}
