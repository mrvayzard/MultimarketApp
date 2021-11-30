package com.vayzard.feature.enrollment.mxa.domain.model

import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import com.vayzard.feature.enrollment.domain.model.InputState

/**
 * Just wrapper.
 * Always contains only base enrollment state and specific enrollment state
 */
internal data class EnrollmentStateMxa(
  val enrollmentBaseState: EnrollmentState = EnrollmentState(),
  val enrollmentSpecificState: EnrollmentSpecificState = EnrollmentSpecificState()
)

/**
 * Contains only specific fields for current market
 */
internal data class EnrollmentSpecificState(
  val mexicoSpecificField: InputState = InputState(),
)
