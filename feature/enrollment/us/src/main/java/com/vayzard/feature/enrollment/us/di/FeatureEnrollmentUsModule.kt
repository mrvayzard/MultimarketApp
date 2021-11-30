package com.vayzard.feature.enrollment.us.di

import com.vayzard.feature.enrollment.ui.EnrollmentFragmentProvider
import com.vayzard.feature.enrollment.us.EnrollmentFragmentProviderUs
import org.koin.dsl.module

val featureEnrollmentUsModule = module {
  single<EnrollmentFragmentProvider> { EnrollmentFragmentProviderUs() }
}