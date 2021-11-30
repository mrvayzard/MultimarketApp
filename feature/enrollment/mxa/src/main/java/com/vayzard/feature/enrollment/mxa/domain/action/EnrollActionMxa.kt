package com.vayzard.feature.enrollment.mxa.domain.action

import com.vayzard.feature.enrollment.domain.exception.EnrollmentException
import com.vayzard.feature.enrollment.domain.validator.FirstNameValidator
import com.vayzard.feature.enrollment.domain.validator.LastNameValidator
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentRepositoryMxa
import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentResultMxa
import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentStateMxa
import com.vayzard.feature.enrollment.mxa.domain.validator.MexicoSpecificFieldValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

data class EnrollActionMxa(
  val reducer: EnrollReducerMxa,
) : EnrollmentActionMxa() {
  override suspend fun reduce(
    state: EnrollmentStateMxa?,
  ): Flow<EnrollmentStateMxa> = state?.let { flowOf(reducer.reduce(state)) } ?: emptyFlow()
}

class EnrollReducerMxa(
  private val firstNameValidator: FirstNameValidator,
  private val lastNameValidator: LastNameValidator,
  private val mexicoSpecificFieldValidator: MexicoSpecificFieldValidator,
  private val repository: EnrollmentRepositoryMxa
) {
  suspend fun reduce(
    state: EnrollmentStateMxa
  ): EnrollmentStateMxa {
    val firstNameError = firstNameValidator.validate(
      value = state.enrollmentBaseState.firstName.value
    )
    val lastNameError = lastNameValidator.validate(
      value = state.enrollmentBaseState.lastName.value
    )
    val mexicoSpecificFieldError = mexicoSpecificFieldValidator.validate(
      value = state.enrollmentSpecificState.mexicoSpecificField.value
    )

    val hasValidationErrors = firstNameError != null || lastNameError != null

    val result = if (hasValidationErrors) {
      // additional validation for initial state
      EnrollmentResultMxa.Failure(
        error = EnrollmentException()
      )
    } else {
      EnrollmentResultMxa.Success(
        userInfo = repository.enroll(
          firstName = state.enrollmentBaseState.firstName.value,
          lastName = state.enrollmentBaseState.lastName.value,
          mexicoSpecificField = state.enrollmentSpecificState.mexicoSpecificField.value
        )
      )
    }

    return state.copy(
      enrollmentBaseState = state.enrollmentBaseState.copy(
        firstName = state.enrollmentBaseState.firstName.copy(
          error = firstNameError
        ),
        lastName = state.enrollmentBaseState.lastName.copy(
          error = lastNameError
        ),
      ),
      enrollmentSpecificState = state.enrollmentSpecificState.copy(
        mexicoSpecificField = state.enrollmentSpecificState.mexicoSpecificField.copy(
          error = mexicoSpecificFieldError
        ),
        resultMxa = result
      ),
    )
  }
}