package com.vayzard.feature.enrollment.mxa.domain.model

import com.vayzard.feature.enrollment.domain.model.UserInfo

data class UserInfoMxa(
  val userInfo: UserInfo,
  val mexicoSpecificField: String,
)
