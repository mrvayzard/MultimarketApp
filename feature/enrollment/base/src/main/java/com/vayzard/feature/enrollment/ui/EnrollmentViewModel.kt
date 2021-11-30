package com.vayzard.feature.enrollment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vayzard.feature.enrollment.domain.EnrollmentProcessor
import com.vayzard.feature.enrollment.ui.epoxy.EnrollmentEpoxyController
import com.vayzard.feature.enrollment.ui.mapper.EnrollmentPresenter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Base view model implementation that contains shared logic of Enrollment screen.
 * It can be used as parent class for specific market implementation.
 */
internal class EnrollmentViewModel(
  private val enrollmentPresenter: EnrollmentPresenter,
  private val enrollmentProcessor: EnrollmentProcessor,
  enrollmentDelegate: EnrollmentViewModelDelegate,
) : ViewModel(),
  EnrollmentEpoxyController.Callback,
  EnrollmentViewModelDelegate by enrollmentDelegate {
  init {
    viewModelScope.launch {
      init(enrollmentProcessor.enrollmentFlow())
    }
  }

  val stateFlow = enrollmentProcessor.enrollmentFlow()
    .map(enrollmentPresenter::toUiModel)

  override fun onFirstNameChanged(value: String) {
    enrollmentProcessor.updateFirstName(value)
  }

  override fun onLastNameChanged(value: String) {
    enrollmentProcessor.updateLastName(value)
  }

  override fun onEnrollButtonClicked() {
    enrollmentProcessor.enroll()
  }
}