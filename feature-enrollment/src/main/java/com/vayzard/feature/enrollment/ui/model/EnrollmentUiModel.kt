package com.vayzard.feature.enrollment.ui.model

import com.vayzard.feature.enrollment.domain.model.InputState

data class EnrollmentUiModel(
  val firstName: InputState,
  val lastName: InputState,
)
