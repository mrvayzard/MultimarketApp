package com.vayzard.feature.enrollment.mxa.domain.model

import com.vayzard.feature.enrollment.domain.model.UserInfo
import com.vayzard.feature.enrollment.domain.model.UserInfoDefault

internal data class UserInfoMxa(
  val userInfoDefault: UserInfoDefault,
  val mexicoSpecificField: String,
) : UserInfo
