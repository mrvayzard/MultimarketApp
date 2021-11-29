package com.vayzard.feature.enrollment.mxa.di

import com.vayzard.feature.enrollment.di.FeatureEnrollmentScope
import com.vayzard.feature.enrollment.mxa.data.api.EnrollmentApiServiceMxa
import com.vayzard.feature.enrollment.mxa.data.repository.EnrollmentRepositoryImplMxa
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentInteractorMxa
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentRepositoryMxa
import com.vayzard.feature.enrollment.mxa.domain.validator.MexicoSpecificFieldValidator
import com.vayzard.feature.enrollment.mxa.ui.EnrollmentViewModelMxa
import com.vayzard.feature.enrollment.mxa.ui.mapper.EnrollmentPresenterMxa
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val featureEnrollmentMxaModule = module {
  scope(named(FeatureEnrollmentScope.ID)) {
    scoped {
      EnrollmentInteractorMxa(
        enrollmentRepositoryMxa = get(),
        mexicoSpecificFieldValidator = get(),
        // provided by feature-enrollment module
        interactor = get()
      )
    }
  }

  viewModel {
    val scope = getScope(FeatureEnrollmentScope.ID)

    EnrollmentViewModelMxa(
      enrollmentPresenterMxa = get(),
      enrollmentInteractorMxa = scope.get(),
      // provided by feature-enrollment module
      enrollmentPresenter = get(),
      // provided by feature-enrollment module
      enrollmentInteractor = scope.get(),
    )
  }

  factory { EnrollmentPresenterMxa() }


  factory { MexicoSpecificFieldValidator() }

  factory {
    EnrollmentRepositoryImplMxa(
      enrollmentApiService = get()
    )
  } bind EnrollmentRepositoryMxa::class

  factory { EnrollmentApiServiceMxa() }
}