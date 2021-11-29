package com.vayzard.feature.enrollment.domain.model

data class EnrollmentState(
  val firstName: InputState = InputState(),
  val lastName: InputState = InputState(),
)
