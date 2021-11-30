package com.vayzard.feature.enrollment.mxa.domain

import com.vayzard.feature.enrollment.mxa.domain.model.UserInfoMxa

internal interface EnrollmentRepositoryMxa {
  suspend fun enroll(
    firstName: String,
    lastName: String,
    mexicoSpecificField: String
  ): UserInfoMxa
}