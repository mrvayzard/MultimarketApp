package com.vayzard.feature.enrollment.mxa.data.repository

import com.vayzard.feature.enrollment.domain.model.UserInfo
import com.vayzard.feature.enrollment.mxa.data.api.EnrollmentApiServiceMxa
import com.vayzard.feature.enrollment.mxa.data.model.EnrollRequestMxa
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentRepositoryMxa
import com.vayzard.feature.enrollment.mxa.domain.model.UserInfoMxa

class EnrollmentRepositoryImplMxa(
  private val enrollmentApiService: EnrollmentApiServiceMxa
) : EnrollmentRepositoryMxa {
  override suspend fun enroll(
    firstName: String,
    lastName: String,
    mexicoSpecificField: String
  ): UserInfoMxa {
    val request = EnrollRequestMxa(
      firstName = firstName,
      lastName = lastName,
      mexicoSpecificField = mexicoSpecificField
    )
    val response = enrollmentApiService.enroll(
      request = request
    )
    return UserInfoMxa(
      userInfo = UserInfo(
        id = response.userId,
        firstName = response.firstName,
        lastName = response.lastName
      ),
      mexicoSpecificField = response.mexicoSpecificField
    )
  }
}