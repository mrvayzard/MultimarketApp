package com.vayzard.utils.extension

import android.widget.TextView

fun TextView.setTextIfDifferent(value: String) {
  if (text?.toString().orEmpty() != value) {
    text = value
  }
}