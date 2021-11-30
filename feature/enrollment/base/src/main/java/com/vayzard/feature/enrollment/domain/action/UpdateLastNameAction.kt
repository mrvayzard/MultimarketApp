package com.vayzard.feature.enrollment.domain.action

import com.vayzard.feature.enrollment.domain.EnrollmentAction
import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import com.vayzard.feature.enrollment.domain.validator.LastNameValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

data class UpdateLastNameAction(
  val value: String,
  val reducer: UpdateLastNameReducer,
) : EnrollmentAction() {
  override suspend fun reduce(
    state: EnrollmentState?,
  ): Flow<EnrollmentState> = state?.let { flowOf(reducer.reduce(value, state)) } ?: emptyFlow()
}

class UpdateLastNameReducer(
  private val lastNameValidator: LastNameValidator
) {
  fun reduce(
    value: String,
    state: EnrollmentState
  ): EnrollmentState = state.copy(
    lastName = state.lastName.copy(
      value = value,
      error = lastNameValidator.validate(value)
    )
  )
}