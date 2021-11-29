package com.vayzard.feature.enrollment.data.repository

import com.vayzard.feature.enrollment.data.api.EnrollmentApiService
import com.vayzard.feature.enrollment.data.model.EnrollRequest
import com.vayzard.feature.enrollment.domain.EnrollmentRepository
import com.vayzard.feature.enrollment.domain.model.UserInfo

class EnrollmentRepositoryImpl(
  private val enrollmentApiService: EnrollmentApiService
) : EnrollmentRepository {
  override suspend fun enroll(
    firstName: String,
    lastName: String
  ): UserInfo {
    val request = EnrollRequest(
      firstName = firstName,
      lastName = lastName
    )
    val response = enrollmentApiService.enroll(
      request = request
    )
    return UserInfo(
      id = response.userId,
      firstName = response.firstName,
      lastName = response.lastName
    )
  }
}