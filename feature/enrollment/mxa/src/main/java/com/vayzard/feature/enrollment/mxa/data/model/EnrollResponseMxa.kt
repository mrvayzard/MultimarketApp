package com.vayzard.feature.enrollment.mxa.data.model

internal data class EnrollResponseMxa(
  val userId: Long,
  val firstName: String,
  val lastName: String,
  val mexicoSpecificField: String,
)
