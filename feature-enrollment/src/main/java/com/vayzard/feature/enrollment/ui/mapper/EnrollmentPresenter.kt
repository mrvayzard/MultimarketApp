package com.vayzard.feature.enrollment.ui.mapper

import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import com.vayzard.feature.enrollment.ui.model.EnrollmentUiModel

class EnrollmentPresenter {
  fun toUiModel(
    enrollmentState: EnrollmentState
  ) = EnrollmentUiModel(
    firstName = enrollmentState.firstName,
    lastName = enrollmentState.lastName,
  )
}