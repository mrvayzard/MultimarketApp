package com.vayzard.feature.enrollment.domain

import com.vayzard.feature.enrollment.domain.exception.EnrollmentException
import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import com.vayzard.feature.enrollment.domain.model.UserInfo
import com.vayzard.feature.enrollment.domain.validator.FirstNameValidator
import com.vayzard.feature.enrollment.domain.validator.LastNameValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EnrollmentInteractor(
  private val enrollmentRepository: EnrollmentRepository,
  private val firstNameValidator: FirstNameValidator,
  private val lastNameValidator: LastNameValidator,

  ) {
  private val enrollmentMutableStateFlow = MutableStateFlow(
    value = EnrollmentState()
  )
  val enrollmentStateFlow: StateFlow<EnrollmentState> = enrollmentMutableStateFlow.asStateFlow()

  suspend fun enroll(): Result<UserInfo> {
    val isValidData = isEnrollDataValid()

    return if (isValidData) {
      val userInfo = enrollmentRepository.enroll(
        firstName = enrollmentStateFlow.value.firstName.value,
        lastName = enrollmentStateFlow.value.lastName.value
      )
      Result.success(userInfo)
    } else {
      Result.failure(EnrollmentException())
    }
  }

  fun isEnrollDataValid(): Boolean {
    val firstName = enrollmentStateFlow.value.firstName
    val firstNameError = firstNameValidator.validate(firstName.value)

    val lastName = enrollmentStateFlow.value.lastName
    val lastNameError = lastNameValidator.validate(lastName.value)

    // additional validation for fields
    enrollmentMutableStateFlow.value = enrollmentStateFlow.value.copy(
      firstName = firstName.copy(
        error = firstNameError
      ),
      lastName = lastName.copy(
        error = lastNameError
      )
    )
    return firstNameError == null && lastNameError == null
  }

  fun updateFirstName(value: String) {
    enrollmentMutableStateFlow.value = enrollmentStateFlow.value.copy(
      firstName = enrollmentStateFlow.value.firstName.copy(
        value = value,
        error = firstNameValidator.validate(value)
      )
    )
  }

  fun updateLastName(value: String) {
    enrollmentMutableStateFlow.value = enrollmentStateFlow.value.copy(
      lastName = enrollmentStateFlow.value.lastName.copy(
        value = value,
        error = lastNameValidator.validate(value)
      )
    )
  }
}
