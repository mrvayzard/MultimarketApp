package com.vayzard.feature.enrollment.data.repository

import com.vayzard.feature.enrollment.data.api.EnrollmentApiService
import com.vayzard.feature.enrollment.data.model.EnrollRequest
import com.vayzard.feature.enrollment.domain.EnrollmentRepository
import com.vayzard.feature.enrollment.domain.model.UserInfoDefault

class EnrollmentRepositoryImpl(
  private val enrollmentApiService: EnrollmentApiService
) : EnrollmentRepository {
  override suspend fun enroll(
    firstName: String,
    lastName: String
  ): UserInfoDefault {
    val request = EnrollRequest(
      firstName = firstName,
      lastName = lastName
    )
    val response = enrollmentApiService.enroll(
      request = request
    )
    return UserInfoDefault(
      id = response.userId,
      firstName = response.firstName,
      lastName = response.lastName
    )
  }
}