package com.vayzard.feature.enrollment.mxa.ui

import androidx.lifecycle.ViewModel
import com.vayzard.feature.enrollment.domain.model.UserInfo
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentBlocMxa
import com.vayzard.feature.enrollment.mxa.domain.model.UserInfoMxa
import com.vayzard.feature.enrollment.mxa.ui.epoxy.EnrollmentEpoxyControllerMxa
import com.vayzard.feature.enrollment.mxa.ui.mapper.EnrollmentPresenterMxa
import com.vayzard.feature.enrollment.ui.EnrollmentViewModelDelegate
import kotlinx.coroutines.flow.map

internal class EnrollmentViewModelMxa(
  private val enrollmentPresenterMxa: EnrollmentPresenterMxa,
  private val enrollmentBlocMxa: EnrollmentBlocMxa,
  private val enrollmentDelegate: EnrollmentViewModelDelegate,
) : ViewModel(),
  EnrollmentEpoxyControllerMxa.Callback,
  EnrollmentViewModelDelegate by enrollmentDelegate {
  val stateFlow = enrollmentBlocMxa.asFlow().map(enrollmentPresenterMxa::toUiModel)

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

  override fun onMexicoSpecificFieldChanged(value: String) {
    enrollmentBlocMxa.updateMexicoSpecificField(value)
  }

  override fun onFirstNameChanged(value: String) {
    enrollmentBlocMxa.updateFirstName(value)
  }

  override fun onLastNameChanged(value: String) {
    enrollmentBlocMxa.updateLastName(value)
  }

  override fun onEnrollButtonClicked() {
    enrollmentBlocMxa.enroll()
  }
}