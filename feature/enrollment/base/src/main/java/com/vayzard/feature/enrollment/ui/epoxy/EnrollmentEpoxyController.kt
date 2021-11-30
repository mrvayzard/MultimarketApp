package com.vayzard.feature.enrollment.ui.epoxy

import com.airbnb.epoxy.EpoxyController
import com.vayzard.feature.enrollment.ui.model.EnrollmentUiModel
import com.vayzard.utils.epoxy.epoxyModelDelegate

class EnrollmentEpoxyController(
  private val callback: Callback,
  enrollmentEpoxyProvider: EnrollmentEpoxyProvider
) : EpoxyController(), EnrollmentEpoxyProvider by enrollmentEpoxyProvider {
  var enrollmentUiModel: EnrollmentUiModel? by epoxyModelDelegate(null)

  override fun buildModels() {
    val model = enrollmentUiModel ?: return

    firstNameEditText(
      text = model.firstName.value,
      error = model.firstName.error?.message,
      onTextChangeAction = callback::onFirstNameChanged
    )
    lastNameEditText(
      text = model.lastName.value,
      error = model.lastName.error?.message,
      onTextChangeAction = callback::onLastNameChanged
    )
    enrollButton(
      onClickAction = callback::onEnrollButtonClicked
    )
  }

  interface Callback {
    fun onFirstNameChanged(value: String)
    fun onLastNameChanged(value: String)
    fun onEnrollButtonClicked()
  }
}
