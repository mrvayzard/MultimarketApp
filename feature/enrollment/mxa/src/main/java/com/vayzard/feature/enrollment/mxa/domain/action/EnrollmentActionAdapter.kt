package com.vayzard.feature.enrollment.mxa.domain.action

import com.vayzard.feature.enrollment.domain.EnrollmentAction
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentActionMxa
import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentStateMxa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

internal data class EnrollmentActionAdapter(
  val action: EnrollmentAction,
) : EnrollmentActionMxa() {
  override suspend fun reduce(
    state: EnrollmentStateMxa?,
  ): Flow<EnrollmentStateMxa> {
    state ?: return emptyFlow()

    return flow {
      action.reduce(state.enrollmentBaseState).collect { updatedBaseState ->
        emit(state.copy(enrollmentBaseState = updatedBaseState))
      }
    }
  }
}