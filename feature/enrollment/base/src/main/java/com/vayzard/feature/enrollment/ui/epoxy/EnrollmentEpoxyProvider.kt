package com.vayzard.feature.enrollment.ui.epoxy

import com.airbnb.epoxy.ModelCollector
import com.vayzard.core.ui.epoxy.OnTextChange
import com.vayzard.core.ui.epoxy.button
import com.vayzard.core.ui.epoxy.editText
import com.vayzard.core.ui.epoxy.margin
import com.vayzard.utils.extension.dp

interface EnrollmentEpoxyProvider {
  fun ModelCollector.firstNameEditText(
    text: String,
    error: String?,
    onTextChangeAction: OnTextChange
  )

  fun ModelCollector.lastNameEditText(
    text: String,
    error: String?,
    onTextChangeAction: OnTextChange
  )

  fun ModelCollector.enrollButton(
    onClickAction: (() -> Unit)?
  )
}

class EnrollmentEpoxyProviderImpl : EnrollmentEpoxyProvider {
  override fun ModelCollector.firstNameEditText(
    text: String,
    error: String?,
    onTextChangeAction: OnTextChange
  ) {
    editText {
      id("firstNameEditText")
      value(text)
      errorMessage(error)
      hintMessage("First name")
      onTextChange(onTextChangeAction)
    }
    margin {
      id("marginAfterFirstName")
      vertical(16.dp)
    }
  }

  override fun ModelCollector.lastNameEditText(
    text: String,
    error: String?,
    onTextChangeAction: OnTextChange
  ) {
    editText {
      id("lastNameEditText")
      value(text)
      errorMessage(error)
      hintMessage("Last name")
      onTextChange(onTextChangeAction)
    }
    margin {
      id("marginAfterLastName")
      vertical(16.dp)
    }
  }

  override fun ModelCollector.enrollButton(
    onClickAction: (() -> Unit)?
  ) {
    button {
      id("enrollButton")
      value("Enroll")
      onClickListener(onClickAction)
    }
    margin {
      id("marginAfterEnrollButton")
      vertical(16.dp)
    }
  }
}