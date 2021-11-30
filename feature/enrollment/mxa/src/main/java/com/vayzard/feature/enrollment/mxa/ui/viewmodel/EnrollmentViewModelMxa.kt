package com.vayzard.feature.enrollment.mxa.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vayzard.feature.enrollment.mxa.domain.EnrollmentBlocMxa
import com.vayzard.feature.enrollment.mxa.ui.epoxy.EnrollmentEpoxyControllerMxa
import com.vayzard.feature.enrollment.mxa.ui.mapper.EnrollmentPresenterMxa
import com.vayzard.feature.enrollment.ui.viewmodel.EnrollmentViewModelDelegate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class EnrollmentViewModelMxa(
  private val enrollmentPresenterMxa: EnrollmentPresenterMxa,
  private val enrollmentBlocMxa: EnrollmentBlocMxa,
  enrollmentDelegate: EnrollmentViewModelDelegate,
) : ViewModel(),
  EnrollmentEpoxyControllerMxa.Callback,
  EnrollmentViewModelDelegate by enrollmentDelegate {
  val stateFlow = enrollmentBlocMxa.asFlow().map(enrollmentPresenterMxa::toUiModel)

  init {
    viewModelScope.launch {
      init(enrollmentBlocMxa.enrollmentFlow())
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