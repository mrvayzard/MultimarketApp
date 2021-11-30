package com.vayzard.feature.enrollment.mxa.ui.mapper

import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentStateMxa
import com.vayzard.feature.enrollment.mxa.ui.model.EnrollmentUiModelMxa
import com.vayzard.feature.enrollment.ui.mapper.EnrollmentPresenter

internal class EnrollmentPresenterMxa(
  private val enrollmentPresenter: EnrollmentPresenter
) {
  fun toUiModel(state: EnrollmentStateMxa) = EnrollmentUiModelMxa(
    enrollmentUiModel = enrollmentPresenter.toUiModel(state.enrollmentBaseState),
    mexicoSpecificField = state.enrollmentSpecificState.mexicoSpecificField
  )
}