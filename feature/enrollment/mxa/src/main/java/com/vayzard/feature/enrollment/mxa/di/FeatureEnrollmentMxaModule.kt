package com.vayzard.feature.enrollment.mxa.di

import com.vayzard.feature.enrollment.di.FeatureEnrollmentScope
import com.vayzard.feature.enrollment.mxa.data.api.EnrollmentApiServiceMxa
import com.vayzard.feature.enrollment.mxa.data.repository.EnrollmentRepositoryImplMxa
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentBlocMxa
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentRepositoryMxa
import com.vayzard.feature.enrollment.mxa.domain.action.EnrollReducerMxa
import com.vayzard.feature.enrollment.mxa.domain.action.UpdateMexicoSpecificFieldReducerMxa
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
      EnrollmentBlocMxa(
        enrollReducer = get(),
        updateMexicoSpecificFieldReducer = get(),
        // provided by base module
        updateFirstNameReducer = get(),
        updateLastNameReducer = get(),
        scope = get()
      )
    }
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
    val scope = getScope(FeatureEnrollmentScope.ID)

    EnrollmentViewModelMxa(
      enrollmentPresenterMxa = get(),
      enrollmentBlocMxa = scope.get(),
      // provided by base module
      enrollmentPresenter = get(),
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