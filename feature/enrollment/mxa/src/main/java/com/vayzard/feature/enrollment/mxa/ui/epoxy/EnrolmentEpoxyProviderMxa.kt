package com.vayzard.feature.enrollment.mxa.ui.epoxy

import com.airbnb.epoxy.ModelCollector
import com.vayzard.core.ui.epoxy.OnTextChange
import com.vayzard.core.ui.epoxy.editText
import com.vayzard.core.ui.epoxy.margin
import com.vayzard.utils.extension.dp

internal interface EnrollmentEpoxyProviderMxa {
  fun ModelCollector.mexicoSpecificFieldEditText(
    text: String,
    error: String?,
    onTextChangeAction: OnTextChange
  )
}

internal class EnrollmentEpoxyProviderMxaImpl : EnrollmentEpoxyProviderMxa {
  override fun ModelCollector.mexicoSpecificFieldEditText(
    text: String,
    error: String?,
    onTextChangeAction: OnTextChange
  ) {
    editText {
      id("mexicoSpecificFieldEditText")
      value(text)
      errorMessage(error)
      hintMessage("Mexico specific field")
      onTextChange(onTextChangeAction)
    }
    margin {
      id("marginAfterMexicoSpecificField")
      vertical(16.dp)
    }
  }
}