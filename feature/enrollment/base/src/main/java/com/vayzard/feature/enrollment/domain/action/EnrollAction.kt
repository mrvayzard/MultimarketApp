package com.vayzard.feature.enrollment.domain.action

import com.vayzard.feature.enrollment.domain.EnrollmentAction
import com.vayzard.feature.enrollment.domain.EnrollmentRepository
import com.vayzard.feature.enrollment.domain.exception.EnrollmentException
import com.vayzard.feature.enrollment.domain.model.EnrollmentResult
import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import com.vayzard.feature.enrollment.domain.validator.FirstNameValidator
import com.vayzard.feature.enrollment.domain.validator.LastNameValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

data class EnrollAction(
  val reducer: EnrollReducer,
) : EnrollmentAction() {
  override suspend fun reduce(
    state: EnrollmentState?,
  ): Flow<EnrollmentState> = state?.let { flowOf(reducer.reduce(state)) } ?: emptyFlow()
}

class EnrollReducer(
  private val firstNameValidator: FirstNameValidator,
  private val lastNameValidator: LastNameValidator,
  private val repository: EnrollmentRepository
) {
  suspend fun reduce(
    state: EnrollmentState
  ): EnrollmentState {
    val firstNameError = firstNameValidator.validate(state.firstName.value)
    val lastNameError = lastNameValidator.validate(state.lastName.value)

    val hasValidationErrors = firstNameError != null || lastNameError != null

    val result = if (hasValidationErrors) {
      // additional validation for initial state
      EnrollmentResult.Failure(
        error = EnrollmentException()
      )
    } else {
      EnrollmentResult.Success(
        userInfo = repository.enroll(
          firstName = state.firstName.value,
          lastName = state.lastName.value
        )
      )
    }

    return state.copy(
      firstName = state.firstName.copy(
        error = firstNameError
      ),
      lastName = state.lastName.copy(
        error = lastNameError
      ),
      result = result
    )
  }
}