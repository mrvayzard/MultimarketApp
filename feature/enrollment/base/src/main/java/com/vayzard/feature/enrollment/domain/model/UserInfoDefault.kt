package com.vayzard.feature.enrollment.domain.model

interface UserInfo

data class UserInfoDefault(
  val id: Long,
  val firstName: String,
  val lastName: String
) : UserInfo