package com.vayzard.feature.enrollment.mxa.ui.epoxy

import com.airbnb.epoxy.EpoxyController
import com.vayzard.feature.enrollment.mxa.ui.model.EnrollmentUiModelMxa
import com.vayzard.feature.enrollment.ui.epoxy.EnrollmentEpoxyController
import com.vayzard.feature.enrollment.ui.epoxy.EnrollmentEpoxyProvider
import com.vayzard.utils.epoxy.epoxyModelDelegate

internal class EnrollmentEpoxyControllerMxa(
  private val callback: Callback,
  enrollmentEpoxyProvider: EnrollmentEpoxyProvider,
  enrollmentEpoxyProviderMxa: EnrollmentEpoxyProviderMxa
) : EpoxyController(),
  EnrollmentEpoxyProvider by enrollmentEpoxyProvider,
  EnrollmentEpoxyProviderMxa by enrollmentEpoxyProviderMxa {
  var enrollmentUiModelMxa: EnrollmentUiModelMxa? by epoxyModelDelegate(null)

  override fun buildModels() {
    val model = enrollmentUiModelMxa ?: return

    firstNameEditText(
      text = model.enrollmentUiModel.firstName.value,
      error = model.enrollmentUiModel.firstName.error?.message,
      onTextChangeAction = callback::onFirstNameChanged
    )
    lastNameEditText(
      text = model.enrollmentUiModel.lastName.value,
      error = model.enrollmentUiModel.lastName.error?.message,
      onTextChangeAction = callback::onLastNameChanged
    )
    mexicoSpecificFieldEditText(
      text = model.mexicoSpecificField.value,
      error = model.mexicoSpecificField.error?.message,
      onTextChangeAction = callback::onMexicoSpecificFieldChanged
    )
    enrollButton(
      onClickAction = callback::onEnrollButtonClicked
    )
  }

  interface Callback : EnrollmentEpoxyController.Callback {
    fun onMexicoSpecificFieldChanged(value: String)
  }
}