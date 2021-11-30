package com.vayzard.feature.enrollment.jpn.di

import com.vayzard.feature.enrollment.jpn.ui.JpnEnrollmentFragmentProvider
import com.vayzard.feature.enrollment.ui.EnrollmentFragmentProvider
import org.koin.dsl.module

val featureEnrollmentJpnModule = module {
  single<EnrollmentFragmentProvider> { JpnEnrollmentFragmentProvider() }
}