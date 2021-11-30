package com.vayzard.feature.enrollment.mxa.ui

import androidx.lifecycle.viewModelScope
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentBlocMxa
import com.vayzard.feature.enrollment.mxa.domain.model.EnrollmentResultMxa
import com.vayzard.feature.enrollment.mxa.ui.mapper.EnrollmentPresenterMxa
import com.vayzard.feature.enrollment.mxa.ui.model.EnrollmentUiModelMxa
import com.vayzard.feature.enrollment.ui.EnrollmentViewModel
import com.vayzard.feature.enrollment.ui.mapper.EnrollmentPresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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

  init {
    viewModelScope.launch {
      enrollmentBlocMxa.asFlow().collect {
        processEnrollmentResult(it.enrollmentSpecificState.resultMxa)
      }
    }
  }

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

  private fun processEnrollmentResult(result: EnrollmentResultMxa) {
    when (result) {
      is EnrollmentResultMxa.Failure -> {
        showMessage(result.error.message ?: "Unknown error")
      }
      is EnrollmentResultMxa.Success -> {
        showMessage(result.userInfo.toString())
      }
      EnrollmentResultMxa.Idle -> {
        // do nothing
      }
    }
  }
}
