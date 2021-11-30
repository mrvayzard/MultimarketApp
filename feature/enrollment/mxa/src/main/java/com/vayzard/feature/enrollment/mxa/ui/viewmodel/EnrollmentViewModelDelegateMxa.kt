package com.vayzard.feature.enrollment.mxa.ui.viewmodel

import com.vayzard.feature.enrollment.domain.model.UserInfo
import com.vayzard.feature.enrollment.mxa.domain.model.UserInfoMxa
import com.vayzard.feature.enrollment.ui.viewmodel.EnrollmentViewModelDelegateDefault

class EnrollmentViewModelDelegateMxa : EnrollmentViewModelDelegateDefault() {
  /**
   * Mexico market has unique UserInfo model
   */
  override fun showUserInfo(userInfo: UserInfo) {
    if (userInfo is UserInfoMxa) {
      val message = StringBuilder()
        .appendLine("Mexico implementation:")
        .appendLine(userInfo.userInfoDefault.firstName)
        .appendLine(userInfo.userInfoDefault.lastName)
        .appendLine(userInfo.mexicoSpecificField)
        .toString()
      showMessage(message)
    }
  }
}
