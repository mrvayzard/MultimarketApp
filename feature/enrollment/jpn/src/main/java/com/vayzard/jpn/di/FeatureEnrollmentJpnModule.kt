package com.vayzard.jpn.di

import com.vayzard.feature.enrollment.domain.validator.FirstNameValidator
import com.vayzard.feature.enrollment.domain.validator.LastNameValidator
import com.vayzard.jpn.domain.validator.FirstNameValidatorJpn
import com.vayzard.jpn.domain.validator.LastNameValidatorJpn
import org.koin.dsl.bind
import org.koin.dsl.module

val featureEnrollmentJpnModule = module {
  factory { FirstNameValidatorJpn() } bind FirstNameValidator::class
  factory { LastNameValidatorJpn() } bind LastNameValidator::class
}