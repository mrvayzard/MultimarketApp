package com.vayzard.feature.enrollment.mxa.domain

import com.vayzard.feature.enrollment.domain.EnrollmentInteractor
import com.vayzard.feature.enrollment.domain.exception.EnrollmentException
import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentSpecificState
import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentStateMxa
import com.vayzard.feature.enrollment.mxa.domain.model.UserInfoMxa
import com.vayzard.feature.enrollment.mxa.domain.validator.MexicoSpecificFieldValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class EnrollmentInteractorMxa(
  private val enrollmentRepositoryMxa: EnrollmentRepositoryMxa,
  private val mexicoSpecificFieldValidator: MexicoSpecificFieldValidator,
  private val interactor: EnrollmentInteractor,
) {
  private val enrollmentSpecificMutableStateFlow = MutableStateFlow(
    value = EnrollmentSpecificState()
  )
  val stateFlow: Flow<EnrollmentStateMxa> = combine(
    interactor.enrollmentStateFlow,
    enrollmentSpecificMutableStateFlow,
    ::EnrollmentStateMxa
  )

  suspend fun enroll(): Result<UserInfoMxa> {
    val isValidData = isEnrollDataValid()

    return if (isValidData) {
      val userInfo = enrollmentRepositoryMxa.enroll(
        firstName = interactor.enrollmentStateFlow.value.firstName.value,
        lastName = interactor.enrollmentStateFlow.value.lastName.value,
        mexicoSpecificField = enrollmentSpecificMutableStateFlow.value.mexicoSpecificField.value,
      )
      Result.success(userInfo)
    } else {
      Result.failure(EnrollmentException())
    }
  }

  fun updateMexicoSpecificField(value: String) {
    enrollmentSpecificMutableStateFlow.value = enrollmentSpecificMutableStateFlow.value.copy(
      mexicoSpecificField = enrollmentSpecificMutableStateFlow.value.mexicoSpecificField.copy(
        value = value,
        error = mexicoSpecificFieldValidator.validate(value)
      )
    )
  }

  private fun isEnrollDataValid(): Boolean {
    val mexicoSpecificField = enrollmentSpecificMutableStateFlow.value.mexicoSpecificField
    val mexicoSpecificFieldError = mexicoSpecificFieldValidator.validate(mexicoSpecificField.value)

    // additional validation for fields
    enrollmentSpecificMutableStateFlow.value = enrollmentSpecificMutableStateFlow.value.copy(
      mexicoSpecificField = mexicoSpecificField.copy(
        error = mexicoSpecificFieldError
      )
    )
    return interactor.isEnrollDataValid() && mexicoSpecificFieldError == null
  }
}
