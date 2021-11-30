package com.vayzard.feature.enrollment.mxa.ui.model

import com.vayzard.feature.enrollment.domain.model.InputState
import com.vayzard.feature.enrollment.ui.model.EnrollmentUiModel

internal data class EnrollmentUiModelMxa(
  val enrollmentUiModel: EnrollmentUiModel,
  val mexicoSpecificField: InputState
)