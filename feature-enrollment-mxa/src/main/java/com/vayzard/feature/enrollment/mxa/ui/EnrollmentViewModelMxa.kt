package com.vayzard.feature.enrollment.mxa.ui

import androidx.lifecycle.viewModelScope
import com.vayzard.feature.enrollment.domain.EnrollmentInteractor
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentInteractorMxa
import com.vayzard.feature.enrollment.mxa.ui.mapper.EnrollmentPresenterMxa
import com.vayzard.feature.enrollment.mxa.ui.model.EnrollmentUiModelMxa
import com.vayzard.feature.enrollment.ui.EnrollmentViewModel
import com.vayzard.feature.enrollment.ui.mapper.EnrollmentPresenter
import com.vayzard.feature.enrollment.ui.model.MessageState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class EnrollmentViewModelMxa(
  private val enrollmentPresenterMxa: EnrollmentPresenterMxa,
  private val enrollmentInteractorMxa: EnrollmentInteractorMxa,
  enrollmentPresenter: EnrollmentPresenter,
  enrollmentInteractor: EnrollmentInteractor,
) : EnrollmentViewModel(
  enrollmentPresenter = enrollmentPresenter,
  enrollmentInteractor = enrollmentInteractor
) {
  val mexicoStateFlow: Flow<EnrollmentUiModelMxa> = enrollmentInteractorMxa.stateFlow
    .map(enrollmentPresenterMxa::toUiModel)

  /**
   * This method uses specific interactor for `enroll` method because MXA enrollment api has different scheme
   */
  override fun onEnrollButtonClicked() {
    viewModelScope.launch {
      enrollmentInteractorMxa.enroll()
        .onSuccess { userInfoMxa -> showMessage(text = userInfoMxa.toString()) }
        .onFailure { error -> showMessage(text = error.message ?: "Unknown error") }
    }
  }

  /**
   * Unique method for MXA market
   */
  fun onMexicoSpecificFieldChanged(value: String) {
    viewModelScope.launch {
      enrollmentInteractorMxa.updateMexicoSpecificField(value)
    }
  }
}
