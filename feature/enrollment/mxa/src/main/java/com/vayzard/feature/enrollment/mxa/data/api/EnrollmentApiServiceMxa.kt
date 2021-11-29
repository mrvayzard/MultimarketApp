package com.vayzard.feature.enrollment.mxa.data.api

import com.vayzard.feature.enrollment.mxa.data.model.EnrollRequestMxa
import com.vayzard.feature.enrollment.mxa.data.model.EnrollResponseMxa
import kotlin.random.Random

class EnrollmentApiServiceMxa {
  suspend fun enroll(
    request: EnrollRequestMxa
  ): EnrollResponseMxa = EnrollResponseMxa(
    userId = Random.nextLong(),
    firstName = request.firstName,
    lastName = request.lastName,
    mexicoSpecificField = request.mexicoSpecificField,
  )
}