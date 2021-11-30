package com.vayzard.feature.enrollment.mxa.di

import com.vayzard.feature.enrollment.mxa.data.api.EnrollmentApiServiceMxa
import com.vayzard.feature.enrollment.mxa.data.repository.EnrollmentRepositoryImplMxa
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentBlocMxa
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentRepositoryMxa
import com.vayzard.feature.enrollment.mxa.domain.action.EnrollReducerMxa
import com.vayzard.feature.enrollment.mxa.domain.action.UpdateMexicoSpecificFieldReducerMxa
import com.vayzard.feature.enrollment.mxa.domain.validator.MexicoSpecificFieldValidator
import com.vayzard.feature.enrollment.mxa.ui.EnrollmentFragmentProviderMxa
import com.vayzard.feature.enrollment.mxa.ui.mapper.EnrollmentPresenterMxa
import com.vayzard.feature.enrollment.mxa.ui.viewmodel.EnrollmentViewModelDelegateMxa
import com.vayzard.feature.enrollment.mxa.ui.viewmodel.EnrollmentViewModelMxa
import com.vayzard.feature.enrollment.ui.EnrollmentFragmentProvider
import com.vayzard.feature.enrollment.ui.viewmodel.EnrollmentViewModelDelegate
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val featureEnrollmentMxaModule = module {
  factory {
    EnrollmentBlocMxa(
      enrollReducer = get(),
      updateMexicoSpecificFieldReducer = get(),
      // provided by base module
      updateFirstNameReducer = get(),
      // provided by base module
      updateLastNameReducer = get(),
      // provided by app module
      scope = get(),
      // provided by app module
      dispatcherProvider = get(),
    )
  }
  factory {
    UpdateMexicoSpecificFieldReducerMxa(
      mexicoSpecificFieldValidator = get()
    )
  }
  factory {
    EnrollReducerMxa(
      firstNameValidator = get(),
      lastNameValidator = get(),
      mexicoSpecificFieldValidator = get(),
      repository = get()
    )
  }

  viewModel {
    EnrollmentViewModelMxa(
      enrollmentPresenterMxa = get(),
      enrollmentBlocMxa = get(),
      enrollmentDelegate = get()
    )
  }

  factory { EnrollmentViewModelDelegateMxa() } bind EnrollmentViewModelDelegate::class

  factory {
    EnrollmentPresenterMxa(
      // provided by base module
      enrollmentPresenter = get()
    )
  }

  factory { MexicoSpecificFieldValidator() }

  factory {
    EnrollmentRepositoryImplMxa(
      enrollmentApiService = get()
    )
  } bind EnrollmentRepositoryMxa::class

  factory { EnrollmentApiServiceMxa() }

  single<EnrollmentFragmentProvider> { EnrollmentFragmentProviderMxa() }
}