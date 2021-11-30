package com.vayzard.feature.enrollment.mxa.domain.action

import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentStateMxa
import com.vayzard.feature.enrollment.mxa.domain.validator.MexicoSpecificFieldValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

data class UpdateMexicoSpecificFieldActionMxa(
  val value: String,
  val reducer: UpdateMexicoSpecificFieldReducerMxa,
) : EnrollmentActionMxa() {
  override suspend fun reduce(
    state: EnrollmentStateMxa?,
  ): Flow<EnrollmentStateMxa> = state?.let { flowOf(reducer.reduce(this, state)) } ?: emptyFlow()
}

class UpdateMexicoSpecificFieldReducerMxa(
  private val mexicoSpecificFieldValidator: MexicoSpecificFieldValidator
) {
  fun reduce(
    action: UpdateMexicoSpecificFieldActionMxa,
    state: EnrollmentStateMxa
  ): EnrollmentStateMxa = state.copy(
    enrollmentSpecificState = state.enrollmentSpecificState.copy(
      mexicoSpecificField = state.enrollmentSpecificState.mexicoSpecificField.copy(
        value = action.value,
        error = mexicoSpecificFieldValidator.validate(action.value)
      )
    )
  )
}