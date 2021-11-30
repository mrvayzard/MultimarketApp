package com.vayzard.feature.enrollment.data.api

import com.vayzard.feature.enrollment.data.model.EnrollRequest
import com.vayzard.feature.enrollment.data.model.EnrollResponse
import kotlin.random.Random

class EnrollmentApiService {
  fun enroll(
    request: EnrollRequest
  ): EnrollResponse = EnrollResponse(
    userId = Random.nextLong(),
    firstName = request.firstName,
    lastName = request.lastName
  )
}