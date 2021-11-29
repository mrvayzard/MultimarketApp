package com.vayzard.feature.enrollment.domain

import com.vayzard.feature.enrollment.domain.model.UserInfo

interface EnrollmentRepository {
  suspend fun enroll(
    firstName: String,
    lastName: String
  ): UserInfo
}