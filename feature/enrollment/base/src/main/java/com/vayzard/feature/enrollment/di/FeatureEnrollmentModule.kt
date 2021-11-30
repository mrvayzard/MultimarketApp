package com.vayzard.feature.enrollment.di

import com.vayzard.feature.enrollment.data.api.EnrollmentApiService
import com.vayzard.feature.enrollment.data.repository.EnrollmentRepositoryImpl
import com.vayzard.feature.enrollment.domain.EnrollmentBloc
import com.vayzard.feature.enrollment.domain.EnrollmentRepository
import com.vayzard.feature.enrollment.domain.action.EnrollReducer
import com.vayzard.feature.enrollment.domain.action.UpdateFirstNameReducer
import com.vayzard.feature.enrollment.domain.action.UpdateLastNameReducer
import com.vayzard.feature.enrollment.domain.validator.FirstNameValidator
import com.vayzard.feature.enrollment.domain.validator.FirstNameValidatorDefault
import com.vayzard.feature.enrollment.domain.validator.LastNameValidator
import com.vayzard.feature.enrollment.domain.validator.LastNameValidatorDefault
import com.vayzard.feature.enrollment.ui.EnrollmentViewModel
import com.vayzard.feature.enrollment.ui.mapper.EnrollmentPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val featureEnrollmentModule = module {
  // region UI
  viewModel {
    EnrollmentViewModel(
      enrollmentPresenter = get(),
      enrollmentProcessor = get<EnrollmentBloc>()
    )
  }
  factory { EnrollmentPresenter() }
  // endregion

  // region domain
  factory {
    EnrollmentBloc(
      updateFirstNameReducer = get(),
      updateLastNameReducer = get(),
      enrollReducer = get(),
      // provided by app module
      scope = get(),
      // provided by app module
      dispatcherProvider = get()
    )
  }
  factory {
    UpdateFirstNameReducer(
      firstNameValidator = get()
    )
  }
  factory {
    UpdateLastNameReducer(
      lastNameValidator = get()
    )
  }
  factory {
    EnrollReducer(
      firstNameValidator = get(),
      lastNameValidator = get(),
      repository = get()
    )
  }
  factory { FirstNameValidatorDefault() } bind FirstNameValidator::class
  factory { LastNameValidatorDefault() } bind LastNameValidator::class
  // endregion

  // region Data
  factory {
    EnrollmentRepositoryImpl(
      enrollmentApiService = get()
    )
  } bind EnrollmentRepository::class

  factory { EnrollmentApiService() }
  // endregion
}