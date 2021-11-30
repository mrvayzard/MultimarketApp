package com.vayzard.feature.enrollment.domain.model

data class EnrollmentState(
  val firstName: InputState = InputState(),
  val lastName: InputState = InputState(),
  val result: EnrollmentResult = EnrollmentResult.Idle,
)

sealed class EnrollmentResult {
  object Idle : EnrollmentResult()

  data class Failure(
    val error: Exception
  ) : EnrollmentResult()

  data class Success(
    val userInfo: UserInfo
  ) : EnrollmentResult()
}
