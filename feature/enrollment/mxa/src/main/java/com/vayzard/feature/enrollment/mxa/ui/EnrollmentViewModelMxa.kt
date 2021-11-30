package com.vayzard.feature.enrollment.mxa.ui

import androidx.lifecycle.viewModelScope
import com.vayzard.feature.enrollment.domain.model.UserInfo
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentBlocMxa
import com.vayzard.feature.enrollment.mxa.domain.model.UserInfoMxa
import com.vayzard.feature.enrollment.mxa.ui.mapper.EnrollmentPresenterMxa
import com.vayzard.feature.enrollment.mxa.ui.model.EnrollmentUiModelMxa
import com.vayzard.feature.enrollment.ui.EnrollmentViewModel
import com.vayzard.feature.enrollment.ui.mapper.EnrollmentPresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class EnrollmentViewModelMxa(
  private val enrollmentPresenterMxa: EnrollmentPresenterMxa,
  private val enrollmentBlocMxa: EnrollmentBlocMxa,
  enrollmentPresenter: EnrollmentPresenter,
) : EnrollmentViewModel(
  enrollmentPresenter = enrollmentPresenter,
  enrollmentProcessor = enrollmentBlocMxa
) {
  val mexicoStateFlow: Flow<EnrollmentUiModelMxa> = enrollmentBlocMxa.asFlow()
    .map(enrollmentPresenterMxa::toUiModel)

  /**
   * This method uses specific interactor for `enroll` method because MXA enrollment api has different scheme
   */
  override fun onEnrollButtonClicked() {
    viewModelScope.launch {
      enrollmentBlocMxa.enroll()
    }
  }

  /**
   * Unique method for MXA market
   */
  fun onMexicoSpecificFieldChanged(value: String) {
    viewModelScope.launch {
      enrollmentBlocMxa.updateMexicoSpecificField(value)
    }
  }

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
