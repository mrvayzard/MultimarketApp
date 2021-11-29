package com.vayzard.feature.enrollment.di

import com.vayzard.feature.enrollment.data.api.EnrollmentApiService
import com.vayzard.feature.enrollment.data.repository.EnrollmentRepositoryImpl
import com.vayzard.feature.enrollment.domain.EnrollmentInteractor
import com.vayzard.feature.enrollment.domain.EnrollmentRepository
import com.vayzard.feature.enrollment.domain.validator.FirstNameValidator
import com.vayzard.feature.enrollment.domain.validator.LastNameValidator
import com.vayzard.feature.enrollment.ui.EnrollmentViewModel
import com.vayzard.feature.enrollment.ui.mapper.EnrollmentPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val featureEnrollmentModule = module {
  scope(named(FeatureEnrollmentScope.ID)) {
    scoped {
      EnrollmentInteractor(
        enrollmentRepository = get(),
        firstNameValidator = get(),
        lastNameValidator = get(),
      )
    }
  }

  viewModel {
    val scope = getScope(FeatureEnrollmentScope.ID)

    EnrollmentViewModel(
      enrollmentPresenter = get(),
      enrollmentInteractor = scope.get()
    )
  }

  factory { EnrollmentPresenter() }


  factory { FirstNameValidator() }

  factory { LastNameValidator() }

  factory {
    EnrollmentRepositoryImpl(
      enrollmentApiService = get()
    )
  } bind EnrollmentRepository::class

  factory { EnrollmentApiService() }
}