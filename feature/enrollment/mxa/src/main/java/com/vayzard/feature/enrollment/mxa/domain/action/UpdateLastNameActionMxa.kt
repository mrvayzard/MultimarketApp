package com.vayzard.feature.enrollment.mxa.domain.action

import com.vayzard.feature.enrollment.domain.action.UpdateLastNameReducer
import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentStateMxa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

data class UpdateLastNameActionMxa(
  val value: String,
  val reducer: UpdateLastNameReducer,
) : EnrollmentActionMxa() {
  override suspend fun reduce(
    state: EnrollmentStateMxa?,
  ): Flow<EnrollmentStateMxa> {
    state ?: return emptyFlow()

    val updatedState = state.copy(
      enrollmentBaseState = reducer.reduce(value, state.enrollmentBaseState)
    )
    return flowOf(updatedState)
  }
}