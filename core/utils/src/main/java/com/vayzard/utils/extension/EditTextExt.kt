package com.vayzard.utils.extension

import android.widget.EditText

fun EditText.setTextIfDifferent(value: String) {
  if (text?.toString().orEmpty() != value) {
    setText(value)
  }
}
