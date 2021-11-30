package com.vayzard.feature.enrollment.domain

import com.vayzard.feature.enrollment.domain.model.EnrollmentState
import kotlinx.coroutines.flow.Flow

interface EnrollmentProcessor {
  fun enrollmentFlow(): Flow<EnrollmentState>

  fun enroll()
  fun updateFirstName(value: String)
  fun updateLastName(value: String)
}