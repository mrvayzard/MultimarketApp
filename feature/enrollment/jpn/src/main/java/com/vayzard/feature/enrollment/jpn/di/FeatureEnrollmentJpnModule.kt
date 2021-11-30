package com.vayzard.feature.enrollment.jpn.di

import com.vayzard.feature.enrollment.domain.validator.FirstNameValidator
import com.vayzard.feature.enrollment.domain.validator.LastNameValidator
import com.vayzard.feature.enrollment.jpn.domain.validator.FirstNameValidatorJpn
import com.vayzard.feature.enrollment.jpn.domain.validator.LastNameValidatorJpn
import com.vayzard.feature.enrollment.jpn.ui.EnrollmentFragmentProviderJpn
import com.vayzard.feature.enrollment.ui.EnrollmentFragmentProvider
import org.koin.dsl.bind
import org.koin.dsl.module

val featureEnrollmentJpnModule = module {
  single<EnrollmentFragmentProvider> { EnrollmentFragmentProviderJpn() }

  factory { FirstNameValidatorJpn() } bind FirstNameValidator::class
  factory { LastNameValidatorJpn() } bind LastNameValidator::class
}