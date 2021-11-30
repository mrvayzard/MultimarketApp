package com.vayzard.feature.enrollment.domain.action

import com.vayzard.feature.enrollment.domain.EnrollmentAction
import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import com.vayzard.feature.enrollment.domain.validator.FirstNameValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

data class UpdateFirstNameAction(
  val value: String,
  val reducer: UpdateFirstNameReducer,
) : EnrollmentAction() {
  override suspend fun reduce(
    state: EnrollmentState?,
  ): Flow<EnrollmentState> = state?.let { flowOf(reducer.reduce(value, state)) } ?: emptyFlow()
}

class UpdateFirstNameReducer(
  private val firstNameValidator: FirstNameValidator
) {
  fun reduce(
    value: String,
    state: EnrollmentState
  ): EnrollmentState = state.copy(
    firstName = state.firstName.copy(
      value = value,
      error = firstNameValidator.validate(value)
    )
  )
}