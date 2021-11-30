package com.vayzard.feature.enrollment.domain

import com.vayzard.feature.enrollment.domain.model.UserInfoDefault

interface EnrollmentRepository {
  suspend fun enroll(
    firstName: String,
    lastName: String
  ): UserInfoDefault
}