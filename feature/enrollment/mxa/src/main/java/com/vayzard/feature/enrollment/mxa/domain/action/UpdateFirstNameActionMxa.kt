package com.vayzard.feature.enrollment.mxa.domain.action

import com.vayzard.feature.enrollment.domain.action.UpdateFirstNameReducer
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentActionMxa
import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentStateMxa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

internal data class UpdateFirstNameActionMxa(
  val value: String,
  val reducer: UpdateFirstNameReducer,
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