package com.vayzard.feature.enrollment.mxa.ui.mapper

import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentStateMxa
import com.vayzard.feature.enrollment.mxa.ui.model.EnrollmentUiModelMxa

internal class EnrollmentPresenterMxa {
  fun toUiModel(state: EnrollmentStateMxa) = EnrollmentUiModelMxa(
    mexicoSpecificField = state.enrollmentSpecificState.mexicoSpecificField
  )
}