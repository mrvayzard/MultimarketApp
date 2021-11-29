package com.vayzard.feature.enrollment.ui.extension

import android.widget.EditText

fun EditText.setTextIfDifferent(value: String) {
  if (text?.toString().orEmpty() != value) {
    setText(value)
  }
}

fun EditText.setErrorIfDifferent(value: String?) {
  if (error != value) {
    error = value
  }
}